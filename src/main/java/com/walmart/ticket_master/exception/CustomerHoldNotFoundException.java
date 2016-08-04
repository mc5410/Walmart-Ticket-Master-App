package com.walmart.ticket_master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer with this hold ID Not Found")
public class CustomerHoldNotFoundException extends Exception {
	
		private static final long serialVersionUID = 1L;

}
