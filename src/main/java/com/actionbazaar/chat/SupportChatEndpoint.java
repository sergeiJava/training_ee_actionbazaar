package com.actionbazaar.chat;

import java.io.IOException;
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

@ServerEndpoint(value = "/admin/support",
		encoders = {CommandMessageEncoder.class},
		decoders = {CommandMessageDecoder.class})
public class SupportChatEndpoint{

	
	private static final Logger logger = Logger.getLogger("ChatSupport");
	
	@EJB
	private ChatServer chatServer;
	
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
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
		chatServer.customServiceRepDisconnected(session);
		
	}


	@OnError
	public void onError(Session session, Throwable thr) {
		chatServer.customServiceRepDisconnected(session);
		try {
			session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, thr.getMessage()));
		} catch (IOException e) {
			Logger.getLogger(SupportChatEndpoint.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	
}
