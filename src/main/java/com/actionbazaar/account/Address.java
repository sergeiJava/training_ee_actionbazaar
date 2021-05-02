package com.actionbazaar.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.actionbazaar.State;

@Embeddable
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 5, max = 30, message = "{streetNameSize}")
	private String streetName1;
	
	//protected String streetName2;
	
	@NotNull
	@Size(min = 2, max = 30, message = "{citySize}")
	private String city;
	
	@NotNull
	private State state;
	
	@NotNull
	@Size(min = 5, max = 5, message = "{zipCodeSize}")
	private String zipCode;
	
	@Size(min = 10, max = 10)
	protected String phone;
	
	public Address() {
		super();
	}
	
    public Address(String streetName1, String city, State state, String zipCode, String phone) {
        this.streetName1 = streetName1;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
    }

	public String getStreetName1() {
		return streetName1;
	}

	public void setStreetName1(String streetName1) {
		this.streetName1 = streetName1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE_CODE")
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(name = "ZIP_CODE")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
