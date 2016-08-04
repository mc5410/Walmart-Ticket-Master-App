package com.walmart.ticket_master.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.ticket_master.controller.entity.HoldSeatBody;
import com.walmart.ticket_master.controller.entity.ReserveSeatBody;
import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.exception.CustomerHoldNotFoundException;
import com.walmart.ticket_master.exception.LevelNotFoundException;
import com.walmart.ticket_master.exception.MinOrMaxLevelNotValidException;
import com.walmart.ticket_master.exception.SeatsAreFullException;
import com.walmart.ticket_master.service.TicketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value="/api/v1")
@Api(tags="v1")
public class TicketController {
	
	@Autowired
	private TicketService Service;
	
	@RequestMapping(value = "/seats/{level}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get seats in a level", notes = "Gets the total number of seats in each specified level")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public int numSeatsAvailable(@PathVariable("level") Optional<Integer> level) throws LevelNotFoundException   {
		return Service.numSeatsAvailable(level);
	}
	
	
	@RequestMapping(value = "/seats",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get seats in all levels", notes = "Gets the total number of seats in all the levels")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public int getAllSeatsAvailable() throws LevelNotFoundException   {
		return Service.getAllSeatsAvailable();
	}
	
	
	@RequestMapping(value="/hold",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "hold number of seats", notes = "This request is to hold number of seats for a customer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<HoldSeat> holdSpecifiedSeats(@RequestBody HoldSeatBody holdseat) throws LevelNotFoundException, SeatsAreFullException, MinOrMaxLevelNotValidException   {
		
		int numofseats = holdseat.getNumSeats();
		String email = holdseat.getCustEmail();
		
		if(holdseat.getMinLevel() != 0 && holdseat.getMaxLevel() != 0){
			
		Optional<Integer> minlevel = Optional.of(holdseat.getMinLevel());
		Optional<Integer> maxlevel = Optional.of(holdseat.getMaxLevel());
		
		return Service.findAndHoldSeats(numofseats,minlevel,maxlevel,email);
		}
		else{
			
			Optional<Integer> minlevel = Optional.of(0);
			Optional<Integer> maxlevel = Optional.of(0);
			return Service.findAndHoldSeats(numofseats,minlevel,maxlevel,email);
		}
				
	}
	
	
	@RequestMapping(value = "/reserve",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "reserve the held seat", notes = "reserve the already held seats using customer email id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<ReserveSeat> reserveHeldSeats(@RequestBody ReserveSeatBody reserveSeat) throws CustomerHoldNotFoundException {
			
		return Service.reserveSeats(reserveSeat.getSeatHoldId(),reserveSeat.getCustomerEmail());
	}
	
	

}
