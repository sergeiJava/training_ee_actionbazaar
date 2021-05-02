package com.actionbazaar.chat;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;




@WebListener
public class ContextListener  implements ServletContextListener{

	private static final Logger logger = Logger.getLogger("ContextListener");
	
	@Inject
	private ChatServer chatServer;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		logger.log(Level.INFO, "ContextListener initialized......");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.log(Level.INFO, "ContextListener destroyed......");
		chatServer.shutdown();
		
	}

}
