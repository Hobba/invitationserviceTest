package com.invitationService.invitationService;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class CreatorDAOStub extends CreatorDAO{


	Logger logger = LoggerFactory.getLogger(CreatorDAOStub.class);


	private List<Creator> creators = new ArrayList<>();
	private List<Participant> participants = new ArrayList<>();

	public CreatorDAOStub(){
	    logger.info("### initializing CreatorDAOStub ###");
    }

	public void insertCreator(Creator c) {
		creators.add(c);
	}

	public void insertParticipant(Participant p) {
	participants.add(p);
	}

	public boolean hasParticipantAnswered(Participant p) {
		return false;
	}

	public void setParticipantAsAnswered(Participant p) {
	}

	public boolean isCreatorExist(String email) {
		return creators.stream().anyMatch(c -> email.equalsIgnoreCase(c.getEmail()) );
	}
	
	public List<Participant> getAllParticipantsForSurvey (String survey_id) {
		return participants.stream().filter(p->survey_id.equalsIgnoreCase(p.getSurvey_id())).collect(Collectors.toList());
	}

}