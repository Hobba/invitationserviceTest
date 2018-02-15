package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import com.invitationService.models.Creator;
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

	public void sendMail(Creator creator) {
		Email email = new Email();
		email.setAddress(creator.getEmail());
		email.setSubject("You created a new survey");
		email.setContent(parseEmail());
		sendMailToAddress(email);
	}

	public void sendMail(Survey survey) {
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject("You were invited to participate in a survey by " + survey.getCreator().getName());
			email.setContent(parseEmail());
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

	private String parseEmail() {
		ClassLoader cl = getClass().getClassLoader();
		InputStream is = cl.getResourceAsStream("static/tmpl/emailTemplate.html");

		return inputstreamToString(is);
	}

	private String inputstreamToString(InputStream is) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer, "UTF-8");
		} catch (IOException e) {
			return "ERROR";
		}
		return writer.toString();
	}

}