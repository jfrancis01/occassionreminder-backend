package com.occassionreminder.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.occassionreminder.model.Occassion;

public interface OccassionRepository extends JpaRepository<Occassion, Integer> {
	
	ArrayList<Occassion> findByUserID(String userID);
}
