package com.debesh.spring.budgetmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOutputModel {
	private Long id;
	private String accName;
	private String accNumber;
	private String description;
	private double currentBalance;
}
