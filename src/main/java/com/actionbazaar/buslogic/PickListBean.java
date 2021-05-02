package com.actionbazaar.buslogic;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.actionbazaar.model.Category;

@Named
@Singleton
public class PickListBean {

	private static final Logger logger = Logger.getLogger("PickList");
	
	@PersistenceContext(unitName = "BusLogic_XA")
	private EntityManager entityManager;
	
	public PickListBean() {
	}
	
	public List<Category> getCategories(){
		Query q = entityManager.createNamedQuery("Categories.findCategories");
		List<Category> categories = (List<Category>) q.getResultList();
		return categories;
	}
	
	public List<Category> findCategories(String keywords[]){
		List<Category> categories = new LinkedList<Category>();
		for(String keyword: keywords) {
			Query q = entityManager.createNativeQuery("select category_category_id from keywords where keywords = ?keyword");
			q.setParameter("keyword", keyword);
			List<Long> ids = q.getResultList();
			for (long id: ids) {
				categories.add(entityManager.find(Category.class, id));
			}
		}
		return categories;
	}
	
	public void addCategories(List<Category> categories) {
		for(Category category: categories) {
			entityManager.persist(category);
		}
	}
	
	@PostConstruct
	public void innit() {
		logger.info("Pick List Singleton loaded.....");
	}
}
