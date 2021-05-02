package com.actionbazaar.chat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import com.actionbazaar.account.Employee;
import com.actionbazaar.chat.commands.ChatMessage;
import com.actionbazaar.chat.commands.CommandMessage;
import com.actionbazaar.chat.commands.CommandTypes;

public class SupportConversaion implements Serializable{

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 3721383820319526216L;

	
	/**
	 * Employee answering questions
	 */
	private Employee employee;
	
	/**
	 * Session
	 */
	private Session clientSession;
	
	/**
	 * Employee session
	 */
	private Session csrSession;
	
	/**
	 * Support Conversation
	 * @param clientSession - client session
	 * @param csrSession - customer service session
	 */
	public SupportConversaion(Session clientSession, Session csrSession) {
		this.clientSession = clientSession;
		this.csrSession = csrSession;
	}
	
	/**
	 * Sends an introduction to each person in the convesation
	 */
	public void connect() {
		Map<String, String> parameters = new HashMap<String, String>();
		if(clientSession.getUserPrincipal() == null) {
			parameters.put("message", "You are talking to a guest.");
		} else {
			parameters.put("message", "You are talking to " + clientSession.getUserPrincipal());
		}
		CommandMessage introForCsr = new CommandMessage(CommandTypes.CONNECT, parameters);
		parameters = new HashMap<>();
		parameters.put("message", "You are talking to " + csrSession.getUserPrincipal());
		CommandMessage introForClient = new CommandMessage(CommandTypes.CONNECT,parameters);
		clientSession.getAsyncRemote().sendObject(introForClient);
		csrSession.getAsyncRemote().sendObject(introForCsr);
	}
	
	/**
	 * Delivers a message to the other party
	 * @param id - message id
	 * @param message - message to be send
	 */
	public void deliverMessage(String id, String message) {
		String username;
		if(clientSession.getId().equals(id)) {
			if(clientSession.getUserPrincipal() == null) {
				username = "Guest";
			} else {
				username = clientSession.getUserPrincipal().getName();
			}
		} else {
			username = csrSession.getUserPrincipal().getName();
		}
		ChatMessage cm = new ChatMessage(username, message);
		csrSession.getAsyncRemote().sendObject(cm);
		clientSession.getAsyncRemote().sendObject(cm);
	}
	
	/**
	 * Returns the client session
	 * @return client session
	 */
	public Session getClientSession() {
		return clientSession;
	}
	
	/**
	 * Returns the CSR session
	 * @return csr session
	 */
	public Session getCSRSession() {
		return csrSession;
	}
}
