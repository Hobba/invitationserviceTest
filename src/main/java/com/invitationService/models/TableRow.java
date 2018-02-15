package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableRow extends Creator {

	protected Boolean Status;

	public TableRow(int Id, String Name, String Email, Boolean Status) {
		this.Id = Id;
		this.Name = Name;
		this.Email = Email;
		this.Status = Status;
	}

}
