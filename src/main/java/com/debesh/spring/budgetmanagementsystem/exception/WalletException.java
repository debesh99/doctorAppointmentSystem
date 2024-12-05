package com.debesh.spring.budgetmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WalletException extends Exception {

	public WalletException(String message) {
		super(message);
	}

}
