package com.invitationService.invitationService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.invitationService.models.User;

@ControllerAdvice
public class LoginEmailExceptionHandler {

//	  @ExceptionHandler(MethodArgumentNotValidException.class)
//	    public ResponseEntity<ExceptionResponse> invalidInput(MethodArgumentNotValidException ex) {
//	        ExceptionResponse response = new ExceptionResponse();
//	        response.setErrorCode("Validation Error");
//	        response.setErrorMessage(ex.getMessage());
//	        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
//	    }


}
