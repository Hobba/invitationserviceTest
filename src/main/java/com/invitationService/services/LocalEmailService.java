package com.invitationService.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.invitationService.models.Creator;
import com.invitationService.models.Survey;

public class LocalEmailService implements EmailService {

	public void sendMail(Creator creator) {
		System.out.println(parseEmail());
	}

	public void sendMail(Survey survey) {
		System.out.println(parseEmail());
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

	// private String parseSubject(User user) {
	// // TODO: User field "Invited by"
	// return "You were invited to a survey by user.getInvitedBy()";
	// }

}