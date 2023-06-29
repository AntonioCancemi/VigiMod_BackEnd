package com.vigimod.api.exception;

import java.time.DateTimeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class MyExcpetionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<String> manageEntityExistsException(EntityExistsException e) {
		return new ResponseEntity<String>(e.getMessage() + " EntityExistsException!!!", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> manageEntityNotFoundException(EntityNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage() + " EntityNotFoundException!!!", HttpStatus.FOUND);
	}

	@ExceptionHandler(EnumConstantNotPresentException.class)
	public ResponseEntity<String> manageEnumConstantNotPresentException(EnumConstantNotPresentException e) {
		return new ResponseEntity<String>(e.getMessage() + " EnumConstantNotPresentException!!!",
				HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<String> manageEnumConstantNotPresentException(NumberFormatException e) {
		return new ResponseEntity<String>(e.getMessage() + " NumberFormatException!!!", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateTimeException.class)
	public ResponseEntity<String> manageEnumConstantNotPresentException(DateTimeException e) {
		return new ResponseEntity<String>(e.getMessage() + " ParseDateTimeException!!!", HttpStatus.BAD_REQUEST);
	}
}
