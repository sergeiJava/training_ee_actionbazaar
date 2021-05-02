package com.actionbazaar.buslogic;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.actionbazaar.buslogic.exceptions.CreditProcessingException;
import com.actionbazaar.model.CreditCard;

/**
 * Session Bean implementation class CreditCardManagerBean
 */
@Stateless(name = "CreditCardManager")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CreditCardManagerBean implements CreditCardManager{

	
	private static final Logger logger = Logger.getLogger("CreditCardManagerBean");
    /**
     * Default constructor. 
     */
    public CreditCardManagerBean() {
        // TODO Auto-generated constructor stub
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
	@Override
	public void chargeCreditCard(CreditCard creditCard, Double amount) throws CreditProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateCard(CreditCard card) throws CreditProcessingException {
		// TODO Auto-generated method stub
		return true;
	}

}
