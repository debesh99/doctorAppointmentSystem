package com.debesh.spring.budgetmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.debesh.spring.budgetmanagementsystem.model.ErrorOutputModel;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(WalletException.class)
	public ResponseEntity<ErrorOutputModel> handleWalletException(WalletException e, WebRequest request) {
		ErrorOutputModel errorOutputModel = new ErrorOutputModel();
		errorOutputModel.setErrorCode(404);
		errorOutputModel.setErrorMessgae(e.getMessage());
		return new ResponseEntity<ErrorOutputModel>(errorOutputModel, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidUserCredentialsException.class)
	public ResponseEntity<?> handleWalletException(InvalidUserCredentialsException e, WebRequest request) {
		ErrorOutputModel errorOutputModel = new ErrorOutputModel();
		errorOutputModel.setErrorCode(400);
		errorOutputModel.setErrorMessgae(e.getMessage());
		return new ResponseEntity<ErrorOutputModel>(errorOutputModel, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleWalletException(UserNotFoundException e, WebRequest request) {
		ErrorOutputModel errorOutputModel = new ErrorOutputModel();
		errorOutputModel.setErrorCode(400);
		errorOutputModel.setErrorMessgae(e.getMessage());
		return new ResponseEntity<ErrorOutputModel>(errorOutputModel, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<?> handleWalletException(TransactionException e, WebRequest request) {
		ErrorOutputModel errorOutputModel = new ErrorOutputModel();
		errorOutputModel.setErrorCode(400);
		errorOutputModel.setErrorMessgae(e.getMessage());
		return new ResponseEntity<ErrorOutputModel>(errorOutputModel, HttpStatus.BAD_REQUEST);
	}

}
