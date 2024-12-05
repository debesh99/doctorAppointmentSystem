package com.debesh.spring.budgetmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debesh.spring.budgetmanagementsystem.entity.Transaction;
import com.debesh.spring.budgetmanagementsystem.exception.TransactionException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.TransactionInputModel;
import com.debesh.spring.budgetmanagementsystem.service.impl.TransactionServiceImpl;
import com.debesh.spring.budgetmanagementsystem.service.impl.ValidationErrorServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private ValidationErrorServiceImpl validationErrorService;

	@GetMapping("/getalltransactions/{wallet_id}")
	public ResponseEntity<?> getAll(@PathVariable Long wallet_id) throws WalletException {
		logger.info("Retrieving all transactions for wallet ID: {}", wallet_id);
		return new ResponseEntity<>(transactionService.getAll(wallet_id), HttpStatus.OK);
	}

	@GetMapping("/gettransactionbyid/{wallet_id}/{id}")
	public ResponseEntity<?> getById(@PathVariable Long wallet_id, @PathVariable Long id)
			throws WalletException, TransactionException {
		logger.info("Retrieving transaction with ID: {} for wallet ID: {}", id, wallet_id);
		return new ResponseEntity<>(transactionService.getById(wallet_id, id), HttpStatus.OK);
	}

	@PostMapping("/createtransaction/{wallet_id}")
	public ResponseEntity<?> create(@PathVariable Long wallet_id,
			@Valid @RequestBody TransactionInputModel transactionInputModel, BindingResult result)
			throws WalletException {
		logger.info("Creating transaction for wallet ID: {}", wallet_id);
		ResponseEntity<?> errors = validationErrorService.validate(result);
		if (errors != null) {
			logger.warn("Validation errors occurred while creating transaction.");
			return errors;
		}
		Transaction transactionSaved = transactionService.create(wallet_id, transactionInputModel);
		logger.info("Transaction created: {}", transactionSaved);
		return new ResponseEntity<Transaction>(transactionSaved, HttpStatus.CREATED);
	}

	@PutMapping("/updatetransaction/{wallet_id}/{id}")
	public ResponseEntity<?> update(@PathVariable Long wallet_id, @PathVariable Long id,
			@Valid @RequestBody TransactionInputModel transactionInputModel, BindingResult result)
			throws WalletException, TransactionException {
		logger.info("Updating transaction with ID: {} for wallet ID: {}", id, wallet_id);
		ResponseEntity<?> errors = validationErrorService.validate(result);
		if (errors != null) {
			logger.warn("Validation errors occurred while updating transaction.");
			return errors;
		}
		Transaction transaction = transactionService.update(wallet_id, id, transactionInputModel);
		logger.info("Transaction updated: {}", transaction);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}

	@DeleteMapping("/deletetransaction/{wallet_id}/{id}")
	public ResponseEntity<?> delete(@PathVariable Long wallet_id, @PathVariable Long id)
			throws WalletException, TransactionException {
		logger.info("Deleting transaction with ID: {} for wallet");
		transactionService.delete(wallet_id, id);
		return ResponseEntity.ok("Transaction with id " + id + " successfully deleted");
	}
}