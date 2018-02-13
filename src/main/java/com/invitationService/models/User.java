package com.invitationService.models;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRegistry;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class User {
	
	protected int Id;
	protected String Firstname;
	protected String Lastname;
	@org.hibernate.validator.constraints.Email
	@NotEmpty
	@Pattern(regexp=".*@?.*(\\.)?.*")
	protected String Email;
}
