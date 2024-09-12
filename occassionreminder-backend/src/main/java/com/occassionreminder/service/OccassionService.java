package com.occassionreminder.service;

import java.util.ArrayList;

import com.occassionreminder.model.Occassion;

public interface OccassionService {
	
	public int addOccassion(Occassion occassion);
	public void editOccassion(Occassion occassion);
	public Occassion getOccassion(int occassionID);
	
}
