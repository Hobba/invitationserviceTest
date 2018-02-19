package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.invitationService.models.Creator;
import com.invitationService.models.Email;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;

public class LocalEmailService implements EmailService {

	@Value("${invitationservice.base.url}")
	private String base_url;

	@Value("${designservice.base.url}")
	private String designservice_base_url;

	private final Logger LOGGER = LoggerFactory.getLogger(LocalEmailService.class);

	public void sendAccountMailToCreator(Creator creator, boolean isRegistered) {
		Email email = new Email();
		email.setAddress(creator.getEmail());
		email.setSubject("SimQue: Deine Registrierung");

		if (isRegistered) {
			email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR_REGISTERED));
		} else {
			email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR_UNREGISTERED));
		}

		email.setContent(email.getContent().replaceAll("\\$\\{CREATORLINK\\}", designservice_base_url));

		LOGGER.info(email.getContent());
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
			email.setContent(email.getContent().replaceAll("\\$\\{USERLINK\\}", "http://userlink.de/"));

			LOGGER.info(email.getContent());
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
			email.setContent(email.getContent().replaceAll("\\$\\{USERLINK\\}", "http://userlink.de"));

			LOGGER.info(email.getContent());
		}
	}

	private String getEmailContent(TEMPLATE_TYPE template) {
		ClassLoader cl = getClass().getClassLoader();

		switch (template) {
		case CREATOR_UNREGISTERED:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Creator.html"));
		case CREATOR_REGISTERED:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Creator_Registered.html"));
		case PARTICIPANTS:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Participants.html"));
		case REMINDER:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Reminder.html"));
		default:
			LOGGER.warn("Couldn't get template files, falling back to default file.");
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
			LOGGER.warn("Couldn't copy InputStream to StringWriter", e);
		}
		return writer.toString();
	}

	private enum TEMPLATE_TYPE {
		CREATOR_UNREGISTERED, CREATOR_REGISTERED, PARTICIPANTS, REMINDER;
	}

}