package com.occassionreminder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.occassionreminder.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);
	@Query("SELECT new User (u.firstName, u.lastName, u.email) from User u where u.userID = ?1")
	Optional<User> findByUserID(String userID);
}
