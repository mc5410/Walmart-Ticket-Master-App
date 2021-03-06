package com.walmart.ticket_master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "level Not Found")
public class LevelNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5968111547444142953L;

}