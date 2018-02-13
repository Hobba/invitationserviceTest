package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	protected int Id;
	protected String Firstname;
	protected String Lastname;
	protected String Email;
}
