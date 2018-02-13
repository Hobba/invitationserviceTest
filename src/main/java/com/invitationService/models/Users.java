package com.invitationService.models;

import java.util.List;


public class Users {
	private String name;
	private List<User> Users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return Users;
	}

	public void setUsers(List<User> users) {
		Users = users;
	}
	
	
}
