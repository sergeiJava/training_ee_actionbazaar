package com.actionbazaar.chat;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;

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


@ServerEndpoint(value = "/chat",
		encoders = {CommandMessageEncoder.class},
		decoders = {CommandMessageDecoder.class})

public class ClientChatEndpoint{

	private static final Logger logger = Logger.getLogger("ChatService");
	
	@EJB
	private ChatServer chatServer;
	
	
	public ClientChatEndpoint() {
		
	}
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		logger.log(Level.INFO, "Trying to establish connection with websocket, session: {0}", session.getId());
		chatServer.addClientSession(session);
	}
	
	@OnMessage
	public void onMessage(Session session, AbstractCommand command) {
		
		command.assosiateSession(session, chatServer);
		chatServer.performCommand(command);
		logger.log(Level.INFO, "Message sent");
	}
	

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.log(Level.INFO, "onClose: Removing client session from cache");
		chatServer.removeClientSession(session);
	}
	
	
	@OnError
	public void onError(Session session, Throwable thr) {
		logger.log(Level.INFO, "onError: Removing client session from cache");
		chatServer.removeClientSession(session);
		logger.log(Level.SEVERE, "Fatal Error occured", thr);
	}

	
	
}
