package com.actionbazaar.chat.bulletin;

import java.io.Serializable;

import javax.websocket.Session;

public class CustomServiceRepEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2043539066101812534L;

	private Session session;
	
	public CustomServiceRepEvent(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}	
	
}
