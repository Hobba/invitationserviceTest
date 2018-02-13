package com.invitationService.models;

import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

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
