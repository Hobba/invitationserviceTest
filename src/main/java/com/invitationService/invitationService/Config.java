package com.invitationService.invitationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public CreatorDAO creatorDAO() {
		return new CreatorDAO();
	}
	
//	@Bean
//	public ApiKeyClass api() {
//		return new ApiKeyClass();
//	}

}