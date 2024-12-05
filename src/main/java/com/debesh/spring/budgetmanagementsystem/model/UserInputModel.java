package com.debesh.spring.budgetmanagementsystem.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInputModel {
	@Column(length = 25, nullable = false)
	private String firstName;
	
	@Column
	private String lastName;
	@Column(length = 25, nullable = false)
	private String email;

	@Column(length = 10, nullable = false)
	private String password;

	@Column(length = 7, nullable = false)
	private String username;

}
