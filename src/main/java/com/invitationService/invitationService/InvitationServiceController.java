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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.invitationService.models.Creator;
import com.invitationService.models.Participant;
import com.invitationService.models.Survey;
import com.invitationService.services.EmailService;

@Controller
@EnableAutoConfiguration
public class InvitationServiceController {
	
	//TODO: REFACTOR THIS!

	@GetMapping("/login")
	public String login(Model model) {

		model.addAttribute("user", new Creator());
		return "login_form";
	}

	@PostMapping("/goToDesigner")
	public String goToDesigner(@Valid @ModelAttribute Creator user, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errormessage",
					"Bitte die Eingabe prüfen, die Emailadresse ist nicht gültig.");
			return "redirect:/login";
		} else {

			// TODO redirect to designer service mit user.email als attribute
			return "designerMock";
		}
	}

	@Autowired
	private EmailService emailService;

	@RequestMapping("/")
	public String home(Model model) {
		List<Participant> liste = new ArrayList<>();

		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", false));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", false));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));
		liste.add(new Participant(1, "e@mail.de", true));

		model.addAttribute("liste", liste);
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/sendAccountMailToCreator", method = RequestMethod.POST)
	public Creator SendMailToCreator(@RequestBody Creator creator) {
		emailService.sendAccountMailToCreator(creator);
		return creator;
	}

	@ResponseBody
	@RequestMapping(value = "/sendInvitationToParticipants", method = RequestMethod.POST)
	public Survey SendMailToParticipants(@RequestBody Survey survey) {
		emailService.sendInviteToParticipants(survey);
		return survey;
	}

	@ResponseBody
	@RequestMapping(value = "/sendInvitationEmailsList", method = RequestMethod.POST)
	public List<Creator> SendInvitationEmailsList(@RequestBody List<Creator> users) {
		users.forEach(s -> System.out.println(s));
		// emailService.sendMail(user);
		return null;
	}

	@RequestMapping("/list/{id}")
	public List<Participant> getRows(@PathVariable String id) {
		return new ArrayList<>();
	}

}
