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
import com.occassionreminder.model.AuthResponseData;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;
import com.occassionreminder.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;
	PasswordEncoder passEncoder;
	ObjectMapper mapper = new ObjectMapper();

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
	public ResponseEntity<String> login(User user) throws JsonProcessingException {
		Optional<User> found = userRepo.findByEmail(user.getEmail());
		if(!found.isPresent()) {
			return new ResponseEntity<>(MyConstants.INVALID_USERNAME_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		User fromDb = found.get();
		if (passEncoder.matches(user.getPassword(), fromDb.getPassword())) {
			return new ResponseEntity<>(mapper.writeValueAsString(new AuthResponseData("********", fromDb.getUserID(), "AUTH")), HttpStatus.OK);
		}
		return new ResponseEntity<>(MyConstants.INVALID_USERNAME_PASSWORD, HttpStatus.BAD_REQUEST);
	}

}
