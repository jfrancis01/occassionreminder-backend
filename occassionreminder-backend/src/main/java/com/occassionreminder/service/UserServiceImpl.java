package com.occassionreminder.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;
import com.occassionreminder.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	UserRepository userRepo;

	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public String registerUser(User user) {
		User back = userRepo.save(user);
		return "User saved with id: " + back.getUserID();
	}

	@Override
	public String editUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserProfile(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Occassion> getOccassions(String userID) {
		User user = userRepo.getById(userID);
		return user.getOccassions();
	}

}
