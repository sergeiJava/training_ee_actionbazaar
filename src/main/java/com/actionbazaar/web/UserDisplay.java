package com.actionbazaar.web;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.actionbazaar.account.AnonymousUser;
import com.actionbazaar.account.User;
import com.actionbazaar.account.UserService;

@Named
@SessionScoped
public class UserDisplay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9210732892315394744L;

	private static final Logger logger = Logger.getLogger("UserDisplay");
	
	private User user;
	
	@EJB
	private UserService userService;
	
	/**
	 * Checks credentials - if we have logged in
	 */
	private synchronized void checkCredentials() {
		if(user !=null && !user.isGuest()) {
			return;
		}
		String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		if(remoteUser != null) {
			Context ctx = null;
			try {
                ctx = new InitialContext();
                //UserService us = (UserService)ctx.lookup("java:global/actionbazaar-0.0.1-SNAPSHOT/UserServiceBean");
               // user = us.getUser(remoteUser);
                user = userService.getUser(remoteUser);
            } catch (NamingException e) {
                logger.log(Level.SEVERE,"Unable to retrieve the UserServiceBean.",e);
            } finally {
                if(ctx != null) try { ctx.close(); } catch (Throwable t) {}    
            }
		} else {
			instantiateGuestInstance();
		}
	}
	
	/**
     * Instantiates a guest instance
     */
	private void instantiateGuestInstance() {
		user = new AnonymousUser();
		user.setGuest(true);
	}
	
	/**
     * Returns the current user
     * @return user
     */
	public User getUser() {
		checkCredentials();
		return user;
	}
	
	/**
     * Returns true if we are a guest
     * @return true if guest
     */
	public boolean isGuest() {
		checkCredentials();
		return user == null || user.isGuest();
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		instantiateGuestInstance();
	}
}
