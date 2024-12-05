package com.debesh.spring.budgetmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInputModel {
	@NotNull(message = "Amount can't be null")
	private Double amount;

	@Size(max = 100)
	private String description;

	@Column(nullable = false)
	@Min(1)
	@Max(2)
	private int type; // 1. Income 2.Expense
}
