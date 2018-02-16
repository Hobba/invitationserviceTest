package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import com.invitationService.models.Email;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MailgunEmailService implements EmailService {

	@Value("${mailgun.api.url}")
	private String mailgun_url;

	@Value("${mailgun.api.key}")
	private String mailgun_key;

	@Value("${mailgun.api.from}")
	private String mailgun_from;

	public void sendCreationMailToCreator(Survey survey) {
		Email email = new Email();
		email.setAddress(survey.getCreator().getEmail());
		email.setSubject("You created a new survey");
		email.setContent(getEmailContent(TEMPLATE_TYPE.CREATOR));
		email.setContent(email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle()));
		email.setContent(email.getContent().replaceAll("\\$\\{CREATORNAME\\}", survey.getCreator().getName()));

		sendMailToAddress(email);
	}

	public void sendInviteToParticipants(Survey survey) {
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject("You were invited to participate in a survey by " + survey.getCreator().getName());
			email.setContent(getEmailContent(TEMPLATE_TYPE.PARTICIPANTS));
			email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
			email.getContent().replaceAll("\\$\\{CREATORNAME\\}", survey.getCreator().getName());

			sendMailToAddress(email);
		}
	}

	public void sendReminderToParticipants(Survey survey) {
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject("You were invited to participate in a survey by " + survey.getCreator().getName());
			email.setContent(getEmailContent(TEMPLATE_TYPE.REMINDER));
			email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
			email.getContent().replaceAll("\\$\\{CREATORNAME\\}", survey.getCreator().getName());

			sendMailToAddress(email);
		}
	}

	private JsonNode sendMailToAddress(Email email) {

		HttpResponse<JsonNode> request = null;

		try {
			request = Unirest.post(mailgun_url + "/messages").basicAuth("api", mailgun_key)
					.queryString("from", mailgun_from).queryString("to", email.getAddress())
					.queryString("subject", email.getSubject()).queryString("html", email.getContent()).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return request.getBody();
	}

	private String getEmailContent(TEMPLATE_TYPE template) {
		ClassLoader cl = getClass().getClassLoader();

		System.out.println(template.toString());

		switch (template) {
		case CREATOR:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Creator.html"));
		case PARTICIPANTS:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Participants.html"));
		case REMINDER:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate_Reminder.html"));
		default:
			return inputStreamToString(cl.getResourceAsStream("static/tmpl/emailTemplate.html"));
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