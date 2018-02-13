package com.invitationService.invitationService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invitationService.models.TableRow;

import com.invitationService.models.User;

import com.invitationService.services.EmailService;

@Controller
@EnableAutoConfiguration
public class InvitationServiceController {

	@Autowired
	private EmailService emailService;

	@RequestMapping("/")
	public String home(Model model) {
		List<TableRow> liste = new ArrayList<>();

		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", false));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", false));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));
		liste.add(new TableRow(1, "Peter", "Hummels", "e@mail.de", true));

		model.addAttribute("liste", liste);
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/sendInvitationEmails", method = RequestMethod.POST)
	public String SendInvitationEmails(@RequestBody List<User> users) {
		emailService.sendMail(users);
		return "HELLO";
	}

	@RequestMapping("/list/{id}")
	public List<TableRow> getRows(@PathVariable String id) {
		return new ArrayList<>();
	}

}
