package com.invitationService.invitationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class Config {

	@Bean
	public CreatorDAO creatorDAO() {
		return new CreatorDAO();
	}
	
	@Bean
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(new MongoClient(), "bootcamp");
	}

	@Bean
	public MongoOperations mongoTemplate() {
		return new MongoTemplate(mongoDbFactory());
	}

	
//	@Bean
//	public ApiKeyClass api() {
//		return new ApiKeyClass();
//	}

}