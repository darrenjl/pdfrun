package com.patdivillyfitness.runcoach.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class STGroup {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String ownerEmail;

	@Persistent
	private Date startDate;

	@Persistent
	private String uniqueCode;

	// persisting set of keys instead of list of users because of the way
	// GAE groups entities together and provides ownership.
	@Persistent
	private Set<Long> users;

	@NotPersistent
	private List<STUser> userList;

	public STGroup(String name, Date startDate, int numRounds) {
		super();
		this.name = name;
		this.startDate = startDate;
	}

	public STGroup() {

	}

	public STGroup(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Set<Long> getUsers() {
		return users;
	}

	public void setUsers(Set<Long> users) {
		this.users = users;
	}

	public List<STUser> getUserList() {
		return userList;
	}

	public void setUserList(List<STUser> userList) {
		this.userList = userList;
	}

	public void addUser(Long user) {
		this.users.add(user);
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

}
