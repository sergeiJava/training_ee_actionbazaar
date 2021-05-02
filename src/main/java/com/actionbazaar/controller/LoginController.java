package com.actionbazaar.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.actionbazaar.NavigationRules;

@Model
public class LoginController {

	@Inject
	private FacesContext context;
	
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String authenticate() {
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.login(username, password);
			Logger.getLogger("LoginTest").log(Level.INFO, "Got username: {0}", username);
		} catch (Throwable t) {
			return null;
		}
		return NavigationRules.HOME.getRule();
		
	}
	
}
