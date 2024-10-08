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
	
	public AuthResponseData(String email, String userID, boolean authStatus) {
		this.email = email;
		this.userID = userID;
		this.authStatus = authStatus;
	}
	
	private String email;
	private String userID;
	private boolean authStatus;
}


