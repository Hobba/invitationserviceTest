package com.invitationService.services;

import com.invitationService.models.Creator;
import com.invitationService.models.Survey;

public interface EmailService {

	boolean sendAccountMailToCreator(Creator creator, boolean isRegistered);

	int sendInviteToParticipants(Survey survey);

	int sendReminderToParticipants(Survey survey);

}