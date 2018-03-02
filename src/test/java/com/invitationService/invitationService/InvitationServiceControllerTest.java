package com.invitationService.invitationService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.stubbing.answers.Returns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.MultiValueMap;
import org.thymeleaf.standard.expression.AndExpression;

import com.invitationService.models.Creator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import javax.swing.text.View;

@RunWith(SpringRunner.class)
@WebMvcTest(InvitationServiceController.class)
public class InvitationServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreatorDAO creatorDAO;

	@Test
	public void indexShouldReturnLoginForm() throws Exception {

		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("login_form"));

	}

	@Test
	public void indexCallShouldInvokeMethodLogin() throws Exception {

		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeExists("user")).andExpect(model().attribute("showLogin", true));

	}

	@Test
	public void testeGoToDesignerWithAttributes() throws Exception {

		Creator creator = new Creator();
		creator.setEmail("test@example.com");

		this.mockMvc
				.perform(post("/goToConfirmation").requestAttr("creator", creator).param("email", "test@example.com"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("email"))
				.andExpect(model().attribute("showLogin", false)).andExpect(view().name("login_form"));
	}
	
	@Test
	public void testeGoToDesignerWithAttributesAndBindingResultError() throws Exception {

		Creator creator = new Creator();
		creator.setEmail("test@example.com");

		this.mockMvc
				.perform(post("/goToConfirmation").requestAttr("creator", creator).param("email", "testexample.com"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("showLogin", true))
				.andExpect(model().attributeExists("errormessage"))
				.andExpect(model().attributeExists("user"))
				.andExpect(view().name("login_form"));
	}

}
