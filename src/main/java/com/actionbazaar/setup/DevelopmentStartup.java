package com.actionbazaar.setup;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.inject.Alternative;

import com.actionbazaar.account.BazaarAccount;
import com.actionbazaar.account.Employee;
import com.actionbazaar.account.UserService;
import com.actionbazaar.buslogic.ItemManager;
import com.actionbazaar.model.Category;
import com.actionbazaar.model.Item;


@Alternative
public class DevelopmentStartup extends SystemStartup{

	private static final Logger logger = Logger.getLogger("DevelopmentStartup");
	
	@EJB
	private UserService userService;
	
	@EJB
	private ItemManager itemManager;

	@Override
	public void innit() {
		logger.info("Development Startup initialized...");
		
		if(userService.getUser("admin") == null) {
			Employee employee = new Employee("Administrator", "Administrator", "admin", "password", new Date(), "Administrator");
			employee.addGroup("admin");
			employee.addGroup("csr");
			userService.createUser(employee);
		}
		
		if(userService.getUser("tester") == null) {
			BazaarAccount tester = new BazaarAccount("High", "Bidder", "tester", "password", null, new Date(), true);
			tester.addGroup("bidder");
			tester.addGroup("seller");
			userService.createUser(tester);
		}
		if(userService.getUser("seller") == null) {
			BazaarAccount seller = new BazaarAccount("Seller", "Seller", "seller", "password", null, new Date(), true);
			seller.addGroup("seller");
			userService.createUser(seller);
		}
		//Add a default category
		List<Category> categories = itemManager.getCategories();
		if(categories.isEmpty()) {
			itemManager.createCategory(new Category("Sailboat", new String[] {"sailing", "boating", "water"}));
			itemManager.createCategory(new Category("Soccer", new String[] {"sports", "summer", "ball"}));
		}
		
		//Insert some starter items into the system
		if(itemManager.getItemCount() == 0) {
			itemManager.save(new Item("J30", new Date(), new Date(), new Date(), 100.0));
			itemManager.save(new Item("Sunfish", new Date(), new Date(), new Date(), 100.0));
		}
	}
	
	
}
