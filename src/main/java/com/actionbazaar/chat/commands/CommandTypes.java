package com.actionbazaar.chat.commands;

public enum CommandTypes {

	CONNECT(CommandMessage.class),
	MESSAGE(ChatMessage.class),
	CSR_LOST(CommandMessage.class),
	CLIENT_LOST(CommandMessage.class),
	COMMAND(CommandMessage.class);
	
	
	private final Class clazz;
	
	CommandTypes(Class clazz){
		this.clazz = clazz;
	}
	
	/**
	 * Return the command class
	 * @return command class
	 */
	public Class getCommandClass() {
		return clazz;
	}
}
