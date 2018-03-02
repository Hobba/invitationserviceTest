package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.invitationService.invitationService.CreatorDAO;
import com.invitationService.models.Creator;
import com.invitationService.models.Email;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;
import com.invitationService.tokenmaster.TokenService;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MailgunEmailService implements EmailService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CreatorDAO dao;

	@Value("${mailgun.api.url}")
	private String mailgun_url;

	@Value("${mailgun.api.key}")
	private String mailgun_key;

	@Value("${mailgun.api.from}")
	private String mailgun_from;

	@Value("${invitationservice.base.url}")
	private String base_url;

	@Value("${designservice.base.url}")
	private String designservice_base_url;

	@Value("${surveyservice.base.url}")
	private String surveyservice_base_url;

	private final Logger LOGGER = LoggerFactory.getLogger(MailgunEmailService.class);

	public boolean sendAccountMailToCreator(Creator creator, boolean isRegistered) {
		Email email = new Email();
		email.setAddress(creator.getEmail());
		email.setSubject("SimQue: Deine Registrierung");

		if (isRegistered) {
			email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR_REGISTERED));
			LOGGER.info("Das  Emailtemplate für einen bereits registrierten Creator wurde aufgerufen");
		} else {
			email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR_UNREGISTERED));
			LOGGER.info("Das  Emailtemplate für einen NEUEN Creator wurde aufgerufen");
		}

		email.setContent(email.getContent().replaceAll("\\$\\{CREATORLINK\\}", designservice_base_url + "c/?creator="
				+ tokenService.createCreatorJWT("id", "invitationservice", "email", creator.getEmail())));

		LOGGER.info("Eine Mail für einen Creator {} wurde erstellt", creator.getEmail());

		return sendMailToAddress(email);
	}

	public int sendInviteToParticipants(Survey survey) {

		Set<Participant> part_list = new HashSet<Participant>();

		int successfullSendCounter = 0;
		for (Participant p : survey.getParticipants()) {

			if (!part_list.contains(p)) {
				part_list.add(p);

				String token = tokenService.createUserJWT("", "IS", "surveyInvitation", p.getEmail(), survey.getId());
				p.setHasAnswered(false);
				p.setToken(token);
				p.setSurvey_id(survey.getId());

				LOGGER.info("Es wird versucht, der Teilnehmer {} für die Umfrage {} in die DB zu schreiben",
						p.getEmail(), survey.getId());
				dao.insertParticipant(p);

				Email email = new Email();
				email.setAddress(p.getEmail());
				email.setSubject("Du wurdest von " + survey.getCreator().getName()
						+ " eingeladen, an einer Umfrage teilzunehmen");
				email.setContent(getEmailContent(TEMPLATE_TYPE.PARTICIPANTS));
				email.setContent(email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle()));
				email.setContent(
						email.getContent().replaceAll("\\$\\{CREATORNAME\\}", getCreatorName(survey.getCreator())));
				email.setContent(
						email.getContent().replaceAll("\\$\\{GREETING\\}", survey.getSettings().getGreeting()));
				email.setContent(
						email.getContent().replaceAll("\\$\\{USERLINK\\}", surveyservice_base_url + "?user=" + token));
				LOGGER.info("Eine Email für einen Teilnehmer wurde erstellt");

				if (sendMailToAddress(email)) {
					LOGGER.info("Eine Email wurde an {} gesendet", email.getAddress());
					successfullSendCounter++;
				} else {
					LOGGER.info("Could not send email to: " + email.getAddress());
				}

			} else {
				LOGGER.info(
						"Es wurde versucht, die Email {} doppelt als Teilnehmer einzutragen und dies wurde verhindet",
						p.getEmail());
			}
		}
		LOGGER.info("Es wurden Emails an {} Teilnehmer gesendet", successfullSendCounter);
		return successfullSendCounter;
	}

	public int sendReminderToParticipants(Survey survey) {
		int successfullSendCounter = 0;
		for (Participant p : survey.getParticipants()) {
			if (!p.getHasAnswered()) {
				String token = tokenService.createUserJWT("", "IS", "surveyInvitation", p.getEmail(), survey.getId());
				Email email = new Email();
				email.setAddress(p.getEmail());
				email.setSubject(
						"Hast du vergessen an der Umfrage von " + survey.getCreator().getName() + " teilzunehmen?");
				email.setContent(getEmailContent(TEMPLATE_TYPE.REMINDER));
				email.setContent(email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle()));
				email.setContent(
						email.getContent().replaceAll("\\$\\{CREATORNAME\\}", getCreatorName(survey.getCreator())));
				// email.setContent(
				// email.getContent().replaceAll("\\$\\{GREETING\\}",
				// survey.getSettings().getGreeting()));
				email.setContent(
						email.getContent().replaceAll("\\$\\{USERLINK\\}", surveyservice_base_url + "?user=" + token));
				LOGGER.info("Eine Email für einen Teilnehmer wurde erstellt");

				if (sendMailToAddress(email)) {
					LOGGER.info("Eine Email wurde an {} gesendet", email.getAddress());
					successfullSendCounter++;
				} else {
					LOGGER.info("Could not send email to: " + email.getAddress());
				}
			}
		}
		LOGGER.info("Es wurden Emails an {} Teilnehmer gesendet", successfullSendCounter);
		return successfullSendCounter;
	}

	private boolean sendMailToAddress(Email email) {
		try {
			Unirest.post(mailgun_url + "/messages").basicAuth("api", mailgun_key).queryString("from", mailgun_from)
					.queryString("to", email.getAddress()).queryString("subject", email.getSubject())
					.queryString("html", email.getContent());
			return true;
		} catch (RuntimeException e) {
			LOGGER.warn("Couldn't send email due to mail server connection issues", e);
			return false;
		}
	}

	private String getEmailContent(TEMPLATE_TYPE template) {
		ClassLoader cl = getClass().getClassLoader();

		switch (template) {
		case CREATOR_UNREGISTERED:
			LOGGER.info("Das CreatorEmail Template wurde abgerufen");
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Creator.html"));
		case CREATOR_REGISTERED:
			LOGGER.info("Das CreatorAlreadyRegistered Template wurde abgerufen");
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Creator_Registered.html"));
		case PARTICIPANTS:
			LOGGER.info("Das TeilnehmerEmail Template wurde abgerufen");
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Participants.html"));
		case REMINDER:
			LOGGER.info("Das TeilnehmerReminderEmail Template wurde abgerufen");
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Reminder.html"));
		default:
			LOGGER.warn("Couldn't get template files, falling back to default file.");
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate.html"));
		}
	}

	private String getCreatorName(Creator creator) {
		if (creator.getName() == null) {
			LOGGER.warn("Creator has no Name field, please provide at least a empty name field.");
			return creator.getEmail();
		}
		if (creator.getName().isEmpty()) {
			return creator.getEmail();
		} else {
			return creator.getName();
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