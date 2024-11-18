package com.occassionreminder.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.repository.OccassionRepository;


@Service
public class OccassionServiceImpl implements OccassionService {
	
	OccassionRepository occRepo;
	
	ObjectMapper mapper = new ObjectMapper();
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


	@Override
	public List<Occassion> getOccassions(String userID) {
		return occRepo.findByUserID(userID);
	}


	@Override
	public void deleteOccassion(int occassionID) {
		occRepo.deleteById(occassionID);
	}

	
}
