package com.walmart.ticket_master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="reserve_seats")
@NamedQueries ({
	@NamedQuery(name="ReserveSeat.findReserveSeat", query="SELECT h FROM ReserveSeat h WHERE h.levelId = :level")
})

public class ReserveSeat {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
	
	@Column(nullable= false)
    private long holdId;
    
	@Column(nullable= false)
    private int levelId;
    
	@Column(nullable= false)
    private String custemail;
    
	@Column(nullable= false)
    private int numberOfSeats;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getHoldId() {
        return holdId;
    }

    public void setHoldId(long holdId) {
        this.holdId = holdId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
	public String getCustemail() {
		return custemail;
	}

	public void setCustemail(String customerEmail) {
		this.custemail = customerEmail;
	}

    @Override
    public String toString() {
        return "SeatBooking{" +
                "id=" + id +
                ", holdId=" + holdId +
                ", levelId=" + levelId +
                ", custemail=" +custemail+
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }


}
