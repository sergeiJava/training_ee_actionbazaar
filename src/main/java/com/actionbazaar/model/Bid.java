package com.actionbazaar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.actionbazaar.account.BazaarAccount;

/**
 * Entity implementation class for Entity: Bid
 *
 */

@Entity
@Table(name = "BIDS")

public class Bid implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "BID_DATE")
	private Date bidDate;
	
	@Id
	@GeneratedValue
	@Column(name = "BID_ID")
	private Long bidId;
	
	@Column(name = "BID_PRICE")
	private Double bidPrice;
	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private BazaarAccount bazaarAccount;

	public Bid() {
		super();
	}

	public Bid(BazaarAccount bazaarAccount, Item item, Double bidPrice) {
		this.bazaarAccount = bazaarAccount;
		this.item = item;
		this.bidPrice = bidPrice;
	}


	
	public Date getBidDate() {
		return bidDate;
	}



	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}



	public Long getBidId() {
		return bidId;
	}



	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}



	public Double getBidPrice() {
		return bidPrice;
	}



	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}


	public Item getItem() {
		return item;
	}



	public void setItem(Item item) {
		this.item = item;
	}
	
	
   public BazaarAccount getBidder() {
	   return bazaarAccount;
   }
	
	public void setBidder(BazaarAccount bazaarAccount) {
		this.bazaarAccount = bazaarAccount;
	}
	
	@Override
	public String toString() {
		return "Bid: " + item.getItemName() + " " + bidPrice; 
	}
}
