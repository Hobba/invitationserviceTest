package com.invitationService.invitationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.invitationService.services.MailgunEmailService;
import com.invitationService.services.EmailService;

@Configuration
@Profile("production")
public class ProductionConfig {

	@Bean
	public EmailService emailService() {
		return new MailgunEmailService();
	}
}
