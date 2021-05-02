package com.actionbazaar;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LogoutController {

	private static final Logger logger = Logger.getLogger("LogoutController");
	
	@Inject
	FacesContext facesContext;
	
	public String logout() {
		logger.info("Performing logout...");
		facesContext.getExternalContext().invalidateSession();
		return NavigationRules.HOME.getRule();
	}
}
