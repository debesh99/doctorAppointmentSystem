package com.debesh.spring.budgetmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorOutputModel {
	private int errorCode;
	private String errorMessgae;
}