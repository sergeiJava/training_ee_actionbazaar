package com.actionbazaar.chat.commands;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.websocket.Session;

import com.actionbazaar.chat.ChatServer;

/**
 * Represents an abstract command.
 * 
 */
public abstract class AbstractCommand {

	/**
	 * Command
	 */
	private CommandTypes commandType;
	
	/**
	 * Session
	 */
	protected Session session;
	
	/*
	 * Chat Server
	 */
	@EJB
	protected ChatServer chatServer;
	
	/**
	 * Initializes a command message
	 * @param command - message
	 */
	public AbstractCommand(CommandTypes command) {
		this.commandType = command;
	}
	
	/**
     * Returns the command - used to determine which class to instantiate
     * @return command
     */
	public CommandTypes getCommand() {
		return commandType;
	}
	
	/**
     * Sets the command type
     * @param commandType - command type
     */
	protected void setCommandType(CommandTypes commandType) {
		this.commandType = commandType;
	}
	
	/**
     * Associates this command with the server context
     * @param session - session to which this command was issued
     * @param chatServer - chat server
     */
	public void assosiateSession(Session session, ChatServer chatServer) {
		this.session = session;
		this.chatServer = chatServer;
	}
	
	/**
     * Encodes a message
     * @param writer - JSON Generator
     */
	abstract void encode(JsonGenerator writer);
	
	/**
	 * Decodes a message
	 * @param jsonObject - JSON reader
	 */
	abstract void decode(JsonObject jsonObject);
	
	/**
	 * Invoked to perform the command.
	 */
	public abstract void perform();
}
