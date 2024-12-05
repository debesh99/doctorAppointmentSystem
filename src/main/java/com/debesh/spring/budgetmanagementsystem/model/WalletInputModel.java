package com.debesh.spring.budgetmanagementsystem.model;

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
public class WalletInputModel {
	@NotNull
	@Size(min = 3, max = 25)
	private String accName;

	@NotNull
	@Size(min = 10, max = 25)
	private String accNumber;

	@Size(max = 100)
	private String description;

	@NotNull
	@Min(1)
	@Max(3)
	private Integer priority; // 1. high 2. Medium 3. Low
	
}
