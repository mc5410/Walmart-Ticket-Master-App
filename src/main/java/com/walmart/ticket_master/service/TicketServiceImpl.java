package com.walmart.ticket_master.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.entity.Venue;
import com.walmart.ticket_master.exception.CustomerHoldNotFoundException;
import com.walmart.ticket_master.exception.LevelNotFoundException;
import com.walmart.ticket_master.exception.MinOrMaxLevelNotValidException;
import com.walmart.ticket_master.exception.SeatsAreFullException;
import com.walmart.ticket_master.repository.TicketRepository;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepository repo;
	
	
	private final int expireinseconds = 60;
	
	
	
    public int numSeatsAvailable(Optional<Integer> venueLevel) throws LevelNotFoundException {
		
    	removeExpiredHoldSeats();
    	int level = 0;
        final int availableSeats;
        level = venueLevel.get();
        if(level != 0){
            Venue venue = repo.findVenueByLevel(level);
            
            if(venue!=null){
            	System.out.println(venue.toString());
                return getSeatsByVenue(venue);
            } else {
                throw new LevelNotFoundException();
            }
        } else {
            availableSeats = getAllSeatsAvailable();
        }

        return availableSeats;
    }
    
    private int numSeatsAvailableInt(int venueLevel) throws LevelNotFoundException {
		
         removeExpiredHoldSeats();
     	int level = 0;
         final int availableSeats;
         level = venueLevel;
         if(level != 0){
             Venue venue = repo.findVenueByLevel(level);
             
             if(venue!=null){
     
                 return getSeatsByVenue(venue);
             } else {
                 throw new LevelNotFoundException();
             }
         } else {
             availableSeats = getAllSeatsAvailable();
         }

         return availableSeats;
     }

    public int getAllSeatsAvailable() throws LevelNotFoundException{
    	
    	removeExpiredHoldSeats();
    	
        final List<Venue> venues = repo.findAllVenues();
        
        if(venues != null){
        return venues.stream().mapToInt(this::getSeatsByVenue).sum();
        }
        
        else
        {
        	throw new LevelNotFoundException();
        }
    }
    
    private int getSeatsByVenue(final Venue venue) {
    	
    	removeExpiredHoldSeats();
    	
        final List<HoldSeat> seatHolds = repo.findSeatHoldsByLevel(venue.getLevelId());
        final List<ReserveSeat> seatReserve = repo.findSeatReservesByLevel(venue.getLevelId());
        
        if(seatHolds != null && seatReserve != null){
        final int seatsTaken = seatHolds.stream().mapToInt(HoldSeat::getSeats).sum();
        final int seatsBooked = seatReserve.stream().mapToInt(ReserveSeat::getNumberOfSeats).sum();
        return venue.getSeatsInRow() * venue.getRows() - seatsTaken - seatsBooked;
        }
        
        else if(seatHolds != null && seatReserve == null){
        	final int seatsTaken = seatHolds.stream().mapToInt(HoldSeat::getSeats).sum();
            return venue.getSeatsInRow() * venue.getRows() - seatsTaken;
        }
        
        else if(seatHolds == null && seatReserve != null){
        	
        	final int seatsTaken = seatReserve.stream().mapToInt(ReserveSeat::getNumberOfSeats).sum();
            return venue.getSeatsInRow() * venue.getRows() - seatsTaken;
        }
        
        else{
        	
        	return venue.getSeatsInRow() * venue.getRows();
        }
    }
    
    private void removeExpiredHoldSeats() {
 
        final List<HoldSeat> seatHolds = repo.findExpiredSeatHolds(getExpirationInstant());
            repo.deleteSeatHolds(seatHolds);
   
    }

    private Timestamp getExpirationInstant() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minusExpiredSeconds = now.minusSeconds(expireinseconds);
        Timestamp timestamp = Timestamp.valueOf(minusExpiredSeconds);
        return timestamp;
    }

	@Override
	public List<HoldSeat> findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer>  maxLevel, String customerEmail) throws MinOrMaxLevelNotValidException,LevelNotFoundException,SeatsAreFullException {
		
		removeExpiredHoldSeats();
        Integer minimumLevel = minLevel.get();
        Integer maximumLevel = maxLevel.get();
		 
		if(minimumLevel >=1  && minimumLevel <=4 && maximumLevel <= 4 && maximumLevel >=1){
	       
	        final List<HoldSeat> holds = new ArrayList<>();

	        for(Integer venueLevel = minimumLevel; venueLevel <= maximumLevel; venueLevel++){
	        	
	        	HoldSeat seatHold = new HoldSeat();
	 	        seatHold.setModified(Timestamp.valueOf(LocalDateTime.now()));
	 	        seatHold.setEmailAddress(customerEmail);
	            int availableSeats = numSeatsAvailableInt(venueLevel);
	          
	            if(availableSeats > 0){
	                seatHold.setLevelId(venueLevel);
	                if(availableSeats >= numSeats){
	                    seatHold.setSeats(numSeats);
	                    holds.add(seatHold);
	                    numSeats = 0;
	                    break;
	                } else {
	                    seatHold.setSeats(availableSeats);
	                    holds.add(seatHold);
	                    numSeats = numSeats - availableSeats;
	                    if(venueLevel == maximumLevel && numSeats != 0){
	                    	
	                    	throw new SeatsAreFullException();
	                    }
	                }
	   
	                
	            }
	        }

	        return repo.saveSeatHold(holds);
		}
		
		else if(minimumLevel ==0 && maximumLevel ==0){
			
			removeExpiredHoldSeats();
			List<HoldSeat> holds = new ArrayList<>();

	        for(Integer venueLevel = 1; venueLevel <= 4; venueLevel++){
	        	HoldSeat seatHold = new HoldSeat();
		        seatHold.setModified(Timestamp.valueOf(LocalDateTime.now()));
		        seatHold.setEmailAddress(customerEmail);    
	            int availableSeats = numSeatsAvailableInt(venueLevel);
	            if(availableSeats > 0){
	                seatHold.setLevelId(venueLevel);
	                if(availableSeats >= numSeats){
	                    seatHold.setSeats(numSeats);
	                   
	                    holds.add(seatHold);
	                   
	                    numSeats = 0;
	                    break;
	                } else {
	                    seatHold.setSeats(availableSeats);
	                   
	                    holds.add(seatHold);
	                    
	                    numSeats = numSeats - availableSeats;
	                    	if(venueLevel == 4 && numSeats != 0){
	                    	
	                    	throw new SeatsAreFullException();
	                    }
	                }
	   
	               
	            }
	        }
	        

	        return repo.saveSeatHold(holds);
			
			
		}
		
		else{
			
			throw new MinOrMaxLevelNotValidException();
			
		}
	      
	}

	@Override
	public List<ReserveSeat> reserveSeats(long seatHoldId, String customerEmail) throws CustomerHoldNotFoundException {
		
		removeExpiredHoldSeats();
		
		List<HoldSeat> heldSeats = repo.findSeatHoldsByEmail(customerEmail);
		List<ReserveSeat> reserveSeat = new ArrayList<>();
		
		if(!heldSeats.isEmpty()){
			
			for(HoldSeat hold: heldSeats){
				
			ReserveSeat res = new ReserveSeat();
			res.setHoldId(seatHoldId);
			res.setCustemail(customerEmail);
			res.setLevelId(hold.getLevelId());
			res.setNumberOfSeats(hold.getSeats());
			
			reserveSeat.add(res);
			
				
			}
			
		}
		
		else{
			
			throw new CustomerHoldNotFoundException();
		}
		
		return repo.saveSeatReserve(reserveSeat,heldSeats);
	}

}
