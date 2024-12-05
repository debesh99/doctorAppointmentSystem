package com.debesh.spring.budgetmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;
import com.debesh.spring.budgetmanagementsystem.model.UserInputModel;
import com.debesh.spring.budgetmanagementsystem.model.UserOutputModel;
import com.debesh.spring.budgetmanagementsystem.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	// Create user
	@PostMapping("/register")
	public ResponseEntity<UserOutputModel> createUser(@RequestBody UserInputModel userInputModel) throws InvalidUserCredentialsException {
		logger.info("Creating a new user: {}", userInputModel);
		UserOutputModel createdUser = userServiceImpl.createUser(userInputModel);
		logger.info("User created successfully: {}", createdUser);
		return new ResponseEntity<UserOutputModel>(createdUser, HttpStatus.OK);
	}

	// User login
	@PostMapping("/login")
	public ResponseEntity<UserOutputModel> userLogin(@RequestBody UserInputModel userInputModel) throws InvalidUserCredentialsException {
		logger.info("User login attempt for email: {}", userInputModel.getEmail());
		UserOutputModel existingUser = userServiceImpl.userLogin(userInputModel.getEmail(), userInputModel.getPassword());
		logger.info("User logged in successfully: {}");
		return new ResponseEntity<UserOutputModel>(existingUser, HttpStatus.OK);
	}

//	This method is implemented just for knowledge sake.
//	All users
//	@GetMapping("/getallusers")
//	public ResponseEntity<?> getAll() {
//		return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
//	}

}
//controller Advice