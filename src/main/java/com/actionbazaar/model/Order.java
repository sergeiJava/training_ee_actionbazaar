package com.actionbazaar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.actionbazaar.account.Bidder;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private Long orderId;
	
	@ManyToOne
	@JoinColumn
	private Bidder bidder;
	
	@ManyToOne 
	@JoinColumn
	private Item item;
	
	@ManyToOne
	@JoinColumn
	private Shipping shipping;
	
	@ManyToOne
	@JoinColumn
	private Billing billing;
	
	@ManyToOne
	@JoinColumn
	private CreditCard creditCard;
	
	@Enumerated
	private OrderStatus orderStatus;

	
	/**
     * Default constructor for JPA
     */
    public Order() {
        // Default constructor
    }

    /**
     * Creates a new order
     * @param item  - item in the order
     * @param bidder - bidder
     * @param creditCard - credit card
     */
    public Order(Item item, Bidder bidder, CreditCard creditCard) {
        this.item = item;
        this.bidder = bidder;
        this.creditCard = creditCard;
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
    
    

	
	
}
