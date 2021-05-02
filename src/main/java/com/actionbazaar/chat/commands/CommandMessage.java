package com.actionbazaar.chat.commands;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;

public class CommandMessage extends AbstractCommand{

	private Map<String, String> parameters = new HashMap<String, String>();
	
	
	/**
	 * Invoked by the decoder
	 */
	protected CommandMessage() {
		super(null);
	}

	/**
     * Constructs a new no parameter command message
     * @param command - command
     */
	public CommandMessage(CommandTypes command) {
		super(command);
	}
	
	/**
	 * Initialize a command message
	 * @param command - message
	 * @param parametes - parametes
	 */
	public CommandMessage(CommandTypes command, Map<String, String> parametes) {
		super(command);
		this.parameters = parametes;
	}
	
	/**
	 * Returns the parameters for the command
	 * @return parameters
	 */
	public Map<String, String> getParameters(){
		return parameters;
	}
	
	/**
	 * Writes out the entries
	 */
	@Override
	void encode(JsonGenerator writer) {
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			writer.write(entry.getKey(), entry.getValue());
		}
		
	}

	/**
	 * Decodes the incoming message
	 */
	@Override
	void decode(JsonObject jsonObject) {
		for (String key : jsonObject.keySet()) {
			if(!key.equals(("type"))) {
				parameters.put(key, jsonObject.getString(key));
			} else {
				setCommandType(CommandTypes.valueOf(jsonObject.getString(key)));
			}
		}
	}

	/**
	 * perform the requested command
	 */
	@Override
	public void perform() {
		switch(getCommand()) {
		
		}
	}

}
