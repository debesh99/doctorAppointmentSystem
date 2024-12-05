package com.debesh.spring.budgetmanagementsystem.service.impl;

import org.springframework.stereotype.Service;

import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;
import com.debesh.spring.budgetmanagementsystem.service.UserEmailAndPasswordValidationService;

@Service
public class UserEmailAndPasswordValidationServiceImpl implements UserEmailAndPasswordValidationService {

	@Override
	public Boolean validateEmailAndPassword(String email, String password) throws InvalidUserCredentialsException {
		boolean isValid = isValidEmailAndPassword(email, password);

		if (!isValid) {
			if(!isValidEmail(email)) {
				throw new InvalidUserCredentialsException("Invalid email");
			}
			if(!isValidPassword(password)) {
				throw new InvalidUserCredentialsException("Invalid password");
			}
		}
		return isValid;
	}

	public boolean isValidEmailAndPassword(String email, String password) {
		// Add your email and password validation logic here
		// For example, you can use regular expressions or other validation rules
		// Return true if the email and password are valid, false otherwise
		return isValidEmail(email) && isValidPassword(password);
	}

	public boolean isValidEmail(String email) {
		// Add email validation logic here
		// For example, you can use regular expressions or other validation rules
		// Return true if the email is valid, false otherwise
		return email != null && email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}");
	}

	public boolean isValidPassword(String password) {
		// Add password validation logic here
		// For example, you can define password length requirements or other validation
		// rules
		// Return true if the password is valid, false otherwise
		return password != null && password.length() >= 8;
	}
}
