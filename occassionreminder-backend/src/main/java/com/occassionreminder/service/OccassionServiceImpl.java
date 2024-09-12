package com.occassionreminder.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.occassionreminder.model.Occassion;
import com.occassionreminder.repository.OccassionRepository;


@Service
public class OccassionServiceImpl implements OccassionService {
	
	OccassionRepository occRepo;
	

	public OccassionServiceImpl(OccassionRepository occRepo) {
		super();
		this.occRepo = occRepo;
	}


	@Override
	public int addOccassion(Occassion occassion) {
		Occassion back = this.occRepo.save(occassion);
		return back.getOccassionID();
	}


	@Override
	public void editOccassion(Occassion occassion) {
		occRepo.save(occassion);
	}


	@Override
	public Occassion getOccassion(int occassionID) {
		return occRepo.findById(occassionID).get();
	}
	
	
}
