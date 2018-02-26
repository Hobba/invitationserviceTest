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
		} else {
			email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR_UNREGISTERED));
		}

		email.setContent(email.getContent().replaceAll("\\$\\{CREATORLINK\\}", designservice_base_url + "c/?creator="
				+ tokenService.createCreatorJWT("id", "invitationservice", "email", creator.getEmail())));

		return sendMailToAddress(email);
	}

	public int sendInviteToParticipants(Survey survey) {

		Set<Participant> part_list = new HashSet<Participant>();

		int successfullSendCounter = 0;
		for (Participant p : survey.getParticipants()) {

			if (!part_list.contains(p)) {
				part_list.add(p);

				// TODO create token -done
				// TODO write token and email to DB
				// TODO send token with email -done
				// TODO surveyservice: check if token is used

				String token = tokenService.createUserJWT("", "IS", "surveyInvitation", p.getEmail(), survey.getId());
				p.setToken(token);
				p.setSurvey_id(survey.getId());
				
				dao.insertParticipant(p);

				Email email = new Email();
				email.setAddress(p.getEmail());
				email.setSubject("Du wurdest von " + survey.getCreator().getName()
						+ " eingeladen, an einer Umfrage teilzunehmen");
				email.setContent(getEmailContent(TEMPLATE_TYPE.PARTICIPANTS));
				email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
				email.getContent().replaceAll("\\$\\{CREATORNAME\\}", getCreatorName(survey.getCreator()));
				email.getContent().replaceAll("\\$\\{GREETING\\}", survey.getGreeting());
				email.setContent(
						email.getContent().replaceAll("\\$\\{USERLINK\\}", surveyservice_base_url + "?user=" + token));

				if (sendMailToAddress(email)) {
					successfullSendCounter++;
				} else {
					LOGGER.info("Could not send email to: " + email.getAddress());
				}

			}
		}
		return successfullSendCounter;
	}

	public int sendReminderToParticipants(Survey survey) {
		int successfullSendCounter = 0;
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject(
					"Hast du vergessen an der Umfrage von " + getCreatorName(survey.getCreator()) + " teilzunehmen?");
			email.setContent(getEmailContent(TEMPLATE_TYPE.REMINDER));
			email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
			email.getContent().replaceAll("\\$\\{CREATORNAME\\}", getCreatorName(survey.getCreator()));
			email.setContent(email.getContent().replaceAll("\\$\\{USERLINK\\}", "http://userlink.de"));

			if (sendMailToAddress(email)) {
				successfullSendCounter++;
			} else {
				LOGGER.info("Could not send email to: " + email.getAddress());
			}
		}
		return successfullSendCounter;
	}

	private boolean sendMailToAddress(Email email) {
		try {
			Unirest.post(mailgun_url + "/messages").basicAuth("api", mailgun_key).queryString("from", mailgun_from)
					.queryString("to", email.getAddress()).queryString("subject", email.getSubject())
					.queryString("html", email.getContent()).asJson();
			return true;
		} catch (UnirestException | RuntimeException e) {
			LOGGER.warn("Couldn't send email due to mail server connection issues", e);
			return false;
			// TODO: (JAN) Retry to send email
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