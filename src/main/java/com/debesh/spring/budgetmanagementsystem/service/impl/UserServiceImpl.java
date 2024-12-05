package com.debesh.spring.budgetmanagementsystem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.debesh.spring.budgetmanagementsystem.entity.User;
import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;
import com.debesh.spring.budgetmanagementsystem.model.UserInputModel;
import com.debesh.spring.budgetmanagementsystem.model.UserOutputModel;
import com.debesh.spring.budgetmanagementsystem.repository.UserRepository;
import com.debesh.spring.budgetmanagementsystem.service.UserService;
import com.debesh.spring.budgetmanagementsystem.util.MapUser;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserEmailAndPasswordValidationServiceImpl checkEmailAndPassword;

	@Transactional
	public UserOutputModel createUser(UserInputModel userInputModel) throws InvalidUserCredentialsException {
		if (checkEmailAndPassword.validateEmailAndPassword(userInputModel.getEmail(), userInputModel.getPassword())) {
			User user2 = userRepository.findByEmail(userInputModel.getEmail()).orElse(null);
			if(user2!=null) {
				throw new InvalidUserCredentialsException("User email already exists");
			}
			logger.info("Creating user: {}", userInputModel);
			User user = new User();
			user.setFirstName(userInputModel.getFirstName());
			user.setLastName(userInputModel.getLastName());
			user.setEmail(userInputModel.getEmail());
			user.setUsername(userInputModel.getUsername());
			user.setPassword(userInputModel.getPassword());
			userRepository.save(user);

			UserOutputModel userOutputModel = MapUser.mapToUserOutput(user);
			return userOutputModel;
		}
		throw new InvalidUserCredentialsException("Please provide email and password in correct format");
	}

//	user login
	@Transactional
	public UserOutputModel userLogin(String email, String password) throws InvalidUserCredentialsException {
		logger.info("User login attempt for email: {}", email);
		User user = userRepository.userLogin(email, password).orElse(null);
		if (user != null) {
			logger.info("User logged in successfully: {}", user);
			UserOutputModel userOutputModel = MapUser.mapToUserOutput(user);
			return userOutputModel;
		}
		logger.error("Invalid user credentials for email: {}", email);
		throw new InvalidUserCredentialsException("Invalid user credentials!");
	}

}
