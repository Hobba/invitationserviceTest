package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableRow {

	private int Id;
	private String Name;
	private String Email;

	private Boolean Status;

}
