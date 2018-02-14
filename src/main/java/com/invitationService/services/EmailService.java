package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import com.invitationService.models.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EmailService {

	@Value("${mailgun.api.url}")
	private String mailgun_url;

	@Value("${mailgun.api.key}")
	private String mailgun_key;

	@Value("${mailgun.api.from}")
	private String mailgun_from;

	public void sendMail(User user) {
		sendMailToAddress(user);
	}

	private JsonNode sendMailToAddress(User user) {

		HttpResponse<JsonNode> request = null;

		try {
			request = Unirest.post(mailgun_url + "/messages").basicAuth("api", mailgun_key)
					.queryString("from", mailgun_from).queryString("to", user.getEmail())
					.queryString("subject", this.parseSubject(user)).queryString("html", this.parseEmail()).asJson();
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

	private String parseSubject(User user) {
		// TODO: User field "Invited by"
		return "You were invited to a survey by user.getInvitedBy()";
	}

}