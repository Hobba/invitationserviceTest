package com.invitationService.invitationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.invitationService.services.EmailService;

@Configuration
public class Config {
	
	@Bean
	public EmailService emailService() {
		return new EmailService();
	}

}
