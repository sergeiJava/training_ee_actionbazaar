package com.actionbazaar.buslogic;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.actionbazaar.model.Category;
import com.actionbazaar.model.Item;

/**
 * Session Bean implementation class ItemManager
 */
@Stateless

public class ItemManager {

	@PersistenceContext(unitName = "BusLogic_XA")
	EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public ItemManager() {
        // TODO Auto-generated constructor stub
    }
    
    public void createCategory(Category category) {
    	entityManager.persist(category);
    }
    
    public List<Category> getCategories(){
    	TypedQuery<Category> query = entityManager.createNamedQuery("Categories.findAll", Category.class);
    	return query.getResultList();
    }
    
    public long getItemCount() {
    	Query query = entityManager.createNamedQuery("Item.count", Long.class);
    	System.out.println(">>>>>>>>>Item Conunt: " + query.getSingleResult());
    	return (Long) query.getSingleResult();
    }

    public void getItem(long itemId) {
    	
    }
    
    public void save(Item item) {
    	entityManager.persist(item);
    }
    
    public List<Item> getNewestItems(){
    	return list();
    }
    
    public List<Item> list(){
    	TypedQuery<Item> query = entityManager.createNamedQuery("Item.all", Item.class);
    	return query.getResultList();
    }
    
    public void createItem(Item item) {
    	
    }
}
