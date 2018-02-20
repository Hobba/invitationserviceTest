package com.invitationService.tokenmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ApiKeyClass {

	@Value("token.key")
	private String key;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public String getSecret() {

		logger.debug("Dies ist der mega geheime secret key", key);
		return key;
	}
}
