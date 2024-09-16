package com.occassionreminder.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
		Occassion original = occRepo.getById(occassion.getOccassionID());
		original.setName(occassion.getName());
		original.setOccassionType(occassion.getOccassionType());
		original.setOccasstionDate(occassion.getOccasstionDate());
		original.setOffSetReminder(occassion.getOffSetReminder());
		original.setReminderOn(occassion.isReminderOn());
		occRepo.save(original);
	}


	@Override
	public Occassion getOccassion(int occassionID) {
		return occRepo.findById(occassionID).get();
	}
	
	
}
