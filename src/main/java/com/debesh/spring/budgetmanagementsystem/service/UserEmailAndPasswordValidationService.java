package com.debesh.spring.budgetmanagementsystem.service;

import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;

public interface UserEmailAndPasswordValidationService {
	Boolean validateEmailAndPassword(String email, String password) throws InvalidUserCredentialsException;
}
