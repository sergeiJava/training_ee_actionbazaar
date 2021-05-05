package com.actionbazaar.chat;


import com.actionbazaar.chat.commands.AbstractCommand;
import com.actionbazaar.chat.commands.ChatMessage;
import com.actionbazaar.chat.commands.CommandTypes;
import com.actionbazaar.chat.commands.CommandMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.TopicPublisher;
import javax.jms.Topic;
import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * Responsible for associating chats with authenticated users
 *
 */
@Singleton
public class ChatServer{

	/**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("ChatServer");
    
    /**
     * Support people with no one to talk to
     */
    
    private final Stack<Session> availableReps = new Stack<>();
    
    /**
     * Clients with no one to talk to
     */
   
    private final Stack<Session> waitingClients = new Stack<>();
    
    /**
     * Sessions
     */
    private Set<Session> csrSessions;
    
    /**
     * Client sessions
     */
    private Set<Session> clientSessions;
    
    /**
     * Conversations keyed by the csr session key
     */
    private Map<String,SupportConversaion> conversations;
    
   
	@Resource(mappedName = "java:/ConnectionFactory")
    private  TopicConnectionFactory connectionFactory;
	
    
    @Resource(mappedName = "java:/jms/topic/BulletinMessage")
    private Topic topic;
    
   
    private TopicPublisher producer;
    //private JMSProducer producer;
    
   private javax.jms.TopicSession jmsSession;
    
    /**
     * Constructs a new chat server
     */
    public ChatServer() {
        
    }
    
    @PostConstruct
    public void innit() {
    	logger.log(Level.INFO, "Initializing Chat Server....");
    	csrSessions = new HashSet<>();
        clientSessions = new HashSet<>();
        conversations = new HashMap<>();
        TopicConnection connection;
		try {
			connection = connectionFactory.createTopicConnection();
			jmsSession = connection.createTopicSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			producer = jmsSession.createPublisher(topic);
			logger.log(Level.INFO, "Producer registered: {0}", producer != null);
		} catch (JMSException e) {
			logger.log(Level.SEVERE, "Error registring producer", e);
		}
        
        
    }
    
    /**
     * Returns session statistics
     */
    public void getSessionStats() {
        
    }
    
    /**
     * Registers a new customer service representative.
     * @param csrSession - custom service representative
     */
    public void customServiceRepConnected(Session csrSession) { 
        csrSessions.add(csrSession);
        logger.log(Level.INFO, "CSR registered with session id: {0}", csrSession.getId());
        if(!waitingClients.empty()) {
        	//Session clientSession = removeWaitingClient();
            Session clientSession = waitingClients.pop();
            connectSession(clientSession,csrSession); 
        } else {
        	
            availableReps.push(csrSession);
            updateWaitingSessionCount();
            logger.log(Level.INFO, "CSR session added to waiting list");
            
        }
    }
    
    /**
     * Disconnects a session
     * @param csrSession - custom service representative disconnected
     */
    public void customServiceRepDisconnected(Session csrSession) {
        csrSessions.remove(csrSession);
        
    }
    
    /**
     * Adds a client session
     * @param clientSession - client session
     */
    public void addClientSession(Session clientSession) {
    	logger.log(Level.INFO, "Adding client session to cache: {0}", clientSession.getId());
        clientSessions.add(clientSession);
        if(!availableReps.empty()) {
            Session repSession = availableReps.pop();
            connectSession(clientSession,repSession); 
            
        } else {
           waitingClients.push(clientSession);
           logger.log(Level.INFO,"Client session added to waiting list");
        }
        updateWaitingSessionCount();
    }
    
    /**
     * Closes out a session
     * @param clientSession - client sessions
     */
    public void removeClientSession(Session clientSession) {
        logger.info("Closing client session.");
        SupportConversaion sc = conversations.get(clientSession.getId());
        clientSessions.remove(sc.getCSRSession());
        csrSessions.remove(sc.getClientSession());
        conversations.remove(sc.getCSRSession().getId());
        conversations.remove(sc.getClientSession().getId());
        // todo - send message to the csr that the session has ended. 
        sc.getCSRSession().getAsyncRemote().sendObject(new CommandMessage(CommandTypes.CLIENT_LOST));
        customServiceRepConnected(sc.getCSRSession());
        updateWaitingSessionCount();
    }
    
    /**
     * Handles a message
     * @param sourceSession - session sending the messages
     * @param message - text to be send
     */
    public void sendMessage(Session sourceSession, String message) {
        SupportConversaion sc = conversations.get(sourceSession.getId());
        if(sc != null) {
            sc.deliverMessage(sourceSession.getId(), message);
        } else {
            // the other party isn't there anymore...
            sourceSession.getAsyncRemote().sendObject(new ChatMessage("server","Chat session counterpart is missing."));
        }
    }
    
    /**
     * Send command message to session
     * @param session - target session
     * @param command - command
     */
    public void sendCommandMessage(Session session, Map<String, String> command) {
    	CommandMessage cm = new CommandMessage(CommandTypes.COMMAND, command);
    	session.getAsyncRemote().sendObject(cm);
    }
    /**
     * Handles a close message
     * @param session - message
     */
    public void handleClose(Session session) {
        // Loss of a customer service representative, put the client back in the pool to get a new CSR
        if(csrSessions.contains(session)) {
            csrSessions.remove(session);
            SupportConversaion sc = conversations.get(session.getId());
            if(sc != null) {
                sc.getClientSession();
                conversations.remove(sc.getClientSession().getId());
                sc.getClientSession().getAsyncRemote().sendObject(new CommandMessage(CommandTypes.CSR_LOST));
                waitingClients.push(sc.getClientSession());
            }
        }
    }
    
    /**
     * Connects a session - sends a command message over to both clients
     * @param clientSession - client session
     * @param csrSession - csr session
     */
    protected void connectSession(Session clientSession, Session csrSession) {
    	logger.log(Level.INFO, "Trying to connect CSR and Client sessions");
    	logger.log(Level.INFO,"CSR session: {0}", csrSession.getId());
    	logger.log(Level.INFO, "Client session: {0}", clientSession.getId());
        SupportConversaion sc = new SupportConversaion(clientSession,csrSession);
        conversations.put(clientSession.getId(),sc);
        conversations.put(csrSession.getId(),sc);
        sc.connect();
    }
    
    /**
     * Invoked by the context listener when the server is shutting down.
     */
    public void shutdown() {
        logger.info("Shutdown requested - terminating outstanding conversations.");
        for(Session session : csrSessions) {
            try { 
                session.close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY,
                        "Server shutdown"));
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Error closing connection",e);
            }
        }
        for(Session session : clientSessions) {
            try { 
                session.close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY,
                        "Server shutdown"));
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Error closing connection",e);
            }  
        }
    }
    
    /**
     * Command that is to be performed
     * @param command - command to be performed
     */
    public void performCommand(AbstractCommand command) {
        command.perform();
    }
	
	/**
	 * Sending message to Topic for all Customer Service Representatives
	 * message of current waiting clients and available CSR
	 */
	private void updateWaitingSessionCount() {
		logger.log(Level.INFO, "Trying to send JMS message");
		if(jmsSession != null) {
			try {
				MapMessage message = jmsSession.createMapMessage();
				message.setString("waitingCSR", String.valueOf(availableReps.size()));
				message.setString("waitingClients", String.valueOf(waitingClients.size()));
				producer.send(message);
			} catch (JMSException e) {
				logger.log(Level.SEVERE, "Failed to send JMS message:", e);
			}
		}
		logger.log(Level.INFO, "JMS message sent");
	}
	
}
