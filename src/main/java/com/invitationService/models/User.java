package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
	@Override
	public String toString() {
		return "User [Id=" + Id + ", Firstname=" + Firstname + ", Lastname=" + Lastname + ", Email=" + Email + "]";
	}
	protected int Id;
	protected String Firstname;
	protected String Lastname;
	protected String Email;
}
