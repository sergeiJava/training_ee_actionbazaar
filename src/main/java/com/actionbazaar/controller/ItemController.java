package com.actionbazaar.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.actionbazaar.CurrentUser;
import com.actionbazaar.NavigationRules;
import com.actionbazaar.NewItem;
import com.actionbazaar.SelectedItem;
import com.actionbazaar.account.BazaarAccount;
import com.actionbazaar.account.User;
import com.actionbazaar.buslogic.ItemManager;
import com.actionbazaar.model.Item;
import com.actionbazaar.NewItem;

@Model
public class ItemController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("ItemController");

	@EJB
	private ItemManager itemManager;
	
	@Inject
	private Conversation conversation;
	
	private BigDecimal bidAmount;
	
	private Item item;
	
	@Inject @NewItem
	private Event<Item> itemNotifier;
	
	@CurrentUser
	private User currentUser;
	
	public String startConversation(Item item) {
		conversation.begin();
		this.item = item;
		return NavigationRules.ITEM.getRule();
	}
	
	@Produces
	@Named("selectedItem")
	@SelectedItem
	@ConversationScoped
	public Item getCurrentItem() {
		if(item == null)
			item = new Item();
		return item;
	}
	
	public String add() {
		logger.log(Level.INFO,"Adding item: {0}", item.getItemName());
		item.setSeller((BazaarAccount) currentUser);
		itemManager.save(item);
		itemNotifier.fire(item);
		return NavigationRules.HOME.getRule();
	}
}
