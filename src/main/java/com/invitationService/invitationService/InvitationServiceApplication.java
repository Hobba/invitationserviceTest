package com.invitationService.invitationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.invitationService.tokenmaster.TokenService;

@ComponentScan("com.invitationService")
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