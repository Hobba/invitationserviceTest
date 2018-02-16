package com.invitationService.services;

import com.invitationService.models.Creator;
import com.invitationService.models.Survey;

public interface EmailService {

	void sendAccountMailToCreator(Creator creator);

	void sendInviteToParticipants(Survey survey);

	void sendReminderToParticipants(Survey survey);

}