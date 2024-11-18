package com.occassionreminder.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;
import com.occassionreminder.service.OccassionService;
import com.occassionreminder.service.UserService;

@RestController
@RequestMapping("/occassionsreminder")
public class Controller {
	
	UserService userService;
	OccassionService occassionService;
	
	public Controller(UserService userService, OccassionService occassionService) {
		super();
		this.userService = userService;
		this.occassionService = occassionService;
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		Calendar cal = Calendar.getInstance();
		return "Welcome to the Occassions Reminder REST Service " + new Date(cal.getTimeInMillis());
	}
	
	/**
	 * This method gets all the Occassions depending on the 
	 * logged in user
	 * @param userID
	 * @return
	 */

	@GetMapping("/occassions")
	public List<Occassion> getOccassions(@RequestParam String  userID){
		return this.occassionService.getOccassions(userID);
	}
	
	/**
	 * This method is used to read a single occasion
	 * @param occassionID
	 * @return
	 */
	@GetMapping("/occassion")
	public Occassion getOccassion(@RequestParam int occassionID) {
		Occassion back = this.occassionService.getOccassion(occassionID);
		System.out.print(back.toString());
		return back;
	}
	
	/**
	 * This method adds an occasion to the list of occassions 
	 * for the logged in user
	 * @param occassion
	 * @return
	 */
	
	@PostMapping("/add")
	public String createOccassion(@RequestBody Occassion occassion) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(occassionService.addOccassion(occassion)).toString();
			System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * This method updates an existing occassion record
	 * @param occassion
	 */
	
	@PutMapping("/edit")
	public void editOccassion(@RequestBody Occassion occassion) {
		occassionService.editOccassion(occassion);
	}
	
	
	/**
	 * This method allows a user to register using an
	 * email and password
	 * @param user
	 * @return
	 * @throws JsonProcessingException 
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) throws JsonProcessingException {
		return userService.registerUser(user);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(Authentication authentication){
		return userService.login(authentication);
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<String> getUser(@RequestParam String userID) {
		return userService.getUserProfile(userID);
	}
	
	@PutMapping("/editUser")
	public ResponseEntity<String> editUser(@RequestBody User user) {
			return userService.editUser(user);
	}
	
	@GetMapping("/delete")
	public void delete(@RequestParam int  occassionID) {
		occassionService.deleteOccassion(occassionID);
	}
	
	private Date setDate(int year, int month, int day) {
		
		Date currentDate = new Date();
		currentDate.setYear(year);
		currentDate.setMonth(month);
		currentDate.setDate(day);
		return currentDate;
		
	}
	
	private String formatDate(Date date ) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
		String currentDateTime = dateFormat.format(date);
		return currentDateTime;
	}
}
