package com.actionbazaar.account;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.actionbazaar.model.Order;



/**
 * Entity implementation class for Entity: Bidder
 *
 */
@Entity
@Table(name = "BIDDERS")
@DiscriminatorValue(value = "B")
@PrimaryKeyJoinColumn(name="USER_ID")
public class Bidder extends User implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<BillingInfo> billingInfo = new HashSet<BillingInfo>();
	
	@OneToMany
	private Set<Order> orders;
	
	public Bidder() {
		super();
	}
   
  //  public Bidder(String firstName, String lastName, Long creditRating) {
    //    super(firstName, lastName);
      //  this.creditRating = creditRating;
    //}
	
    public Bidder(String username, String password, String firstName, String lastName,
            Address address, Date dateCreated, boolean accountVerified) {
    	super(firstName,lastName,username, password,address,dateCreated,accountVerified);
}

	
	
	
}
