package com.invitationService.services;

import com.invitationService.models.Survey;

public interface EmailService {
	
	void sendMailToCreator(Survey survey);

	void sendMailToParticipants(Survey survey);

}