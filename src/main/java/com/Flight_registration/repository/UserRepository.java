package com.Flight_registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Flight_registration.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
