package com.actionbazaar.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import com.actionbazaar.NewItem;
import com.actionbazaar.buslogic.ItemManager;
import com.actionbazaar.chat.ChatServer;
import com.actionbazaar.model.Item;
import com.actionbazaar.util.PerformanceMonitor;

@Named
@ApplicationScoped
@PerformanceMonitor
public class LandingController {

	private ItemManager itemManamger;
	
	
	private List<Item> newestItems;
	
	protected LandingController() {
		
	}
	
	@Inject
	public LandingController(ItemManager itemManager) {
		this.itemManamger = itemManager;
	}
	
	@PostConstruct
	public void init() {
		newestItems = itemManamger.getNewestItems();
		
	}
	
	public void onNewItem(
			@Observes
			@NewItem
			Item item) {
		newestItems.add(0, item);
		
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	public List<Item> getNewestItems(){
		return newestItems;
	}
}
