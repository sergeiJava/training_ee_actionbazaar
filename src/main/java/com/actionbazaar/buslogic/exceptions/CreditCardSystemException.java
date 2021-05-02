package com.actionbazaar.buslogic.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class CreditCardSystemException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
