package com.actionbazaar;

public enum NavigationRules {

	ITEM("item"),
	PLACE_BID("placeBid"),
	HOME("home");
	
	private String rule;
	
	private NavigationRules(String rule) {
		this.rule = rule;
	}
	
	public String getRule() {
		return rule;
	}
}
