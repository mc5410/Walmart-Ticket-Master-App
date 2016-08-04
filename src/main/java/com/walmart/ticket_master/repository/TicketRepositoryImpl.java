package com.walmart.ticket_master.repository;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.walmart.ticket_master.entity.HoldSeat;
import com.walmart.ticket_master.entity.ReserveSeat;
import com.walmart.ticket_master.entity.Venue;


@Repository
@Transactional
public class TicketRepositoryImpl implements TicketRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Venue findVenueByLevel(int levelId) {
		
		Venue venue = em.find(Venue.class,levelId);
		
		return venue;
	}

	@Override
	public List<Venue> findAllVenues() {
		
		String query = "SELECT u FROM Venue u ORDER BY u.levelId ASC";
		List<Venue> venues = em.createQuery(query, Venue.class).getResultList();
		return venues;
	}
	
	@Override
	public List<HoldSeat> findSeatHoldsByLevel(int level) {
		
		TypedQuery<HoldSeat> qlString = em.createNamedQuery("HoldSeat.findHoldSeat", HoldSeat.class);
		qlString.setParameter("level", level);
        final List<HoldSeat> seatHoldsLevel =  qlString.getResultList();
        return seatHoldsLevel;
	}
	
	@Override
	public List<HoldSeat> findSeatHoldsByEmail(String customerEmail) {
		
		TypedQuery<HoldSeat> qlString = em.createNamedQuery("HoldSeat.findHoldSeatByEmail", HoldSeat.class);
		qlString.setParameter("id", customerEmail);
        final List<HoldSeat> seatHoldsLevel =  qlString.getResultList();
        return seatHoldsLevel;
	}

	@Override
	public List<ReserveSeat> findSeatReservesByLevel(int level) {
		
		TypedQuery<ReserveSeat> qlString = em.createNamedQuery("ReserveSeat.findReserveSeat", ReserveSeat.class);
		qlString.setParameter("level", level);

        final List<ReserveSeat> seatReserveLevel =  qlString.getResultList();

        return seatReserveLevel;
	}
	
    @Override
    public List<HoldSeat> findExpiredSeatHolds(Timestamp expiredTime) {
    		
    		TypedQuery<HoldSeat> qlString = em.createNamedQuery("HoldSeat.deleteExpired", HoldSeat.class);
    		qlString.setParameter("expiredTime", expiredTime);

            final List<HoldSeat> expiredSeatHoldIds =  qlString.getResultList();
   
            return expiredSeatHoldIds;

    }

	@Override
	public void deleteSeatHolds(List<HoldSeat> seatHold) {
		
		for(HoldSeat hs : seatHold){
			
			em.remove(hs);
		}		
		
	}

	@Override
	public List<HoldSeat> saveSeatHold(List<HoldSeat> seatHold) {
		
		for(HoldSeat hs:seatHold){
			
			em.persist(hs);
		}
		return seatHold;
	}
	
	@Override
	public List<ReserveSeat> saveSeatReserve(List<ReserveSeat> seatReserve, List<HoldSeat> seatHold) {
		
		for(ReserveSeat rs: seatReserve){
			
			em.persist(rs);
		}
		
		for(HoldSeat hs: seatHold){
			
			em.remove(hs);
		}
		
		return seatReserve;
	}

}
