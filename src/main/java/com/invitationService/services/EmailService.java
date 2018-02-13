package com.invitationService.services;

import java.util.List;

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
					.queryString("subject", this.parseSubject()).queryString("text", this.parseEmail()).asJson();
			
			System.out.println("Email successfully send to master.");
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return request.getBody();
	}

	private String parseEmail() {
		return "DEFAULT TEXT";
	}

	private String parseSubject() {
		return "DEFAULT SUBJECT";
	}

}
