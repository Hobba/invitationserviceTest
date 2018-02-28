package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
	private Object id;
	private int type;
	private Boolean required;
	private String question;
	private Object options; 
}
 