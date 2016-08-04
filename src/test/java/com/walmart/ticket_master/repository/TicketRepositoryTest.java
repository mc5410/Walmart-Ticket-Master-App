package com.walmart.ticket_master.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.walmart.ticket_master.TestConfig;
import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.entity.Venue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class TicketRepositoryTest extends TestConfig{
	
	
	@Mock
	private EntityManager em;
	
	@InjectMocks
	private TicketRepositoryImpl repo  = new TicketRepositoryImpl();
	
	@Mock
	private TypedQuery<Venue> query;
	
	@Mock
	private TypedQuery<HoldSeat> query1;
	
	@Mock
	private TypedQuery<ReserveSeat> query2;
	
	@Mock
	private HoldSeat hold;
	
	@Mock
	List<HoldSeat> delhold;
	
	@Mock
	List<ReserveSeat> res;
	
	@Mock
	private ReserveSeat reserve;
	
	@Mock
	private Venue venue;
	
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
	
	
	@Test
	public void findVenueByLevelTestTest(){
		
		Mockito.when(em.find(Venue.class, venue.getLevelId())).thenReturn(venue);
		Venue actual = repo.findVenueByLevel(venue.getLevelId());
		Assert.assertEquals(venue, actual);
	}
	
	
	@Test
	public void findAllVenuesTest(){
		
		List<Venue> expected = Arrays.asList(venue);
		Mockito.when(em.createQuery("SELECT u FROM Venue u ORDER BY u.levelId ASC", Venue.class)).thenReturn(query); 
		Mockito.when(query.getResultList()).thenReturn(expected);
		List<Venue> users = repo.findAllVenues();
		Assert.assertEquals(expected, users);
		
	}
	
	@Test
	public void findSeatHoldsByLevelTest() {
		
		List<HoldSeat> expected = Arrays.asList(hold);
		Mockito.when(em.createNamedQuery("HoldSeat.findHoldSeat", HoldSeat.class)).thenReturn(query1);
		Mockito.when(query1.getResultList()).thenReturn(expected);
		List<HoldSeat> actual = repo.findSeatHoldsByLevel(hold.getLevelId());
        Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findSeatHoldsByEmailTest() {
		
		List<HoldSeat> expected = Arrays.asList(hold);
		Mockito.when(em.createNamedQuery("HoldSeat.findHoldSeatByEmail", HoldSeat.class)).thenReturn(query1);
		Mockito.when(query1.getResultList()).thenReturn(expected);
		List<HoldSeat> actual = repo.findSeatHoldsByEmail(hold.getEmailAddress());
		 Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findSeatReservesByLevelTest() {
		
		List<ReserveSeat> expected = Arrays.asList(reserve);
		Mockito.when(em.createNamedQuery("ReserveSeat.findReserveSeat", ReserveSeat.class)).thenReturn(query2);
		Mockito.when(query2.getResultList()).thenReturn(expected);
		List<ReserveSeat> actual = repo.findSeatReservesByLevel(reserve.getLevelId());
		Assert.assertEquals(expected, actual);
		
	}
	
    @Test
    public void findExpiredSeatHolds() {
    	
    	List<HoldSeat> expected = Arrays.asList(hold);
		Mockito.when(em.createNamedQuery("HoldSeat.deleteExpired", HoldSeat.class)).thenReturn(query1);
		Mockito.when(query1.getResultList()).thenReturn(expected);
		List<HoldSeat> actual = repo.findExpiredSeatHolds(getExpirationInstant());
		Assert.assertEquals(expected, actual);

    }
    
	
	@Test
	public void saveSeatHoldTest() {
		
		List<HoldSeat> expected = new ArrayList<HoldSeat>();
		expected.add(hold);
		List<HoldSeat> actual = new ArrayList<HoldSeat>();
		actual.add(hold);
		Assert.assertEquals(expected, actual);
		
	}
	
				
    private Timestamp getExpirationInstant() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minusExpiredSeconds = now.minusSeconds(60);
        Timestamp timestamp = Timestamp.valueOf(minusExpiredSeconds);
        return timestamp;
    }
	
	
	
	
	

}
