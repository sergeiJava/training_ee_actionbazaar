package com.actionbazaar.buslogic;

import com.actionbazaar.buslogic.exceptions.CreditProcessingException;
import com.actionbazaar.model.CreditCard;

public interface CreditCardManager {
	
	public void chargeCreditCard(CreditCard creditCard, Double amount) throws CreditProcessingException;
	
	public boolean validateCard(CreditCard card) throws CreditProcessingException;


}
