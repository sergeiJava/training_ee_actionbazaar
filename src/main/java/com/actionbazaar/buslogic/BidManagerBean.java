package com.actionbazaar.buslogic;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.actionbazaar.BidManagerQualifier;
import com.actionbazaar.account.BazaarAccount;
import com.actionbazaar.buslogic.exceptions.CreditCardSystemException;
import com.actionbazaar.buslogic.exceptions.CreditProcessingException;
import com.actionbazaar.model.Bid;
import com.actionbazaar.model.CreditCard;
import com.actionbazaar.model.Item;

/**
 * Session Bean implementation class BidManagerBeanCMT
 */

@Stateless
@BidManagerQualifier
public class BidManagerBean implements BidManager{

	private static final Logger logger = Logger.getLogger("CreditCard");
	
	@EJB
	CreditCardManager creditCardManager;
	
	@Resource
	SessionContext context;
    /**
     * Default constructor. 
     */
    public BidManagerBean() {
        // TODO Auto-generated constructor stub
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void placeSnagItOrder(Item item, BazaarAccount bazaarAccount, CreditCard card) {
		try {
			if(!hasBids(item)) {
				creditCardManager.validateCard(card);
				creditCardManager.chargeCreditCard(card, item.getInitialPrice());
				closeBid(item, bazaarAccount, item.getInitialPrice());
			}
		} catch (CreditProcessingException ce) {
            logger.log(Level.SEVERE, "An error ocurred processing the order.", ce);
            context.setRollbackOnly();
        } catch (CreditCardSystemException ccse) {
            logger.log(Level.SEVERE, "Unable to validate credit card.", ccse);
            context.setRollbackOnly();
        }
		
	}
    
    public void closeBid(Item item, BazaarAccount bazaarAccount, Double initialPrice) throws CreditCardSystemException{
    	//closes out the Bid....
    }
    
    protected boolean hasBids(Item item) {
    	return item.getBids().isEmpty();
    }

    public void cancelBid(Bid bid) {
    	//process with canceling the bid
    }
    
    @PermitAll
    public List<Bid> getBids(Item item){
    	return item.getBids();
    }


	@Override
	public void closeBid(Item item, BazaarAccount bazaarAccount, BigDecimal initialPrice)
			throws CreditCardSystemException {
		// TODO Auto-generated method stub
		
	}

	/**
     * Must be authenticated as a bidder to bid!
     * @param bid - bidder
     */
	@RolesAllowed({"seller","bidder"})
	@Override
	public void placeBid(Bid bid) {
		logger.info("Bid placed!");
	}
}
