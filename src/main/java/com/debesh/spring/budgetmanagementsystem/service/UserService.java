package com.debesh.spring.budgetmanagementsystem.service;

import com.debesh.spring.budgetmanagementsystem.exception.InvalidUserCredentialsException;
import com.debesh.spring.budgetmanagementsystem.model.UserInputModel;
import com.debesh.spring.budgetmanagementsystem.model.UserOutputModel;

public interface UserService {
	UserOutputModel createUser(UserInputModel userInputModel) throws InvalidUserCredentialsException;;

	UserOutputModel userLogin(String email, String password) throws InvalidUserCredentialsException;

//	List<User> getAllUsers();
}
