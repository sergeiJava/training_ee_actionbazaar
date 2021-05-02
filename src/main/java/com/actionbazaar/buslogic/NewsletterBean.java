package com.actionbazaar.buslogic;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

/**
 * Session Bean implementation class NewsletterBean
 */
@Stateless
public class NewsletterBean {

	@PersistenceUnit(unitName = "BusLogic_XA")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public NewsletterBean() {
        // TODO Auto-generated constructor stub
    }

    @Schedule(second = "0", minute = "0", hour = "0", dayOfMonth = "1", month = "*", year = "*")
    
    public void sendMonthlyNewsletter() {
    	// sends out the newsleter
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Schedules({
    	@Schedule(second = "0", minute = "0", hour = "12", dayOfMonth = "last Thu", month = "Nov", year = "*"),
    	@Schedule(second = "0", minute = "0", hour = "12", dayOfMonth = "18", month = "Dec", year = "*")
    	
    })
    public void sendHollydayNewsletter() {
    	//Send Hollyday News letter
    }
}
