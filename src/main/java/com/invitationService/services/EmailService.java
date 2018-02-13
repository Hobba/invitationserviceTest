package com.invitationService.services;

import org.springframework.beans.factory.annotation.Value;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EmailService {

	// TODO: connector, sender, parser der emailliste, platzhalter in der html
	// auffüllen

	@Value("${mailgun.api.url}")
	private String mailgun_url;

	@Value("${mailgun.api.key}")
	private String mailgun_key;

	@Value("${mailgun.api.from}")
	private String mailgun_from;

	public JsonNode sendMail(String emailaddress) throws UnirestException {

		HttpResponse<JsonNode> request = Unirest.post(mailgun_url + "/messages").basicAuth("api", mailgun_key)
				.queryString("from", mailgun_from).queryString("to", emailaddress)
				.queryString("subject", this.parseSubject()).queryString("text", this.parseEmail()).asJson();

		return request.getBody();
	}

	public String parseEmail() {
		return "";
	}

	public String parseSubject() {
		return "";
	}

}
