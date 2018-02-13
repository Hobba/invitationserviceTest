package com.invitationService.invitationService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invitationService.models.TableRow;

@Controller
@EnableAutoConfiguration
public class InvitationServiceController {

	
	
	@RequestMapping("/")
	public String home(Model model) {
		List<TableRow> liste = new ArrayList<>();
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", false));
		liste.add(new TableRow("Christian", "e@mail.de", true));
		liste.add(new TableRow("Hans", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Anna", "e@mail.de", true));
		liste.add(new TableRow("Lukas", "e@mail.de", true));
		liste.add(new TableRow("Simone", "e@mail.de", true));
		liste.add(new TableRow("Angela", "e@mail.de", true));
		liste.add(new TableRow("Bernd", "e@mail.de", false));
		liste.add(new TableRow("Chantal", "e@mail.de", true));
		liste.add(new TableRow("Dummy", "e@mail.de", true));
		liste.add(new TableRow("Simple", "e@mail.de", true));
		liste.add(new TableRow("Questionaire", "e@mail.de", true));
		liste.add(new TableRow("CGI", "e@mail.de", true));
		liste.add(new TableRow("Consulting", "e@mail.de", true));
		model.addAttribute("liste", liste);
		return "index";
	}
	

	@ResponseBody
	@RequestMapping("/list/{id}")
	public List<TableRow> getRows(@PathVariable String id){
		
		return new ArrayList<>();
	}
	

	
	
	
}
