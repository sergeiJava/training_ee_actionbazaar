package com.actionbazaar.chat.commands;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class ChatMessage extends AbstractCommand{

	private String user;
	
	private String message;
	
	/**
	 * Protected constructor - used by the decoder
	 */
	protected ChatMessage() {
		super(CommandTypes.MESSAGE);
		
	}
	
	/**
	 * Constructs a ew chat message
	 * @param user
	 * @param message
	 */
	public ChatMessage(String user, String message) {
		super(CommandTypes.MESSAGE);
		this.user = user;
		this.message = message;
	}

	/**
	 * Returns user
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Returns message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
     * Encodes a chat message
     * @param writer - JSON writer
     */
	@Override
	void encode(JsonGenerator writer) {
		writer.write("user", user);
		writer.write("message", message);
		
	}

	/**
	 * Decodes a message
	 * @param jsonObject - json object to be decoded
	 */
	@Override
	void decode(JsonObject jsonObject) {
		if(jsonObject.containsKey("user")) {
			user = jsonObject.getString("user");
		}
		if(jsonObject.containsKey("message")) {
			message = jsonObject.getString("message");
		}
		
	}

	/**
	 * Sends the message out
	 */
	@Override
	public void perform() {
		chatServer.sendMessage(session, message);
	}

}
