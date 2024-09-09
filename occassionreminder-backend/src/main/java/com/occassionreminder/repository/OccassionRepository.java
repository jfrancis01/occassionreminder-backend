package com.occassionreminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.occassionreminder.model.Occassion;

public interface OccassionRepository extends JpaRepository<Occassion, Integer> {

}
