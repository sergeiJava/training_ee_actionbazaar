package com.actionbazaar.web;

import java.io.Serializable;

public class PhoneNumber implements Serializable{

	
	private static final long serialVersionUID = 1921527305556046248L;
	
	private String areaCode = "";
	
	private String prefix = "";
	
	private String lineNumber = "";
	
	public PhoneNumber() {
		
	}
	
	public PhoneNumber(String areaCode, String prefix, String lineNumber) {
		this.areaCode = areaCode;
		this.prefix = prefix;
		this.lineNumber = lineNumber;		
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	@Override
	public String toString() {
		return areaCode + "-" + prefix + "-" + lineNumber;
		
	}

}
