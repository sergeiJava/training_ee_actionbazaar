package com.actionbazaar.account;

import java.io.Serializable;

public class AnonymousUser extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnonymousUser() {
		setUsername("ANONYMOUS");
	}

	@Override
	public boolean isAnonymous() {
		return true;
	}
	
	
	
}
