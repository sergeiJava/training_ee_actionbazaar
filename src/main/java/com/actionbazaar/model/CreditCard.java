package com.actionbazaar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.actionbazaar.account.BillingInfo;

@Entity
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name = "BILLING_ID")
public class CreditCard extends BillingInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Size(min = 3, max = 45, message = "{step3_nameOnCardRequired}")
	private String nameOnCard;
	
	@Size(min = 3, max = 45, message = "{step3_accountNumberRequired}")
	private String accountNumber;

	@NotNull
	@Size(min = 2, max = 2, message = "{step3_twoDigitExpirationMonth}")
	@DecimalMin(value = "1", message = "{step3_minimumMonthValue}")
	@DecimalMax(value = "12", message = "{step3_maximumMonthValue}")
	@Digits(integer = 2, fraction = 0, message = "{step3_invalidExpirationMonth}")
	private String expirationMonth;
	
	@NotNull
	@Size(min = 4, max = 4, message = "{step3_twoDigitExpirationYear}")
	@DecimalMin(value = "2020", message = "{step3_MinimumYearValue}")
	@Digits(integer = 4, fraction = 0, message = "{step3_invalidExpirationYear}")
	private String expirationYear;
	
	@Size(min = 3, max = 4, message = "{step3_securityCode}")
	@Digits(integer = 4, fraction = 0, message = "{step3_securityCode}")
	private String securityCode;
	
	@Column(name = "CREDITCARDTYPE", columnDefinition = "NVARCHAR2(100)")
	private CreditCardType creditCardType;


	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	
}
