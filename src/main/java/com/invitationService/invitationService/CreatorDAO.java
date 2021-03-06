package com.invitationService.invitationService;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import com.invitationService.models.Creator;
import com.invitationService.models.Participant;

public class CreatorDAO {

	@Autowired
	private MongoOperations template;

	Logger logger = LoggerFactory.getLogger(CreatorDAO.class);

	public void insertCreator(Creator c) {

		template.insert(c, "creator");
		logger.info("Creator {} wurde in der DB angelegt", c.getEmail());
	}

	public void insertParticipant(Participant p) {
		template.insert(p, "participant");
		logger.info("Teilnehmer {} wurde in der DB angelegt", p.getEmail());
	}

	public boolean hasParticipantAnswered(Participant p) {

		if (p.getSurvey_id() != null) {
			logger.info("Teilnehmer( {} ) und die Survey ( {} ) sollen abgefragt werden", p.getEmail(),
					p.getSurvey_id());
		} else {
			logger.info("Der Teilnehmer {} und eine Survey sollen abgefragt werden, aber die Survey ist null",
					p.getEmail());
		}

		Boolean result = null;

		try {
			// TODO WAS ist wenn die DB keinen Teilnehmer enthält??
			Participant participant = template.findOne(
					query(where("email").is(p.getEmail()).and("survey_id").is(p.getSurvey_id())), Participant.class);
			if (participant != null) {

				result = participant.getHasAnswered();
				logger.info("Participant wurde in der DB gefunden und Antwort-Status ist {}", result);
			}

		} catch (Exception e) {
			logger.warn("Die DB Abfrage nach Teilnehmer {} und Umfrage {} - Match für den Status ist fehlgeschlagen", p,
					p.getSurvey_id());

		}

		return result;
	}

	public void setParticipantAsAnswered(Participant p) {
		List<Participant> participant = new ArrayList<>();
		try {
			participant = template.find(query(where("email").is(p.getEmail()).and("survey_id").is(p.getSurvey_id())),
					Participant.class);
			if (!participant.isEmpty()) {
				Participant rewriteParticipant = participant.get(0);
				rewriteParticipant.setHasAnswered(true);
				template.updateFirst(
						query(where("email").is(rewriteParticipant.getEmail()).and("survey_id")
								.is(rewriteParticipant.getSurvey_id())),
						Update.update("hasAnswered", "true"), Participant.class);
				logger.info("Teilnehmer {} erfolgreich geupdatet", rewriteParticipant);
			} else {
				logger.info("Teilnehmerupdate für {} gescheitert!", p.getEmail());

			}
		} catch (Exception e) {
			logger.warn("Error beim setzen des Status des Teilnehmers: {}", p.getEmail());

		}

	}

	public boolean isCreatorExist(String email) {
		BasicQuery q = new BasicQuery("{ \"email\" : \"" + email + "\"}");
		Boolean result = template.find(q, Creator.class).size() != 0;
		logger.info("Es wurde der Creator mit email: {} gesucht und das Ergebnis war: {}", email, result);
		return result;
	}

	public List<Participant> getAllParticipantsForSurvey(String survey_id) {
		return template.find(query(where("survey_id").is(survey_id)), Participant.class);

	}

}