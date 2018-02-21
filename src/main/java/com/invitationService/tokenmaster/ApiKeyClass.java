package com.invitationService.tokenmaster;

public class ApiKeyClass {

	public ApiKeyClass(String key) {
		this.key = key;
	}

	private String key;

	public String getSecret() {

<<<<<<< HEAD
=======
		logger.debug("Dies ist der mega geheime secret key: {}", key);
>>>>>>> master
		return key;
	}
}
