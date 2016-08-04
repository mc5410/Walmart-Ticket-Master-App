package com.walmart.ticket_master.service;

import java.util.List;
import java.util.Optional;

import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.exception.CustomerHoldNotFoundException;
import com.walmart.ticket_master.exception.LevelNotFoundException;
import com.walmart.ticket_master.exception.MinOrMaxLevelNotValidException;
import com.walmart.ticket_master.exception.SeatsAreFullException;

public interface TicketService {
/**
* The number of seats in the requested level that are neither held nor reserved
*
* @param venueLevel a numeric venue level identifier to limit the search
* @return the number of tickets available on the provided level
 * @throws LevelNotFoundException 
*/
  int numSeatsAvailable(Optional<Integer> venueLevel) throws LevelNotFoundException;
/**
* Find and hold the best available seats for a customer
*
* @param numSeats the number of seats to find and hold
* @param minLevel the minimum venue level
* @param maxLevel the maximum venue level
* @param customerEmail unique identifier for the customer
* @return a SeatHold object identifying the specific seats and related
information
 * @throws LevelNotFoundException 
 * @throws SeatsAreFullException 
 * @throws MinOrMaxLevelNotFoundException 
*/
 List<HoldSeat> findAndHoldSeats(int numSeats, Optional<Integer> minLevel,Optional<Integer> maxLevel, String customerEmail) throws SeatsAreFullException, LevelNotFoundException, MinOrMaxLevelNotValidException;
/** 
* Commit seats held for a specific customer
*
* @param seatHoldId the seat hold identifier
* @param customerEmail the email address of the customer to which the seat hold
is assigned
* @return a reservation confirmation code
 * @throws CustomerHoldNotFoundException 
*/
List<ReserveSeat> reserveSeats(long seatHoldId, String customerEmail) throws CustomerHoldNotFoundException;



int getAllSeatsAvailable() throws LevelNotFoundException;

} 

