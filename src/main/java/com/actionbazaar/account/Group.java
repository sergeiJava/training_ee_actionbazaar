package com.actionbazaar.account;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the "GROUPS" database table.
 * 
 */
@Entity
@Table(name="GROUPS")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column(name="group_pk")
    private Long userId;

	@Column
	private String username;

	@ManyToOne
	@JoinColumn
	private User user;
	
	@Column(name="groupid")
    private String groupId;
	
	protected Group() {
	}

	/**
     * Creates a new group with the specified ID
     * @param groupId - group id
     */
    public Group(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Sets the group ID
     * @param groupId - groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username - username
     */
    protected void setUsername(String username) {
        this.username = username;
    }
}