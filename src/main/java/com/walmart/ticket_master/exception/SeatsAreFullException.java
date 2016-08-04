package com.walmart.ticket_master.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "THE NUMBER OF SEATS TO BE BOOKED ARE MORE THAN AVAILABLE SEATS")
public class SeatsAreFullException extends Exception {
	
		private static final long serialVersionUID = 1L;

}
