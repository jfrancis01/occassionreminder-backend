package com.occassionreminder.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.occassionreminder.model.Occassion;

public interface OccassionService {
	
	public int addOccassion(Occassion occassion);
	public void editOccassion(Occassion occassion);
	public Occassion getOccassion(int occassionID);
	public List<Occassion> getOccassions(String userID);
	public void deleteOccassion(int occassionID);
	
}
