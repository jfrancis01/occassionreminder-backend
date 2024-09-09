package com.occassionreminder.service;

import com.occassionreminder.model.User;

public interface UserService {
	
	public String registerUser(User user);
	public String editUser(User user);
	public String deleteUser(String userID);
	public User getUserProfile(String userID);

}
