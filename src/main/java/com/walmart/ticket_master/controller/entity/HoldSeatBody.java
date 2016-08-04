package com.walmart.ticket_master.controller.entity;

public class HoldSeatBody {
	
	 	private int numSeats;
	    private String custEmail;
	    private int minLevel;
	    private int maxLevel;
	    
	    
		@Override
		public String toString() {
			return "HoldSeatBody [numSeats=" + numSeats + ", custEmail=" + custEmail + ", minLevel=" + minLevel
					+ ", maxLevel=" + maxLevel + "]";
		}
		
		public int getNumSeats() {
			return numSeats;
		}
		public int getMinLevel() {
			return minLevel;
		}

		public void setMinLevel(int minLevel) {
			this.minLevel = minLevel;
		}

		public int getMaxLevel() {
			return maxLevel;
		}

		public void setMaxLevel(int maxLevel) {
			this.maxLevel = maxLevel;
		}

		public void setNumSeats(int numSeats) {
			this.numSeats = numSeats;
		}
		public String getCustEmail() {
			return custEmail;
		}
		public void setCustEmail(String custEmail) {
			this.custEmail = custEmail;
		}

	    


}
