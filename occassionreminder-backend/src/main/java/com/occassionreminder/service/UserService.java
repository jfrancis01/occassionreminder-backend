package com.occassionreminder.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;

public interface UserService {
	
	public ResponseEntity<String> registerUser(User user) throws JsonProcessingException;
	public ResponseEntity<String> login(Authentication authentication);
	public ResponseEntity<String>  editUser(User user);
	public String deleteUser(String userID);
	public ResponseEntity<String> getUserProfile(String userID);
	public ArrayList<Occassion> getOccassions(String userID);

}
