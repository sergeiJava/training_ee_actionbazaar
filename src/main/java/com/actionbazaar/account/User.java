package com.actionbazaar.account;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;





/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)

@NamedNativeQueries({
	@NamedNativeQuery(name = "getUserByUsername",query = "SELECT U FROM USERS U WHERE U.USERNAME = ?", resultClass = User.class)
})
public abstract class User implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("User");

	@Id
	@SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private byte[] picture;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date birthDate;
	
	@NotNull
	@NotEmpty
	@Email
	private String email;
	
	@Column(name = "GUEST", columnDefinition = "NUMBER(1)")
	private boolean guest;
	
	@Column(name = "ACCOUNTVERIFIED", columnDefinition = "NUMBER(1)")
	private boolean accountVerified;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date created;
	
	@Column(name = "PWD")
	private String password;
	
	@Embedded
	@AttributeOverride(name = "streetName1", column = @Column(name = "STREET"))
	private Address address;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Group> groups = new HashSet<Group>();

	public User() {
		address = new Address();
	}
   
	public User(String firstName, String lastName, String username, String password, Address address, Date createdDate, boolean accountVerified) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		if(password !=null ) {
			createPassword(password);
		}
		this.address=address;
		this.created = createdDate;
		this.accountVerified = accountVerified;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.toUpperCase();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public void createPassword(String password) {
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			String encodedPassword = Base64.encodeBase64String(digest);
			//String encodedPwd = Base64Encoder.encode(digest);
			this.password = encodedPassword;
			
		} catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE,"Password creation failed",e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "Password creation failed", e);
            throw new RuntimeException(e);
        }
		
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public boolean isAccountVerified() {
		return accountVerified;
	}
	
	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}
	
	public void setGuest(boolean guest) {
		this.guest = guest;
	}
	
	public boolean isGuest() {
		return guest;
	}
	
	
	public Set<Group> getGroups() {
		return groups;
	}

	public void addGroup(String groupName) {
		Group grp = new Group(groupName);
		grp.setUsername(this.username);
		groups.add(grp);
		logger.info("Group added");
        
    }

	public String getDisplay() {
		if(guest)
			return "Guest";
		return firstName;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isAnonymous() {
		return false;
	}
	
	
}
