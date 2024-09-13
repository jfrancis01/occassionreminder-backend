package com.occassionreminder.model;

import java.util.Date;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.NonNull;
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
		return occassionDate;
	}

	public void setOccasstionDate(Date occassionDate) {
		this.occassionDate = occassionDate;
	}

	public boolean isReminderOn() {
		return reminderOn;
	}

	public void setReminderOn(boolean reminderOn) {
		this.reminderOn = reminderOn;
	}

	public String getOffSetReminder() {
		return offsetReminder;
	}

	public void setOffSetReminder(String offsetReminder) {
		this.offsetReminder = offsetReminder;
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
	@JsonProperty("name")
	@NonNull
	private String name;
	@JsonProperty("occassionType")
	@NonNull
	private String occassionType;
	@JsonProperty("occassionDate")
	@NonNull
	private Date occassionDate;
	@JsonProperty("reminderOn")
	@NonNull
	private boolean reminderOn;
	@JsonProperty("offsetReminder")
	@NonNull
	private String offsetReminder;
	@JsonProperty("userID")
	@NonNull
	private String userID;

	public Occassion() {
		
	}
	
	public Occassion(String name, String occassionType, Date occassionDate, boolean reminderOn,
			String offsetReminder) {
		super();
		this.name = name;
		this.occassionType = occassionType;
		this.occassionDate = occassionDate;
		this.reminderOn = reminderOn;
		this.offsetReminder = offsetReminder;
	}
	
}
