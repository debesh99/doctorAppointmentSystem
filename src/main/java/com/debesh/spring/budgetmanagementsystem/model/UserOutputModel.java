package com.debesh.spring.budgetmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputModel {
	private long id;
	private String email;
	private String username;
	private String fullName;

}
