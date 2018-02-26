package com.invitationService.invitationService;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;

public class CreatorDAO {

	@Autowired
	private MongoOperations template;
	
	Logger logger = LoggerFactory.getLogger(CreatorDAO.class);

	public void insertCreator(Creator c) {
		template.insert(c, "creator");
	}
	
	public void insertParticipant(Participant p) {
		template.insert(p, "participant");
	}
	
	public boolean hasParticipantAnswered(Participant p, Survey survey) {
		
		logger.info("Teilnehmer( {} ) und die Survey ( {} ) sollen abgefragt werden", p , survey);
		Boolean result = false;
		try {
		result = template.exists(query(where("email").is(p.getEmail()).and("id").is(survey.getId())), Boolean.class);
		}catch (Exception e) {
			logger.warn("Die DB Abfrage nach Teilnehmer {} und Umfrage {} - Match für den Status ist fehlgeschlagen", p, survey);
			
		}
		
		return result;
	}
	
	public boolean setParticipantAsAnswered(Participant p, Survey survey) {
		//TODO IMPLEMENT JANNIK
		return true;
	}

	public boolean isCreatorExist(String email) {
		BasicQuery q = new BasicQuery("{ \"email\" : \"" + email + "\"}");
		Boolean result = template.find(q, Creator.class).size() != 0;
		logger.info("Es wurde der Creator mit email: {} gesucht und das Ergebnis war: {}", email, result);
		return result;
	}

}