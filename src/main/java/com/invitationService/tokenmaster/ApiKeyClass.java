package com.invitationService.tokenmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ApiKeyClass {
	
	public ApiKeyClass(String key) {
		this.key = key;
	}
	
	//NOTIZ WIE MAN DIE ANNOTATION FIXED: Die Klasse ApiKeyClass muss als Bean angelegt werden und im TokenService Autowired werden, nur so kann die Value Annotation funktionieren. :)
	private String key;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public String getSecret() {

		logger.debug("Dies ist der mega geheime secret key: {}", key);
		return key;
	}
}
