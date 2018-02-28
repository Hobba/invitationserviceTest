package com.invitationService.invitationService;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;
import com.invitationService.services.EmailService;

@Controller
@EnableAutoConfiguration
public class InvitationServiceController {

	@Autowired
	private CreatorDAO dao;

	Logger logger = LoggerFactory.getLogger(InvitationServiceController.class);

	@GetMapping("/")
	public String login(Model model) {
		logger.info("Die Invitationservice root page wurde aufgerufen");
		model.addAttribute("user", new Creator());
		model.addAttribute("showLogin", true);
		return "login_form";
	}

	@PostMapping("/goToConfirmation")
	public String goToDesigner(@Valid @ModelAttribute Creator user, BindingResult bindingResult, Model model) {
		logger.info("Eine Email wurde eingegeben und die 'Bitte Emaillink anklicken' Seite wurde angefragt");
		if (bindingResult.hasErrors()) {
			model.addAttribute("showLogin", true);
			model.addAttribute("user", user);
			model.addAttribute("errormessage", "Bitte die Eingabe prüfen, die Emailadresse ist nicht gültig.");
			logger.info("Eine falsche Email wurde eingegeben: {}", user.getEmail());
			return "login_form";
		} else {
			// send email to creator

			if (dao.isCreatorExist(user.getEmail())) {
				model.addAttribute("userExisted", true);

				emailService.sendAccountMailToCreator(user, true);
			} else {
				dao.insertCreator(user);
				model.addAttribute("userExisted", false);

				emailService.sendAccountMailToCreator(user, false);
			}

			model.addAttribute("showLogin", false);
			model.addAttribute("email", user.getEmail());

			return "login_form";
		}
	}
	
	@RequestMapping("/impressum")
	String impressum(Model model) {
		return "impressum";
	}

	@Autowired
	private EmailService emailService;

	@ResponseBody
	@RequestMapping(value = "/sendInvitationToParticipants", method = RequestMethod.POST)
	public int SendMailToParticipants(@RequestBody Survey survey, @RequestHeader("Authorization")String tokenBase64) {
		logger.info("Token Key: {}", tokenBase64);
		logger.info("SendMailToParticipants wurde aufgerufen für die Survey {}", survey.getId());
		return emailService.sendInviteToParticipants(survey);
	}

	@ResponseBody
	@RequestMapping(value = "/sendReminderToParticipants", method = RequestMethod.POST)
	public int sendReminderToParticipants(@RequestBody Survey survey) {
		logger.info("SendReminderToParticipants wurde aufgerufen für die Survey {}", survey.getId());
		return 100;
		//return emailService.sendReminderToParticipants(survey);
	}

	@ResponseBody
	@RequestMapping(value = "/checkIfTokenIsAlreadyUsed", method = RequestMethod.POST)
	public boolean checkParticipantHasAnswered(@RequestBody Participant p) {
		logger.info("Ein Check, ob ein User {} eine Umfrage {} bereits beantwortet hat wurde aufgerufen", p.getEmail(),
				p.getSurvey_id());
		return dao.hasParticipantAnswered(p);

	}

	@ResponseBody
	@RequestMapping(value = "/setTokenAsUsed", method = RequestMethod.POST)
	public String setParticipantTokenAsUsed(@RequestBody Participant p) {
		logger.info("Ein Teilnehmer {} hat eine Umfrage beantwortet {}", p.getEmail(), p.getSurvey_id() );
		dao.setParticipantAsAnswered(p);
		return "Set as answered: " + p.getEmail();
	}
	
	@ResponseBody
	@RequestMapping(value = "surveys/{id}/participants", method = RequestMethod.GET)
	public List<Participant> getParticipants(@PathVariable("id") String surveyID) {

		List<Participant> result = dao.getAllParticipantsForSurvey(surveyID);

		if (result != null) {
			logger.info("Survey-ID {} | Es wurden die Participants für das Survey angefragt und eine List mit {} Participants zurückgegeben.", surveyID, result.size());
		}

		return result;
	}

}
