package com.actionbazaar.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.actionbazaar.model.Item;
import com.actionbazaar.model.Order;

/**
 * An account that can sell and/orbid on items
 * @author test
 *
 */
@Entity
@DiscriminatorValue(value = "A")
public class BazaarAccount extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<BillingInfo> billingInfo = new HashSet<BillingInfo>();
	
	@OneToMany
	private Set<Order> orders;
	
	@Column(name = "COMM_RATE")
	private BigDecimal commissionRate;
	
	@Column(name = "MAX_ITEMS")
	private long maxItemsAllowed;
	
	/**
	 * Items this seller is selling
	 */
	@OneToMany(mappedBy = "seller")
	private Set<Item> itemsSelling;
	
	/**
	 * DEfault constructor for JPA/JSF
	 */
	public BazaarAccount() {
		
	}
	
	/**
	 * Construct a new user given basic information
	 * @param firstName - first name
     * @param lastName - last name
     * @param username - username
     * @param password - password
     * @param address - address information
     * @param dateCreated - date on which the account was created
     * @param accountVerified  - flag indicating
	 */
	public BazaarAccount(String firstName, String lastName, String username, String password, Address address, Date createdDate,
			boolean accountVerified) {
		super(firstName, lastName, username, password, address, createdDate, accountVerified);
	}

	public Set<BillingInfo> getBillingInfo() {
		return billingInfo;
	}

	public void addBillingInfo(BillingInfo billingInfo) {
		this.billingInfo.add(billingInfo);
	}
	
	public void setBillingInfo(Set<BillingInfo> billingInfo) {
		this.billingInfo = billingInfo;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public long getMaxItemsAllowed() {
		return maxItemsAllowed;
	}

	public void setMaxItemsAllowed(long maxItemsAllowed) {
		this.maxItemsAllowed = maxItemsAllowed;
	}

	public Set<Item> getItemsSelling() {
		return itemsSelling;
	}

	public void setItemsSelling(Set<Item> itemsSelling) {
		this.itemsSelling = itemsSelling;
	}
	
	public Item addItem(Item item) {
		getItemsSelling().add(item);
		item.setSeller(this);
		return item;
	}
	
	public Item removeItem(Item item) {
		getItemsSelling().remove(item);
		item.setSeller(null);
		return item;
	}
}
