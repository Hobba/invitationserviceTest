package com.invitationService.invitationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.invitationService.services.EmailService;
import com.invitationService.services.LocalEmailService;

@Configuration
@Profile("local")
public class LocalConfig {

	@Bean
	public EmailService emailService() {

		return new LocalEmailService();
	}

}
