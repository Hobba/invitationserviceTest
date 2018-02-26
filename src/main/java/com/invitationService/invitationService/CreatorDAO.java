package com.invitationService.invitationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.invitationService.models.Creator;
import com.mongodb.MongoClient;

public class CreatorDAO {

	@Autowired
	private MongoOperations template;

	public void insertCreator(Creator c) {
		template.insert(c, "creator");
	}

	public boolean isCreatorExist(String email) {
		BasicQuery q = new BasicQuery("{ \"email\" : \"" + email + "\"}");
		return template.find(q, Creator.class).size() != 0;
	}

}