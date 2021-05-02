package com.actionbazaar.web;

import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class MessageBundleProvider {

	private transient ResourceBundle bundle;
	
	@Produces
	@MessageBundle
	public ResourceBundle getBundle() {
		if(bundle == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			bundle = context.getApplication().getResourceBundle(context, "msg");
		}
		return bundle;
	}
}
