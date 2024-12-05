package com.debesh.spring.budgetmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.debesh.spring.budgetmanagementsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1 AND u.password = ?2")
	Optional<User> userLogin(String email, String password);
	
	Optional<User> findByEmail(String email);
}
