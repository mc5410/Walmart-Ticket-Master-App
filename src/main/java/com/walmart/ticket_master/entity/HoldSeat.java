package com.walmart.ticket_master.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="hold_seats")
@NamedQueries ({
	@NamedQuery(name="HoldSeat.deleteExpired", query="SELECT h FROM HoldSeat h WHERE h.modified < :expiredTime"),
	@NamedQuery(name="HoldSeat.findHoldSeat", query="SELECT h FROM HoldSeat h WHERE h.levelId = :level"),
	@NamedQuery(name="HoldSeat.findHoldSeatByEmail", query="SELECT h FROM HoldSeat h WHERE h.emailAddress = :id")
})													
public class HoldSeat implements Comparable<HoldSeat> {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable= false)
	private long dbId;

	@Column(nullable= false)
	private Integer levelId;

	@Column(nullable= false)
	private Integer seats;

	@Column(nullable= false)
	private Date modified;

	@Column(nullable= false)
	private String emailAddress;


	public HoldSeat() {

	}

	public long getDbId() {
		return this.dbId;
	}
	
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	public Integer getLevelId() {
		return this.levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Date getModified() {
		return (Date)this.modified.clone();
	}

	public void setModified(Date modified) {
		this.modified = (Date) modified.clone();
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	

	public HoldSeat(long dbId, Integer levelId, Integer seats, Date modified, String emailAddress) {
		super();
		this.dbId = dbId;
		this.levelId = levelId;
		this.seats = seats;
		this.modified = modified;
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "HoldSeat [dbId=" + dbId + ", levelId=" + levelId + ", seats=" + seats + ", modified=" + modified
				+ ", emailAddress=" + emailAddress + "]";
	}

	@Override
	public int compareTo(HoldSeat o) {
		// TODO Auto-generated method stub
		return 0;
	}
}