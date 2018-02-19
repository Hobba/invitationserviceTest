package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.invitationService.models.Creator;
import com.invitationService.models.Email;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;

public class LocalEmailService implements EmailService {

	final Logger logger = LoggerFactory.getLogger(LocalEmailService.class);

	public void sendAccountMailToCreator(Creator creator) {
		Email email = new Email();
		email.setAddress(creator.getEmail());
		email.setSubject("[SimQue] Du hast dich bei SimQue angemeldet");
		email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR));
		// email.setContent(email.getContent().replaceAll("\\$\\{CREATORLINK\\}",
		// survey.getCreatorLink()));

		logger.info(email.getContent());
	}

	public void sendInviteToParticipants(Survey survey) {
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject(
					"Du wurdest von " + survey.getCreator().getName() + " eingeladen, an einer Umfrage teilzunehmen");
			email.setContent(getEmailContent(TEMPLATE_TYPE.PARTICIPANTS));
			email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
			email.getContent().replaceAll("\\$\\{CREATORNAME\\}", getCreatorName(survey.getCreator()));
			email.getContent().replaceAll("\\$\\{GREETING\\}", survey.getGreeting());
			// email.setContent(email.getContent().replaceAll("\\$\\{USERLINK\\}",
			// survey.getUserLink()));

			logger.info(email.getContent());
		}
	}

	public void sendReminderToParticipants(Survey survey) {
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject(
					"Hast du vergessen an der Umfrage von " + survey.getCreator().getName() + " teilzunehmen?");
			email.setContent(getEmailContent(TEMPLATE_TYPE.REMINDER));
			email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
			email.getContent().replaceAll("\\$\\{CREATORNAME\\}", getCreatorName(survey.getCreator()));
			// email.setContent(email.getContent().replaceAll("\\$\\{USERLINK\\}",
			// survey.getUserLink()));

			logger.info(email.getContent());
		}
	}

	private String getEmailContent(TEMPLATE_TYPE template) {
		ClassLoader cl = getClass().getClassLoader();

		switch (template) {
		case CREATOR:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Creator.html"));
		case PARTICIPANTS:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Participants.html"));
		case REMINDER:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Reminder.html"));
		default:
			// TODO: Throw exception.
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate.html"));
		}
	}

	private String getCreatorName(Creator creator) {
		if (!creator.getName().isEmpty()) {
			return creator.getName();
		} else {
			return creator.getEmail();
		}
	}

	private String inputStreamToString(InputStream is) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer, "UTF-8");
		} catch (IOException e) {
			return "ERROR";
		}
		return writer.toString();
	}

	private enum TEMPLATE_TYPE {
		CREATOR, PARTICIPANTS, REMINDER;
	}

}