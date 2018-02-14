package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableRow extends User{	

	private Boolean Status;
	public TableRow(int Id, String Firstname, String Lastname, String Email, Boolean Status) {
		this.Id = Id;
		this.Firstname = Firstname;
		this.Lastname = Lastname;
		this.Email = Email;
		this.Status = Status;
	}

}
