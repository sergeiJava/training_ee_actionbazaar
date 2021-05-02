package com.actionbazaar.buslogic;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import com.actionbazaar.account.BazaarAccount;
import com.actionbazaar.buslogic.exceptions.CreditCardSystemException;
import com.actionbazaar.model.Bid;
import com.actionbazaar.model.CreditCard;
import com.actionbazaar.model.Item;

public interface BidManager {

	
	void placeSnagItOrder(Item item, BazaarAccount bazaarAccount, CreditCard card);
	
	void closeBid(Item item, BazaarAccount bazaarAccount, BigDecimal initialPrice) throws CreditCardSystemException;
	
	void cancelBid(Bid bid);
	
	@PermitAll
	List<Bid> getBids(Item item);
	
	void placeBid(Bid bid);
}
