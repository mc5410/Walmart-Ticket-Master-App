package com.walmart.ticket_master.service;

import static org.junit.Assert.*;

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
import com.walmart.ticket_master.exception.CustomerHoldNotFoundException;
import com.walmart.ticket_master.exception.LevelNotFoundException;
import com.walmart.ticket_master.exception.MinOrMaxLevelNotValidException;
import com.walmart.ticket_master.exception.SeatsAreFullException;
import com.walmart.ticket_master.repository.TicketRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class ReserveSeatsServiceTest extends TestConfig{
	
	
	@Mock
	private TicketRepository repo;
	
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
	
	final int expseconds = 4;
	
	Date date = new Date();
	
	
	@Before
	public void setup(){
		
		MockitoAnnotations.initMocks(this);
		hold = new HoldSeat();
		hold.setEmailAddress("test@test.com");
		hold.setLevelId(1);
		hold.setSeats(1200);
		hold.setModified(date);
		hold.setDbId(1);
		
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
  public void reserveSeatsTest() throws Exception {
      final List<HoldSeat> seatHold = service.findAndHoldSeats(hold.getSeats()
              , Optional.of(hold.getLevelId()), Optional.of(hold.getLevelId()), hold.getEmailAddress());
      List<ReserveSeat> result = service.reserveSeats(hold.getDbId(), hold.getEmailAddress());
      assertNotNull(reserve.getId());
      expTest();
      int numSeatsAvailableAfterReservation = service.numSeatsAvailable(Optional.of(venue.getLevelId()));
      assertEquals( venue.getRows()*venue.getSeatsInRow() - reserve.getNumberOfSeats()-1, numSeatsAvailableAfterReservation);
  }

  @Test(expected = CustomerHoldNotFoundException.class)
  public void reserveSeatsWithNotValidSeatHoldTest() throws CustomerHoldNotFoundException {
	  
      service.reserveSeats(0,"notvalid@email.com");
  }

  @SuppressWarnings("unused")
@Test(expected = CustomerHoldNotFoundException.class)
  public void reserveWithNonExistingEmailAddress() throws SeatsAreFullException, LevelNotFoundException, MinOrMaxLevelNotValidException, CustomerHoldNotFoundException {
      final List<HoldSeat> seatHold = service.findAndHoldSeats(10
              , Optional.of(hold.getLevelId()), Optional.of(hold.getLevelId()), hold.getEmailAddress());
      service.reserveSeats(hold.getDbId(), "dummy@email.com");
  }


  private void expTest() throws InterruptedException {

      Thread.sleep((expseconds + 1) * 1000);
  }
  
	
	

}
