package com.invitationService.invitationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;

public class TestCreatorDAO {

	@InjectMocks
	private CreatorDAO creatorDAO = new CreatorDAO();;

	@Mock
	private MongoOperations template;

	@Mock
	private Participant participant;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testeMockCreatorDAO() {

		Assert.assertTrue(creatorDAO instanceof CreatorDAO);
	}

	@Test
	public void testeMockTemplate() {

		Assert.assertTrue(template instanceof MongoOperations);
	}

	@Test
	public void testeInsertCreator() {

		Creator c = new Creator();
		creatorDAO.insertCreator(c);

		verify(template, times(1)).insert(c, "creator");
	}

	@Test
	public void testeHasParticipantAnsweredFalse() {

		
		
		when(participant.getHasAnswered()).thenReturn(false);
		when(participant.getSurvey_id()).thenReturn("1");
		when(participant.getEmail()).thenReturn("test@example.com");
		when(template.findOne(query(where("email").is(participant.getEmail()).and("id").is(participant.getSurvey_id())), 
				Participant.class)).thenReturn(participant);

		Assert.assertEquals(false, creatorDAO.hasParticipantAnswered(participant));

	}

	@Test
	public void testeHasParticipantAnsweredTrue() {
		
		
		when(participant.getHasAnswered()).thenReturn(true);
		when(participant.getSurvey_id()).thenReturn("1");
		when(participant.getEmail()).thenReturn("test@example.com");
		when(template.findOne(query(where("email").is(participant.getEmail()).and("id").is(participant.getSurvey_id())), 
				Participant.class)).thenReturn(participant);
		
		
		Assert.assertEquals(true, creatorDAO.hasParticipantAnswered(participant));
		
	}

}
