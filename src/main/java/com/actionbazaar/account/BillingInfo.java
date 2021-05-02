package com.actionbazaar.account;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: BillingInfo
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BILLING_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class BillingInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BILLING_ID")
	private Long userId;
	
	@ManyToOne
	@JoinColumn
	private BazaarAccount bidder;
	
	public BillingInfo() {
		super();
	}
   
}
