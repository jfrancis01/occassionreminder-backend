package com.occassionreminder.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.occassionreminder.constants.MyConstants;
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
	public ResponseEntity<String> registerUser(User user) throws JsonProcessingException {
		Optional<User> check = userRepo.findByEmail(user.getEmail());
		if (check.isPresent()) {
			return new ResponseEntity<>(MyConstants.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
		} else {
			String hashPwd = passEncoder.encode(user.getPassword());
			user.setPassword(hashPwd);
			User back = userRepo.save(user);
			ObjectMapper mapper = new ObjectMapper();
			return new ResponseEntity<>(mapper.writeValueAsString(back.getUserID()), HttpStatus.CREATED);
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
		User user = userRepo.findByUserID(userID)
				.orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
		return user;
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
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		if (passEncoder.matches(user.getPassword(), found.getPassword())) {
			return found.getUserID();
		}
		return new AppException("Invalid password", HttpStatus.BAD_REQUEST).getMessage();
	}

}
