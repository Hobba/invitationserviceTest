package com.invitationService.invitationService;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;

public class CreatorDAO {

	@Autowired
	private MongoOperations template;

	public void insertCreator(Creator c) {
		template.insert(c, "creator");
	}
	
	public void insertParticipant(Participant p) {
		template.insert(p, "participant");
	}
	
	public boolean hasParticipantAnswered(Participant p, Survey survey) {
		
		Boolean result = false;
		result = template.exists(query(where("email").is(p.getEmail()).and("id").is(survey.getId())), Boolean.class);
		
		return result;
	}

	public boolean isCreatorExist(String email) {
		BasicQuery q = new BasicQuery("{ \"email\" : \"" + email + "\"}");
		return template.find(q, Creator.class).size() != 0;
	}

}