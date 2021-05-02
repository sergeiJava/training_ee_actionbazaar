package com.actionbazaar.account;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Named
@SessionScoped
public class SessionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UserService userService;
	
	private User user;
	
	@Produces
	@SessionScoped
	@AuthenticatedUser
	@Named("currentUser")
	public User getCurrentUser() {
		System.out.println("getCurrentUser method called>>>>>>>");
		if(user == null || user.isAnonymous()) {
			user = userService.getAuthenticatedUser();
			System.out.println(">>>>User not null " + user.getUsername());
		}
		return user;
	}
	
	public boolean isAuthenticated() {
		return userService.isAuthenticated();
	}
	
	public void logout(@Disposes @AuthenticatedUser User user) {
		this.user = null;
		
	}
}
