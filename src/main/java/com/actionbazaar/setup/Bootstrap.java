package com.actionbazaar.setup;


import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


/**
 * Session Bean implementation class Bootstrap
 */
@Singleton
@Startup
public class Bootstrap {

	private static final Logger logger = Logger.getLogger("BootStrap");
	
	@Inject
	private SystemStartup systemStartup;
	
	
    /**
     * Default constructor. 
     */
    public Bootstrap() {
        // TODO Auto-generated constructor stub
    }

    @PostConstruct
    public void innit() {
    	logger.info("BootStrap loadded.");
    	systemStartup.innit();
    }
}
