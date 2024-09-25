package com.occassionreminder.service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.occassionreminder.exceptions.AppException;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;
import com.occassionreminder.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	UserRepository userRepo;
	PasswordEncoder passEncoder;

	public UserServiceImpl(UserRepository userRepo, PasswordEncoder passEncoder) {
		super();
		this.passEncoder = passEncoder;
		this.userRepo = userRepo;
	}

	@Override
	public String registerUser(User user) {
		try {
			
			String hashPwd = passEncoder.encode(user.getPassword());
			user.setPassword(hashPwd);
			User back = userRepo.save(user);
			return "User saved with id: " + back.getUserID();
		}
		catch(Exception ex) {
			return "An exception occured: " + ex.getMessage();
		}
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
		ArrayList<Occassion> occassions = new ArrayList<Occassion>(user.getOccassions());
		return occassions;
	}

	@Override
	public String login(User user) {
		User found = userRepo.findByEmail(user.getEmail())
				.orElseThrow(()-> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		if(passEncoder.matches(user.getPassword(), found.getPassword())) {
			return found.getUserID();
		}
		return new AppException("Invalid password", HttpStatus.BAD_REQUEST).getMessage();
	}

}
