package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Participant {
	
	protected int Id;

	protected String email;
	protected Boolean Status;

}