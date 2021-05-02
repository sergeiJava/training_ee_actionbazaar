package com.actionbazaar.chat;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

import com.actionbazaar.chat.commands.CommandMessageDecoder;
import com.actionbazaar.chat.commands.CommandMessageEncoder;

public class ChatEndpointConfig implements ServerApplicationConfig {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("ChatEndpointConfig");
    
    public ChatEndpointConfig() {
        logger.info("ChatEndpointConfig instantiated...");
    }
    
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
        logger.info("Checking the endpoint...");
        // Configuring the decoders
        List<Class<? extends Decoder>> decoders = new LinkedList<>();
        decoders.add(CommandMessageDecoder.class);
        // Configuring the encoders
        List<Class<? extends Encoder>> encoders = new LinkedList<>();
        encoders.add(CommandMessageEncoder.class);
        Set<ServerEndpointConfig> configs = new HashSet<>();
        //configs.add(ServerEndpointConfig.Builder.create(ClientChatEndpoint.class, "/chat").build());
        //configs.add(ServerEndpointConfig.Builder.create(SupportChatEndpoint.class, "/admin/support").decoders(decoders).encoders(encoders).build());
        return configs;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        return scanned;
    }
    
}