package com.invitationService.services;

import com.invitationService.models.Survey;

public interface EmailService {

	void sendCreationMailToCreator(Survey survey);

	void sendInviteToParticipants(Survey survey);

	void sendReminderToParticipants(Survey survey);

}