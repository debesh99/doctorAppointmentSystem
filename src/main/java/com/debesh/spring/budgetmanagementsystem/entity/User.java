package com.debesh.spring.budgetmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long Id;

	@Column(length = 25, nullable = false, unique=true)
	private String email;

	@Column(length = 10, nullable = false)
	private String password;

	@Column(length = 7)
	private String username;

	@Column(length = 25, nullable = false)
	private String firstName;

	@Column
	private String lastName;
	
}
