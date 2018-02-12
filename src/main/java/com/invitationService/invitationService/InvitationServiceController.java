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
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Hans", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		liste.add(new TableRow("Peter", "e@mail.de", true));
		model.addAttribute("liste", liste);
		return "index";
	}
	

	@ResponseBody
	@RequestMapping("/list/{id}")
	public List<TableRow> getRows(@PathVariable String id){
		
		return new ArrayList<>();
	}
	

	
	
	
}
