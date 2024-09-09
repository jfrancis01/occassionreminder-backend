package com.occassionreminder.model;

import java.util.Date;

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

	private int occassionID;
	private String name;
	private String occassionType;
	private Date occasstionDate;
	private boolean reminderOn;
	private String offSetReminder;
	
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
