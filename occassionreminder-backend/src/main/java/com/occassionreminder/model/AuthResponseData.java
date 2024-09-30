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

	String email;
	String userID;
	
	public AuthResponseData() {
		
	}
	
	public AuthResponseData(String email, String userID) {
		this.email = email;
		this.userID = userID;
	}
	
}


