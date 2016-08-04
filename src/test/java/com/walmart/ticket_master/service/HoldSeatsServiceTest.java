package com.walmart.ticket_master.service;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.walmart.ticket_master.TestConfig;
import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.entity.Venue;
import com.walmart.ticket_master.exception.LevelNotFoundException;
import com.walmart.ticket_master.exception.MinOrMaxLevelNotValidException;
import com.walmart.ticket_master.exception.SeatsAreFullException;
import com.walmart.ticket_master.repository.TicketRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class HoldSeatsServiceTest extends TestConfig {



	@Mock
	private TicketRepository repo ;
	
	@Autowired
	private TicketService service;
	
	@Mock
	private HoldSeat hold;
	
	@Mock
	private Venue venue;
	
	@Mock
	private ReserveSeat reserve;
	
	@Mock
	List<HoldSeat> delhold;
	
	@Mock
	List<ReserveSeat> res;
	
	Date date = new Date();
	
	
	@Before
	public void setup(){
		
		MockitoAnnotations.initMocks(this);
		hold = new HoldSeat();
		hold.setEmailAddress("test@test.com");
		hold.setLevelId(1);
		hold.setSeats(1200);
		hold.setModified(date);
		hold.setDbId(1232112321);
		
		delhold.add(hold);
		
		reserve = new ReserveSeat();
		reserve.setCustemail("test@test.com");
		reserve.setHoldId(1232112321);
		reserve.setId(UUID.randomUUID().toString());
		reserve.setLevelId(1);
		reserve.setNumberOfSeats(1200);
		
		res.add(reserve);
		
		venue =  new Venue();
		venue.setLevelId(1);
		venue.setLevelName("Orchestra");
		venue.setPrice(100);
		venue.setRows(25);
		venue.setSeatsInRow(50);
		
	}
	


	@SuppressWarnings("unused")
	@Test
    public void findAndHoldSeatsTest() throws SeatsAreFullException, LevelNotFoundException, MinOrMaxLevelNotValidException {
    	
        List<HoldSeat> seatHold = service.findAndHoldSeats(1,Optional.of(1),Optional.of(4),"dummy@gmail.com");
        final int numSeatsAvailableAfterSeatHold = service.numSeatsAvailable(Optional.of(hold.getLevelId()));
        assertEquals(1249, numSeatsAvailableAfterSeatHold);
    }

    @Test(expected = SeatsAreFullException.class)
    public void findAndHoldSeatsWithMoreSeatsTest() throws SeatsAreFullException, LevelNotFoundException, MinOrMaxLevelNotValidException{
        int additionalSeats = 100;
        int numSeatsAvailable = service.numSeatsAvailable(Optional.of(venue.getLevelId()));
        int numSeatsAvailableForMain = service.numSeatsAvailable(Optional.of(venue.getLevelId()));
        service.findAndHoldSeats(numSeatsAvailable + additionalSeats, Optional.of(venue.getLevelId()), 
        		Optional.of(venue.getLevelId()), hold.getEmailAddress());
        assertEquals(numSeatsAvailableForMain, service.numSeatsAvailable(Optional.of(venue.getLevelId())));
    
    }



    
}

	
	
