package com.walmart.ticket_master.controller.entity;

public class ReserveSeatBody {
	
	private long seatHoldId ;
	
	private String customerEmail;
	
	@Override
	public String toString() {
		return "ReserveSeatBody [seatHoldId=" + seatHoldId + ", customerEmail=" + customerEmail + "]";
	}
	public long getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(long seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

}
