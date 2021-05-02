package com.actionbazaar.web;

/**
 * Page Navigation Enumeration
 * @author test
 *
 */
public enum PageNavigationEnum {
	DOCUMENT("document"),
	SELL("sell"),
	CREATE_ACCOUNT("create_account"),
	HOME("home");
	
	private String navigationStep;
	
	private PageNavigationEnum(String navigationStep) {
		this.navigationStep = navigationStep;
	}
	
	public String toString() {
		return navigationStep;
	}
}
