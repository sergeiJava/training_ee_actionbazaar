package com.actionbazaar.chat.commands;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

public class CommandMessageEncoder implements Encoder.Text<AbstractCommand>{

	/**
	 * Initialize method
	 * @param config - endpoint configuration
	 */
	@Override
	public void init(EndpointConfig config) {
		// no implementation
		
	}

	/**
	 * Release any resources
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Encode the message
	 * @param commandMessage - command message
	 * @return JSON encoded message
	 * @throws EncodeException - throws if there is a problem encoding the message
	 */
	@Override
	public String encode(AbstractCommand commandMessage) throws EncodeException {
		StringWriter writer = new StringWriter();
		try(JsonGenerator jsonWriter = Json.createGenerator(writer)){
			jsonWriter.writeStartObject();
			jsonWriter.write("type", commandMessage.getCommand().toString());
			commandMessage.encode(jsonWriter);
			jsonWriter.writeEnd();
		}
		return writer.toString();		
	}

}
