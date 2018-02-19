package com.invitationService.invitationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.invitationService.services.MailgunEmailService;
import com.mongodb.MongoClient;
import com.invitationService.services.EmailService;

@Configuration
@Profile("production")
public class ProductionConfig {

	@Bean
	public EmailService emailService() {
		return new MailgunEmailService();
	}
}
