package com.occassionreminder.model;

public class AuthResponseData {
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public AuthResponseData() {
		
	}
	
	public boolean getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(boolean authStatus) {
		this.authStatus = authStatus;
	}
	
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AuthResponseData(String email, String userID, boolean authStatus, String firstName, String lastName) {
		this.email = email;
		this.userID = userID;
		this.authStatus = authStatus;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	private String email;
	private String userID;
	private boolean authStatus;
	private String firstName;
	private String lastName;
}


