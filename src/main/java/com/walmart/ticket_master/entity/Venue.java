 package com.walmart.ticket_master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="venue")
public class Venue {

	@Id
	@Column(name="level_id")
    private int levelId;
	
	@Column(name="level_name")
    private String levelName;
	
	@Column(name="price")
    private float price;
    
	@Column(name="number_of_rows")
    private int rows;
    
	@Column(name="seats_in_row")
    private int seatsInRow;
    
    public Venue(){
    	
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeatsInRow() {
        return seatsInRow;
    }

    public void setSeatsInRow(int seatsInRow) {
        this.seatsInRow = seatsInRow;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "levelId=" + levelId +
                ", levelName='" + levelName + '\'' +
                ", price=" + price +
                ", rows=" + rows +
                ", seatsInRow=" + seatsInRow +
                '}';
    }
}
