package com.invitationService.invitationService;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;
import com.invitationService.services.EmailService;

@Controller
@EnableAutoConfiguration
public class InvitationServiceController {

	@Autowired
	private CreatorDAO creatorDAO;

	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("user", new Creator());
		model.addAttribute("showLogin", true);
		return "login_form";
	}

	@PostMapping("/goToConfirmation")
	public String goToDesigner(@Valid @ModelAttribute Creator user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("showLogin", true);
			model.addAttribute("user", user);
			model.addAttribute("errormessage", "Bitte die Eingabe prüfen, die Emailadresse ist nicht gültig.");
			return "login_form";
		} else {
			// send email to creator

			if (creatorDAO.isCreatorExist(user.getEmail())) {
				model.addAttribute("userExisted", true);

				emailService.sendAccountMailToCreator(user, true);
			} else {
				creatorDAO.insertCreator(user);
				model.addAttribute("userExisted", false);

				emailService.sendAccountMailToCreator(user, false);
			}

			model.addAttribute("showLogin", false);
			model.addAttribute("email", user.getEmail());

			return "login_form";
		}
	}

	@Autowired
	private EmailService emailService;

	// @RequestMapping("/emails")
	// public String home(Model model) {
	// List<Participant> liste = new ArrayList<>();
	//
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", false));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", false));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	// liste.add(new Participant(1, "e@mail.de", true));
	//
	// model.addAttribute("liste", liste);
	// return "index";
	// }

	@ResponseBody
	@RequestMapping(value = "/sendInvitationToParticipants", method = RequestMethod.POST)
	public int SendMailToParticipants(@RequestBody Survey survey) {
		return emailService.sendInviteToParticipants(survey);
	}

	@ResponseBody
	@RequestMapping(value = "/sendReminderToParticipants", method = RequestMethod.POST)
	public int sendReminderToParticipants(@RequestBody Survey survey) {
		return emailService.sendReminderToParticipants(survey);
	}

	@ResponseBody
	@RequestMapping(value = "/sendInvitationEmailsList", method = RequestMethod.POST)
	public List<Creator> SendInvitationEmailsList(@RequestBody List<Creator> users) {
		users.forEach(s -> System.out.println(s));
		// emailService.sendMail(user);
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/checkIfTokenIsAlreadyUsed", method = RequestMethod.GET)
	public boolean checkParticipantHasAnswered(Participant p, Survey survey) {

		CreatorDAO dao = new CreatorDAO();
		if (dao.hasParticipantAnswered(p, survey)) {
			return true;
		} else {
			return false;
		}

	}

	@RequestMapping("/list/{id}")
	public List<Participant> getRows(@PathVariable String id) {
		return new ArrayList<>();
	}

}
