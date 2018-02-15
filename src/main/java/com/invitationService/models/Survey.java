package com.invitationService.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Survey {
	protected String id;

	protected String title;

	protected String greeting;
	protected Creator creator;
	protected String creationDate;

	protected List<Participant> participants;
}