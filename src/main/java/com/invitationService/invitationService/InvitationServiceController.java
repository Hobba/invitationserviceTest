package com.invitationService.invitationService;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class InvitationServiceController {

	
	
	@RequestMapping("/")
	public String home() {
		
		return "index";
	}
	
}
