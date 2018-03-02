package com.invitationService.invitationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import static org.mockito.Mockito.never;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

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
	public void testeInsertParticipant() {
		Participant parti = new Participant();
		creatorDAO.insertParticipant(parti);
		
		
		verify(template, times(1)).insert(parti, "participant");
	}

	@Test
	public void testeHasParticipantAnsweredFalse() {

		when(participant.getHasAnswered()).thenReturn(false);
		when(participant.getSurvey_id()).thenReturn("1");
		when(template.findOne(query(where("email").is(participant.getEmail()).and("survey_id").is(participant.getSurvey_id())),
				Participant.class)).thenReturn(participant);

		Assert.assertEquals(false, creatorDAO.hasParticipantAnswered(participant));

	}

	@Test
	public void testeHasParticipantAnsweredTrue() {

		when(participant.getHasAnswered()).thenReturn(true);
		when(participant.getSurvey_id()).thenReturn("1");
		
		 when(template.findOne(query(where("email").is(participant.getEmail()).and("survey_id").is(participant.getSurvey_id())),
		 Participant.class)).thenReturn(participant);

		Assert.assertEquals(true, creatorDAO.hasParticipantAnswered(participant));

	}
	
	
	@Test
	public void testeSetParticipantHasAnswered() {
		Participant parti = new Participant("test@example.com", "token", "1", false);
		List<Participant> liste = new ArrayList<>();
		liste.add(parti);
//		when(participant.getEmail()).thenReturn("test@example.com");
//		when(participant.getSurvey_id()).thenReturn("1");
		when(template.find(query(where("email").is("test@example.com").and("survey_id").is("1")),
					Participant.class)).thenReturn(liste);
		
		creatorDAO.setParticipantAsAnswered(parti);
		
		Assert.assertEquals(true, parti.getHasAnswered());
	}
	
	@Test
	public void testeSetParticipantHasAnsweredNoParticipantInDB() {
		Participant parti = new Participant("test@example.com", "token", "1", false);
		List<Participant> liste = new ArrayList<>();
		
//		when(participant.getEmail()).thenReturn("test@example.com");
//		when(participant.getSurvey_id()).thenReturn("1");
		when(template.find(query(where("email").is("test@example.com").and("survey_id").is("1")),
					Participant.class)).thenReturn(liste);
		
		creatorDAO.setParticipantAsAnswered(parti);
		
		Assert.assertEquals(false, parti.getHasAnswered());
	}
	
	
	@Test
	public void testeIsCreatorExistTrue() {
		
		BasicQuery q = new BasicQuery("{ \"email\" : \"" + "test@example.com" + "\"}");
		List<Creator> liste = new ArrayList<>();
		Creator creator = new Creator();
		creator.setEmail("test@example.com");
		liste.add(creator);
		System.out.println(liste.size());
		
		when(template.find(q, Creator.class)).thenReturn(liste);
		
		Assert.assertEquals(true, creatorDAO.isCreatorExist("test@example.com"));
		
	}
	
	@Test
	public void testeIsCreatorExistFalse() {
		
		BasicQuery q = new BasicQuery("{ \"email\" : \"" + "falsetest@example.com" + "\"}");
		List<Creator> liste = new ArrayList<>();
		Creator creator = new Creator();
		creator.setEmail("test@example.com");
		liste.add(creator);
		System.out.println(liste.size());
		
		when(template.find(q, Creator.class)).thenReturn(liste);
		
		Assert.assertEquals(false, creatorDAO.isCreatorExist("test@example.com"));
		
	}
	
	@Test
	public void testeGetAllParticipantsGetNone() {
		when(participant.getSurvey_id()).thenReturn("1");
		List<Participant> liste = new ArrayList<>(); 
		
		
		when(template.find(query(where("survey_id").is("1")), Participant.class)).thenReturn(liste);
		
		
		Assert.assertEquals(liste, creatorDAO.getAllParticipantsForSurvey("1"));
	}
	
	@Test
	public void testeGetAllParticipantsGetSome() {
		when(participant.getSurvey_id()).thenReturn("1");
		List<Participant> liste = new ArrayList<>();
		liste.add(participant);
		liste.add(participant);
		liste.add(participant);
		
		when(template.find(query(where("survey_id").is("1")), Participant.class)).thenReturn(liste);
		
		
		Assert.assertEquals(liste, creatorDAO.getAllParticipantsForSurvey("1"));
	}

}
