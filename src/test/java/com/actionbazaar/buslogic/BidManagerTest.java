package com.actionbazaar.buslogic;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.commons.codec.binary.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.modules.maven.MavenResolver;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.actionbazaar.State;
import com.actionbazaar.account.Address;
import com.actionbazaar.account.BazaarAccount;
import com.actionbazaar.buslogic.exceptions.CreditCardSystemException;
import com.actionbazaar.controller.BidController;
import com.actionbazaar.model.Bid;
import com.actionbazaar.model.Item;
import com.actionbazaar.setup.Bootstrap;
import com.actionbazaar.util.FacesContextProducer;

@RunWith(Arquillian.class)
public class BidManagerTest {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction transaction;
	
	@Deployment
	public static Archive<?> createDeployment(){
		MavenResolverSystem resolver = Maven.resolver();
		
		
		
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war")
				.addClass(Base64.class)
				.addClass(BaseNCodec.class)
	            .addPackage(State.class.getPackage())   
	            .addPackage(Address.class.getPackage())    
	            .addPackage(BidManagerBean.class.getPackage())    
	            .addPackage(CreditCardSystemException.class.getPackage()) 
	            .addPackage(BidController.class.getPackage())
	            .addPackage(Bootstrap.class.getPackage()) 
	            .addPackage(FacesContextProducer.class.getPackage())   
	            .addPackage(Bid.class.getPackage())
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
	            .addAsLibraries(resolver.loadPomFromFile("pom.xml")
	            		.importCompileAndRuntimeDependencies().resolve("commons-codec:commons-codec").withTransitivity().asFile());

	        System.out.println(wa.toString(true));
	        return wa;
	        
	        
	        
	}
	
	/**
	 * Start the transaction
	 * @throws NotSupportedException
	 * @throws SystemException
	 */
	@Before
	public void startTransaction() throws Exception {
		System.out.println("Before starts.....");
		transaction.begin();
		em.joinTransaction();
		System.out.println("Before ended.....");
	}
	
	/**
	 * Commits the transaction
	 * @throws Exception
	 */
	@After
	public void commitTransaction() throws Exception {
		transaction.commit();
	}
	
	/**
	 * Tests creating and persisting an Item
	 */
	@Test
	public void testCreates() {
		System.out.println("Test running");
		/*
		 * Create an Item and persist it
		 */
		Calendar endDate = new GregorianCalendar(2021, 6, 1, 12, 0);
		Item item = new Item("Hobie Holder 14", endDate.getTime(), new Date(), new Date(), 14.0);
		Address address = new Address("street", "city", State.California, "90210", "555-555-5555");
		em.persist(item);
		
		/*
		 * Create a bidder and persist it
		 */
		BazaarAccount bidder = new BazaarAccount("rcupark", "password", "Ryan", "Cupark", address, new Date(), false);
		bidder.setEmail("sdvita@list.ru");
		em.persist(bidder);
		
		/*
		 * Create a bid on the item
		 */
		
		Bid bid = new Bid(bidder, item, 15.0);
		em.persist(bid);
	}
}
