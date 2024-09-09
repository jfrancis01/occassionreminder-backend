package com.occassionreminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.occassionreminder.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
