package com.actionbazaar.controller;

import com.actionbazaar.BidManagerQualifier;
import com.actionbazaar.CurrentBid;
import com.actionbazaar.NavigationRules;
import com.actionbazaar.SelectedItem;
import com.actionbazaar.account.AuthenticatedUser;
import com.actionbazaar.account.BazaarAccount;
import com.actionbazaar.account.User;
import com.actionbazaar.buslogic.BidManager;
import com.actionbazaar.model.Bid;
import com.actionbazaar.model.Item;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class for placing a bid
 * @author Ryan Cuprak
 */
@Model
public class BidController implements Serializable {
    
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		/**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("ItemController");
    
    /**
     * Current bid - created when a user decides to bid on an item
     */
    @Inject @CurrentBid
    private Bid currentBid;
    
    /**
     * Current user
     */
    @Inject @AuthenticatedUser
    private User user;
    
    
    /**
     * Selected item
     */
    @Inject @SelectedItem
    private Item item;
    
    /**
     * Bid manager
     */
    @Inject @BidManagerQualifier
    private BidManager bidManager;
    
    /**
     * Reference to current conversation
     */
    @Inject
    private Conversation conversation;
    
    /**
     * Returns the current bid
     * @return current bid
     */
    @Produces 
    @CurrentBid 
    @Named("currentBid") 
    @ConversationScoped
    public Bid getCurrentBid() {
        Bid bid = new Bid();
        if(user instanceof BazaarAccount) {
            bid.setBidder((BazaarAccount)user);
            logger.log(Level.INFO, "User {0} detected", user.getUsername());
        } 
        bid.setItem(item);
        return bid;
    }
    
     /**
     * Places the order
     * @return 
     */
    public String placeOrder() {
        return NavigationRules.PLACE_BID.getRule();
    }
    
    /**
     * Confirms the bid - order placed
     * @return 
     */
    public String confirmBid() {
        logger.log(Level.INFO, "Conversation ID: {0}", conversation.getId());
        bidManager.placeBid(currentBid);
        conversation.end();
        return NavigationRules.HOME.getRule();
    }
    
}
