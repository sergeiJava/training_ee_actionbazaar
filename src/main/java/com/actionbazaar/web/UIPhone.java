package com.actionbazaar.web;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;


@FacesComponent("ui-component")
public class UIPhone extends UIInput implements NamingContainer{

	public static String COMPONENT_BOUNDLE = "com.actionbazaar.component";
	
	public static String AREA_CODE = "areaCode";
	
	public static String PREFIX = "prefix";
	
	public static String LINE_NUMBER = "lineNumber";

	/**
     * Returns the identifier of the component's family.
     * @return family identifier
     */
	@Override
	public String getFamily() {
		return "javax.faces.NamingContainer";
	}

	/**
     * Performs the encoding - pulling the value out from the model and storing it in the UI
     * @param context - context
     * @throws IOException - IOException
     */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		PhoneNumber phone = (PhoneNumber)getValue();
		UIInput areaCode = (UIInput)findComponent(AREA_CODE);
		UIInput prefix = (UIInput)findComponent(PREFIX);
		UIInput lineNumber = (UIInput) findComponent(LINE_NUMBER);
		if(phone != null) {
			areaCode.setValue(phone.getAreaCode());
			prefix.setValue(phone.getPrefix());
			lineNumber.setValue(phone.getLineNumber());
		}
		super.encodeBegin(context);
	}

	/**
     * Validates the phone number field
     * @param context - context
     */
	@Override
	public void processValidators(FacesContext context) {
		Object value = getAttributes().get("required");
		boolean required = false;
		if(value != null && (Boolean)value) {
			required = (boolean) value;
		}
		UIInput areaCode = (UIInput)findComponent(AREA_CODE);
        UIInput prefix = (UIInput)findComponent(PREFIX);
        UIInput lineNumber = (UIInput)findComponent(LINE_NUMBER);
        int provided = 0;
        
        String areaCodeStr = (String) areaCode.getSubmittedValue();
        if(areaCodeStr.length() < 3 && required ) {
        	areaCode.setValid(false);
        } else if (areaCodeStr.length() == 3) {
        	provided = provided | 0x1;
        }
        String prefixStr = (String) prefix.getSubmittedValue();
        if(prefixStr.length() < 3 && required) {
        	prefix.setValid(false);
        } else if (prefixStr.length() == 3) {
        	provided = provided | 0x2;
        }
        
        String lineNUmberStr = (String) lineNumber.getSubmittedValue();
        if(lineNUmberStr.length() < 4 &&  required) {
        	lineNumber.setValid(false);
        } else if( lineNUmberStr.length() == 4 ) {
        	provided = provided | 0x4;
        }
        
        if(required && provided != 7) {
        	String requiredMessage = (String) getAttributes().get("requiredMessage");
        	context.addMessage(lineNumber.getClientId(), new FacesMessage(
        			FacesMessage.SEVERITY_ERROR,requiredMessage,requiredMessage));
        }
        if(provided != 0 && provided != 7) {
        	FacesContext facesContext = FacesContext.getCurrentInstance();
        	Locale locale = null;
        	if(facesContext != null && facesContext.getViewRoot() !=null) {
        		locale = facesContext.getViewRoot().getLocale();
        		if(locale == null) {
        			locale = Locale.getDefault();
        		}
        	}
        	ResourceBundle bundle = ResourceBundle.getBundle(COMPONENT_BOUNDLE, locale);
        	context.addMessage(lineNumber.getClientId(), new FacesMessage(
        			FacesMessage.SEVERITY_ERROR,bundle.getString("invalid_phone"), bundle.getString("invalid_phone")));
        }
        
        if(!required && provided > 0) {
        	if((provided & 0x1) != 1) 
        		areaCode.setValid(false);
        	if((provided & 0x2) != 2)
        		prefix.setValid(false);
        	if((provided & 0x4) != 4)
        		lineNumber.setValid(false);
        }
		super.processValidators(context);
	}
	
	/**
     * Decodes the incoming request
     * @param context - faces context
     */
	@Override
	public void decode(FacesContext context) {
		super.decode(context);
		UIInput areaCode = (UIInput)findComponent(AREA_CODE);
        UIInput prefix = (UIInput)findComponent(PREFIX);
        UIInput lineNumber = (UIInput)findComponent(LINE_NUMBER);
        PhoneNumber pn = new PhoneNumber();
        pn.setAreaCode((String) areaCode.getSubmittedValue());
        pn.setPrefix((String) prefix.getSubmittedValue());
        pn.setLineNumber((String) lineNumber.getSubmittedValue());
		setValue(pn);
	}
	
	
}
