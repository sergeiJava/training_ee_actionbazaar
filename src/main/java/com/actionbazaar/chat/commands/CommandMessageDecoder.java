package com.actionbazaar.chat.commands;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class CommandMessageDecoder implements Decoder.Text<AbstractCommand>{

	
	public static final Logger logger = Logger.getLogger("ChatEndpointConfig");
	/**
	 * Initializes the decoders
	 * @param config - endpoint configuration
	 */
	@Override
	public void init(EndpointConfig config) {
		// do nothing
		
	}

	@Override
	public void destroy() {
		// destroyed!
		
	}

	/**
	 * Decodes the message
	 * @param message
	 * @return 
	 * @throws DecodeException
	 */
	@Override
	public AbstractCommand decode(String message) throws DecodeException {
		logger.log(Level.INFO, "Decoding {0}", message);
		JsonObject struct;
		try(JsonReader rdr = Json.createReader(new StringReader(message))){
			struct = rdr.readObject();
		}
		String type = struct.getString("type");
		CommandTypes cmdType = CommandTypes.valueOf(type);
		try {
			AbstractCommand cmd = (AbstractCommand)cmdType.getCommandClass().newInstance();
			cmd.decode(struct);
			return cmd;
		} catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(CommandMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
            throw new DecodeException(message,"Could not be decoded - invalid type.");
        }
	}

	/**
	 * Returns true if the message is a command
	 * @param message - contents of the message
	 * @return true if we can decode this message
	 */
	@Override
	public boolean willDecode(String message) {
		JsonObject struct;
		try(JsonReader rdr = Json.createReader(new StringReader(message))){
			struct = rdr.readObject();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
