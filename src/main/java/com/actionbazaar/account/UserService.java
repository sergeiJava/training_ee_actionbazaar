package com.actionbazaar.account;

import javax.ejb.Local;

@Local
public interface UserService {

	User getUser(long userId);
	
	User getUser(String username);
	
	void createUser(User user);
	
	User getAuthenticatedUser();
	
	boolean isAuthenticated();
	
	boolean isUserExist(String username);
}
