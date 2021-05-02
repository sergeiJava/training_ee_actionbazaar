package com.actionbazaar.account;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Session Bean implementation class UserServiceBean
 */
@Stateless
public class UserServiceBean implements UserService {

	@PersistenceContext(unitName = "BusLogic_XA")
	private EntityManager entityManager;
	
	@Resource
	private SessionContext context;
    /**
     * Default constructor. 
     */
    public UserServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public User getUser(long userId) {
		return entityManager.find(BazaarAccount.class, userId);
	}

	@Override
	public User getUser(String username) {
		TypedQuery<User> q = entityManager.createQuery("select u from User u where u.username = :username", User.class);
		//Query q = entityManager.createQuery("select u from User u where u.username = :username");
        q.setParameter("username",username);
        List<User> user = q.getResultList();
        if(user.size() == 1) {
        	return user.get(0);
        } else if(user.size() > 1) {
        	throw new RuntimeException("Username should be unique");
        }
        return null;
	}
	
	
	@Override
	public void createUser(User user) {
		String uid = UUID.randomUUID().toString();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		entityManager.persist(user);
		
	}

	@Override
	public boolean isUserExist(String username) {
		Query query = entityManager.createQuery("select count(u) from User u where u.username = :username");
		query.setParameter("username", username);
		Long count = (Long) query.getSingleResult();
		return ((count.equals(0L)) ? false : true); 
	}

	@Override
	public User getAuthenticatedUser() {
		Principal principal = context.getCallerPrincipal();
		User user;
		String name = principal.getName();
		if(name.equals("ANONYMOUS")) {
			user = new AnonymousUser();
		} else {
			user = this.getUser(name);
			if(context.isCallerInRole("CSR")) {
				//calller is in CSR role
			}
		}
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		Principal p = context.getCallerPrincipal();
		if(p != null) {
			if(p.getName() != null && p.getName().length() > 0 && !p.getName().toUpperCase().equals("ANONYMOUS")) {
				return true;
			}
		}
		return false;
	}



}
