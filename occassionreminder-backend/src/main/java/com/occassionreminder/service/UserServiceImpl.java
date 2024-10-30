package com.occassionreminder.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
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
	//PasswordEncoder passEncoder;
	AuthenticationManager authman;
	ObjectMapper mapper = new ObjectMapper();

	public UserServiceImpl(UserRepository userRepo) {
		super();
		//this.passEncoder = passEncoder;
		this.userRepo = userRepo;
	}

	@Override
	public ResponseEntity<String> registerUser(User user) throws JsonProcessingException {
		/*
		 * Optional<User> check = userRepo.findByEmail(user.getEmail()); if
		 * (check.isPresent()) { return new
		 * ResponseEntity<>(MyConstants.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST); }
		 * else { String hashPwd = passEncoder.encode(user.getPassword());
		 * user.setPassword(hashPwd); User back = userRepo.save(user); return new
		 * ResponseEntity<>(mapper.writeValueAsString(back.getUserID()),
		 * HttpStatus.CREATED); }
		 */
		return null;
	}

	@Override
	public ResponseEntity<String>  editUser(User user) {
		Optional<User> original = userRepo.findByUserID(user.getUserID());
		if(!original.isPresent()) {
			return new ResponseEntity<>(MyConstants.INVALID_USERID, HttpStatus.BAD_REQUEST);
		}
		else {
			userRepo.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getUserID());
		}
		return new ResponseEntity<>(MyConstants.UPDATE_SUCCESSFUL, HttpStatus.OK);
	}

	@Override
	public String deleteUser(String userID) {
		// TODO Auto-generated method stub
		return null;

	}
	
	@Override
	public ResponseEntity<String>  getUserProfile(String userID) {
		Optional<User> user = userRepo.findByUserID(userID);
		if(user.isPresent()) {
			try {
				return new ResponseEntity<>(
			    		mapper.writeValueAsString(
			    				new User(user.get().getUserID(),user.get().getFirstName(), user.get().getLastName(),user.get().getEmail()))
			    		, HttpStatus.OK);
			}
			catch(Exception e) {
				return new ResponseEntity<String>(MyConstants.ERROR_OCCURED,HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>(MyConstants.INVALID_USERID,HttpStatus.BAD_REQUEST);
	}

	@Override
	public ArrayList<Occassion> getOccassions(String userID) {
		User user = userRepo.getById(userID);
		ArrayList<Occassion> occassions = new ArrayList<Occassion>(user.getOccassions());
		return occassions;
	}

	@Override
	public ResponseEntity<String> login(Authentication authentication) {
		try {
			Optional<User> found = userRepo.findByEmail(authentication.getName());
			if (!found.isPresent()) {
				return new ResponseEntity<>(MyConstants.INVALID_USERNAME_PASSWORD, HttpStatus.BAD_REQUEST);
			}
			else {
			    return new ResponseEntity<>(
			    		mapper.writeValueAsString(
			    				new AuthResponseData(found.get().getEmail(), found.get().getUserID(), 
			    						true, found.get().getFirstName(), found.get().getLastName()))
			    		, HttpStatus.OK);
			}
		} 
		catch (Exception exception) {
			return new ResponseEntity<String>(MyConstants.INVALID_USERNAME_PASSWORD, HttpStatus.BAD_REQUEST);
		}

	}

}
