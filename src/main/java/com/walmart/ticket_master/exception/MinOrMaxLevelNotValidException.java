package com.walmart.ticket_master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Given Min Level or Max level Not Valid")
public class MinOrMaxLevelNotValidException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3168020789625284353L;
	
	

}
