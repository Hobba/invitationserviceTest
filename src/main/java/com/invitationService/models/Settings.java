package com.invitationService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Settings {
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private LocalDate creationDate;
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private LocalDate dueDate;
	
	private String greeting;

}
