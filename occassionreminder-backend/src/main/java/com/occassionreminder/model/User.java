package com.occassionreminder.model;

import java.util.ArrayList;
import java.util.Set;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="CUSTOM_USER")
public class User {
	
	public User(String userID, String firstName, String lastName, String email, String password) {
		
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public User() {
		
	}
	
	public User(String userID, String firstName, String lastName, String email) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
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

	public String getEmail() {
		return email;
	}

	public void setEmai(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public Set<Occassion> getOccassions() {
		return this.occassions;
	}

	public void setOccassions(Set<Occassion> occassions) {
		this.occassions = occassions;
	}
	
	@Override
	public String toString(){
		return String.format("User[userID=%s, firstName='%s', lastName='%s', email='%s', password='%s']", 
				userID, firstName, lastName, email , password);
	}
	
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private String email;
	@NonNull
	private String password;
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String userID;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="userid")
	private Set<Occassion> occassions;
}
