package com.debesh.spring.budgetmanagementsystem.util;

import com.debesh.spring.budgetmanagementsystem.entity.User;
import com.debesh.spring.budgetmanagementsystem.model.UserOutputModel;

public class MapUser {
	public static UserOutputModel mapToUserOutput(User user) {
		UserOutputModel userOutputModel = new UserOutputModel();
		userOutputModel.setId(user.getId());
		userOutputModel.setEmail(user.getEmail());
		userOutputModel.setUsername(user.getUsername());
		userOutputModel.setFullName(user.getFirstName() + " " + user.getLastName());
		return userOutputModel;
	}
}
