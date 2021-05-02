package com.actionbazaar.buslogic;

import java.util.logging.Level;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.jboss.logmanager.Logger;

import com.actionbazaar.BidManagerQualifier;
import com.actionbazaar.model.Bid;

@Decorator
public abstract class BidManagerFraudDetector implements BidManager {

	@Inject
	@Delegate
	@BidManagerQualifier
	private BidManager bidManager;

	@Override
	public void placeBid(Bid bid) {
		Logger.getLogger("Decorator").log(Level.INFO, "Checking for fraud...");
		bidManager.placeBid(bid);
		Logger.getLogger("Decorator").log(Level.INFO, "Fraud check complete...");
	}
	
	
	
}
