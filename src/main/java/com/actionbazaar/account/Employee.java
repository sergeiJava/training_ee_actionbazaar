package com.actionbazaar.account;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Employees
 *
 */
@Entity
@Table(name = "EMPLOYEES")
@DiscriminatorValue(value = "E")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Named
@RequestScoped
public class Employee extends User implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	
	public Employee() {
	}
 
    public Employee(String firstName, String lastName, String username, String password, Date dateCreated, String title) {
        super(firstName, lastName, username, password, null, dateCreated, true); 
        this.title = title;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    
}
