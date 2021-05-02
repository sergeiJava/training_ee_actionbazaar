package com.actionbazaar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;

import com.actionbazaar.account.BazaarAccount;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity
@Named
@RequestScoped
@Table(name = "ITEMS")
@NamedQueries({
	@NamedQuery(name = "Item.count", query = "SELECT COUNT(i) FROM Item i"),
	@NamedQuery(name="Item.all", query="SELECT i FROM Item i order by i.itemName asc")
	
})
public class Item implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ITEM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId;
	
	@Column(name = "ITEM_NAME", nullable = false)
	private String itemName;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date bidEndDate;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date bidStartDate;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;
	
	private Double initialPrice;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
	private List<Bid> bids;
	
	@ManyToOne
	@JoinColumn(name = "SELLEER_ID", referencedColumnName = "USER_ID")
	private BazaarAccount seller;
	
	@ManyToMany(mappedBy = "items")
	private Set<Category> category;
	
	public Item() {
		super();
	}
	
	public Item(Long itemId) {
		this.itemId = itemId;
	}
	
	public Item(String itemName, Date bidEndDate, Date bidStartDate, Date createdDate, Double initialPrice) {
        this.bidEndDate = bidEndDate;
        this.itemName = itemName;
        this.bidStartDate = bidStartDate;
        this.initialPrice = initialPrice;
        this.createdDate = createdDate;
    }

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(Date bidEndDate) {
		this.bidEndDate = bidEndDate;
	}

	public Date getBidStartDate() {
		return bidStartDate;
	}

	public void setBidStartDate(Date bidStartDate) {
		this.bidStartDate = bidStartDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
   
	public Bid addBid(Bid bid) {
		getBids().add(bid);
		return bid;
	}
	
	public Bid removeBid(Bid bid) {
		getBids().remove(bid);
		return bid;
	}
	
	public void setSeller(BazaarAccount seller) {
		this.seller = seller;
	}
	
	public BazaarAccount getSeller() {
		return seller;
	}
	
	public void setCategory(Set<Category> category) {
		this.category = category;
	}
	
	public Set<Category> getCategory(){
		return this.category;
	}
	
	public Category addCategory(Category category) {
		getCategory().add(category);
		return category;
	}
	
	public Category removeCategory(Category category) {
		this.category.remove(category);
		return category;
	}
	
	@Override
	public String toString() {
		return itemName;
	}
}
