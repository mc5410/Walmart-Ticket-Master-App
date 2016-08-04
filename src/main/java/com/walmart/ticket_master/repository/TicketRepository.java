package com.walmart.ticket_master.repository;

import java.sql.Timestamp;
import java.util.List;

import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.entity.Venue;

public interface TicketRepository {
	
	
	Venue findVenueByLevel(int level);
	
	List<Venue> findAllVenues();
	
	List<ReserveSeat> findSeatReservesByLevel(int level);

	List<HoldSeat> findExpiredSeatHolds(Timestamp expiredInstant);

	void deleteSeatHolds(List<HoldSeat> seatHoldIds);

	List<HoldSeat> saveSeatHold(List<HoldSeat> seatHold);

	List<HoldSeat> findSeatHoldsByLevel(int level);

	List<HoldSeat> findSeatHoldsByEmail(String customerEmail);

	List<ReserveSeat> saveSeatReserve(List<ReserveSeat> seatReserve, List<HoldSeat> seatHold);
	

}
