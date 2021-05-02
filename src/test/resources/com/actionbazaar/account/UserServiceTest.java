package com.actionbazaar.account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import javax.ejb.EJB;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {
	
	@EJB
	UserService userService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	System.out.println("Before ALL");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("After All");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Bidder bidder = new Bidder("Bidder1", "bidder1", "Bidder", "Bidder", null, new Date(), true)
		bidder.addGroup("User");
		userService.createUser(bidder);
		
	}

}
