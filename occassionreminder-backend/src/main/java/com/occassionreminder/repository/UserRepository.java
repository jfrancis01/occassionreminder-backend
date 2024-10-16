package com.occassionreminder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.occassionreminder.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);
	@Query("SELECT new User (u.userID, u.firstName, u.lastName, u.email) from User u where u.userID = ?1")
	Optional<User> findByUserID(String userID);
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE User u set u.email = :email, u.firstName = :firstname, u.lastName =:lastname where u.userID = :userID")
	void updateUser(@Param("email") String email, @Param("firstname") String firstname, @Param("lastname") String lastname, @Param("userID") String userID);
}
