package com.actionbazaar.chat;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import com.actionbazaar.chat.commands.AbstractCommand;

public class CommandMessageHandler implements MessageHandler.Whole<AbstractCommand>{

	private static final Logger logger = Logger.getLogger("ChatEndpointConfig");
	

	private final ChatServer chatServer;
	
	private final Session session;
	
	public CommandMessageHandler(ChatServer chatServer, Session session) {
		this.chatServer = chatServer;
		this.session = session;
	}
	
	@Override
	public void onMessage(AbstractCommand command) {
		if(logger.isLoggable(Level.FINE)) {
			logger.log(Level.INFO, "Message received: {0}", command);
		}
		command.assosiateSession(session, chatServer);
		chatServer.performCommand(command);
		
	}
	
	

}
