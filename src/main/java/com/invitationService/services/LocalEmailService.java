package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.invitationService.models.Email;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;

public class LocalEmailService implements EmailService {

	public void sendMailToCreator(Survey survey) {
		Email email = new Email();
		email.setAddress(survey.getCreator().getEmail());
		email.setSubject("You created a new survey");
		email.setContent(getEmailContent("creator"));
		email.setContent(email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle()));
		email.setContent(email.getContent().replaceAll("\\$\\{CREATORNAME\\}", survey.getCreator().getName()));

		System.out.println(email.getContent());
	}

	public void sendMailToParticipants(Survey survey) {
		for (Participant p : survey.getParticipants()) {
			Email email = new Email();
			email.setAddress(p.getEmail());
			email.setSubject("You were invited to participate in a survey by " + survey.getCreator().getName());
			email.setContent(getEmailContent("participants"));
			email.getContent().replaceAll("\\$\\{TITLE\\}", survey.getTitle());
			email.getContent().replaceAll("\\$\\{CREATORNAME\\}", survey.getCreator().getName());

			System.out.println(email.getContent());
		}
	}

	private String getEmailContent(String template) {
		ClassLoader cl = getClass().getClassLoader();
		InputStream is = cl.getResourceAsStream("static/tmpl/emailTemplate.html");

		if (template.equals("creator")) {
			is = cl.getResourceAsStream("static/tmpl/emailTemplate_Creator.html");
		} else if (template.equals("participants")) {
			is = cl.getResourceAsStream("static/tmpl/emailTemplate_Participants.html");
		} else if (template.equals("reminder")) {
			is = cl.getResourceAsStream("static/tmpl/emailTemplate_Reminder.html");
		}

		return inputStreamToString(is);
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

}