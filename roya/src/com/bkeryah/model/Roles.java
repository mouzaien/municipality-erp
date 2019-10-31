package com.bkeryah.model;
//default package
//Generated 22 mars 2014 18:08:30 by Hibernate Tools 3.4.0.CR1

public class Roles {

	/**
	 * 
	 */
	private int id;
	private User user;
	private String roleName;

	public Roles() {
	}
	
	public Roles(User user, String roleName) {
		super();
		this.user = user;;
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



}

