package com.invitationService.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Survey {
	private String id;

	private String title;
	private String description;
	
	private Settings settings;
	
	private Creator creator;
	private String creationDate;

	private List<Participant> participants;
	private List<Answer> answer; 
	private List<Question> questions;
	
	private Object participantEmail;
}