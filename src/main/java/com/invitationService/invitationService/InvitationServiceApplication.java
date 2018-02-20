package com.invitationService.invitationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.invitationService.tokenmaster.TokenService;

@SpringBootApplication
public class InvitationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvitationServiceApplication.class, args);
	}
	
	@Bean
	public TokenService tokenService() {
		return new TokenService();
	}
}