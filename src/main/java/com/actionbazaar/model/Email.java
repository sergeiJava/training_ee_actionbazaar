package com.actionbazaar.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Email
 *
 */
@Entity
@Table(name = "EMAIL")
public class Email implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMAIL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long emailId;
	
	@NotNull
	@Size(min = 3, max = 45)
	private String action;
	
	@NotNull
	@Size(min = 3, max = 45)
	private String subject;
	
	@NotNull
	@Lob
	private String htmlContent;
	
	@NotNull
	@Lob
	private String plainContent;
	
	public Email() {
		super();
	}
	
	public Email(String action, String subject, String htmlContent) {
		this.action = action;
		this.subject = subject;
		this.htmlContent = htmlContent;
	}

	public long getEmailId() {
		return emailId;
	}

	public void setEmailId(long emailId) {
		this.emailId = emailId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getPlainContent() {
		return plainContent;
	}

	public void setPlainContent(String plainContent) {
		this.plainContent = plainContent;
	}
   
}
