package com.actionbazaar.chat;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.websocket.CloseReason;
import javax.websocket.server.ServerEndpoint;

import com.actionbazaar.chat.commands.AbstractCommand;
import com.actionbazaar.chat.commands.CommandMessageDecoder;
import com.actionbazaar.chat.commands.CommandMessageEncoder;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms/topic/BulletinMessage"), 
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "java:/jms/topic/BulletinMessage")
@ServerEndpoint(value = "/admin/support",
		encoders = {CommandMessageEncoder.class},
		decoders = {CommandMessageDecoder.class})
public class SupportChatEndpoint implements MessageListener{

	
	private static final Logger logger = Logger.getLogger("ChatSupport");
	
	@EJB
	private ChatServer chatServer;
	
	private Session session;
	
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		logger.log(Level.INFO, "Trying to add Support member to cache {0}", session.getId());
		chatServer.customServiceRepConnected(session);
	}

	@OnMessage
	public void onMessage(Session session, AbstractCommand command) {
		command.assosiateSession(session, chatServer);
		chatServer.performCommand(command);
	}
	
	

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.log(Level.INFO, "CSR onClose method called");
		chatServer.customServiceRepDisconnected(session);
		
		
	}


	@OnError
	public void onError(Session session, Throwable thr) {
		logger.log(Level.INFO, "CSR onError method called");
		chatServer.customServiceRepDisconnected(session);
		
		try {
			session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, thr.getMessage()));
		} catch (IOException e) {
			Logger.getLogger(SupportChatEndpoint.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * JMS method receives a message from topic
	 */
	@Override
	public void onMessage(Message message) {
		logger.log(Level.INFO, "JMS Message received....");
		
		Map<String, String> parameters = new HashMap<>();
		try {
			if(message instanceof MapMessage) {
				MapMessage mess = (MapMessage) message;
				Enumeration<String> names;
				names = mess.getMapNames();
				while(names.hasMoreElements()) {
					String key = names.nextElement().toString();
					logger.log(Level.INFO, "JMS Message key: {0}", key);
					String value = mess.getString(key);
					logger.log(Level.INFO, "JMS Message value: {0}", value);
					parameters.put(key, value);
				}
			}
			chatServer.sendCommandMessage(session, parameters);
		} catch (JMSException e) {
			logger.log(Level.SEVERE, "Failed to handle JMS message: ", e);
		}
		
	}

	
}
