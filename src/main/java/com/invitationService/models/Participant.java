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

	protected String email;
	protected String token;
	protected String survey_id;
	protected Boolean hasAnswered;

}