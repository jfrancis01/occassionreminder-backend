package com.occassionreminder.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;

public interface UserService {
	
	public ResponseEntity<String> registerUser(User user);
	public String login(User user);
	public String editUser(User user);
	public String deleteUser(String userID);
	public User getUserProfile(String userID);
	public ArrayList<Occassion> getOccassions(String userID);

}
