package com.occassionreminder.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.occassionreminder.constants.MyConstants;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/occassionsreminder")
public class Controller {
	
	@GetMapping
	public List<Occassion> getOccassions(){
		ArrayList<Occassion> occassions = new ArrayList<Occassion>();
		occassions.add(new Occassion(1, "Joh Walsh", MyConstants.OccassionType.Birthday.toString(), 
				setDate(1985, 10, 19), true, MyConstants.Offset.daybefore.toString()));
		occassions.add(new Occassion(1, "Wolf Blitz", MyConstants.OccassionType.Anniversary.toString(), 
				setDate(2017, 8, 05), true, MyConstants.Offset.dayoff.toString()));
		occassions.add(new Occassion(1, "Tejo Panda", MyConstants.OccassionType.Birthday.toString(), 
				setDate(2023, 4, 26), true, MyConstants.Offset.weekbefore.toString()));
		occassions.add(new Occassion(1, "Markus Kane", MyConstants.OccassionType.Graduation.toString(), 
				setDate(2044, 4, 26), true, MyConstants.Offset.weekbefore.toString()));
		occassions.add(new Occassion(1, "Kiki Bambrick", MyConstants.OccassionType.Confirmation.toString(), 
				setDate(2029, 10, 1), true, MyConstants.Offset.daybefore.toString()));
		return occassions;
	}
	
	@PostMapping("/add")
	public String createOccassion(@RequestBody Occassion occasion) {
		return ("Created Occasion");
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(user).toString();
			System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

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
