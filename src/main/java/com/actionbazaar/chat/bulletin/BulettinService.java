package com.actionbazaar.chat.bulletin;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * This bulletin service despributes a message to all connected clients.
 */

@ServerEndpoint(value = "/admin/bulletin/{clientType}",
		decoders = {},
		encoders = {})
public class BulettinService implements MessageListener {

	private static final Logger logger = Logger.getLogger("Bulletin");
	
	@Inject
	@JMSConnectionFactory("java:/ConnectionFactory")
	private JMSContext context;
	
	@Resource(mappedName = "java:/jms/topic/BulletinMessage")
	private Topic topic;
	
	private JMSConsumer consumer;
	
	private JMSProducer producer;
    /**
     * Default constructor. 
     */
    public BulettinService() {
        // TODO Auto-generated constructor stub
    }
	
	/**
	 * Invoked whenever the message is published in JMS bulletin
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        logger.log(Level.INFO, "JMS Messages called");
        logger.log(Level.INFO, "JMS Message: {0}", message);
        
        
    }

    /**
     * Invoked when the client connects to the server
     * @param session - client session
     * @param config - session endpoint config
     * @param clientType - client type
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("clientType") String  clientType) {
    	logger.log(Level.INFO,"Client has been connected: {0}", session.getId());
    	//Resistering the consumer for JMS
    	//consumer = context.createConsumer(topic);
    	//consumer.setMessageListener(null);
    	//producer = context.createProducer();
    }
    
    /**
     * Process the incomming message for the client
     * @param message - message 
     * @param session - client session
     */
    @OnMessage
    public void processMessage(String message, Session session) {
    	logger.log(Level.INFO, "Procession incomming messages: {0}", message);
    	logger.log(Level.INFO, "for client : {0}", session.getId());
    }
    
    /**
    @OnMessage
    public CommandResult processCommand(@PathParam("clientType") String clientType, BulletinCommand command, Session session) {
    	return null;
    }
    */
    /**
     * Close JMS connection.
     */
    @OnClose
    public void cleanup() {
    	logger.log(Level.INFO, "Connection closed...(onClose method called )");
    	//consumer.close();
    }
    
    @OnError
    public void handleError(Session session, Throwable throwable, @PathParam("clientType") String clientType) {
    	logger.log(Level.INFO, "Connection closed...(onError method called )");
    	logger.log(Level.SEVERE, "Error occured with client session: {0}", session.getId());
    	logger.log(Level.SEVERE, "Error : {0}", session.getId());
    	//consumer.close();
    }
    
    
}
