package com.occassionreminder.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CUSTOM_OCCASSION")
public class Occassion {

	public int getOccassionID() {
		return occassionID;
	}

	public void setOccassionID(int occassionID) {
		this.occassionID = occassionID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccassionType() {
		return occassionType;
	}

	public void setOccassionType(String occassionType) {
		this.occassionType = occassionType;
	}

	public Date getOccasstionDate() {
		return occasstionDate;
	}

	public void setOccasstionDate(Date occasstionDate) {
		this.occasstionDate = occasstionDate;
	}

	public boolean isReminderOn() {
		return reminderOn;
	}

	public void setReminderOn(boolean reminderOn) {
		this.reminderOn = reminderOn;
	}

	public String getOffSetReminder() {
		return offSetReminder;
	}

	public void setOffSetReminder(String offSetReminder) {
		this.offSetReminder = offSetReminder;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int occassionID;
	private String name;
	private String occassionType;
	private Date occasstionDate;
	private boolean reminderOn;
	private String offSetReminder;
	private String userID;
	

	public Occassion() {
		
	}
	
	public Occassion(int occassionID, String name, String occassionType, Date occasstionDate, boolean reminderOn,
			String offSetReminder) {
		super();
		this.occassionID = occassionID;
		this.name = name;
		this.occassionType = occassionType;
		this.occasstionDate = occasstionDate;
		this.reminderOn = reminderOn;
		this.offSetReminder = offSetReminder;
	}
	
}
