package com.invitationService.services;

import com.invitationService.models.Creator;
import com.invitationService.models.Survey;

public interface EmailService {
	
	void sendMail(Creator creator);

	void sendMail(Survey survey);

}