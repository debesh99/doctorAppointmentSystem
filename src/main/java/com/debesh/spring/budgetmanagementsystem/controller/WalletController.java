package com.debesh.spring.budgetmanagementsystem.controller;

import java.util.List;

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

import com.debesh.spring.budgetmanagementsystem.exception.UserNotFoundException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.WalletInputModel;
import com.debesh.spring.budgetmanagementsystem.model.WalletOutputModel;
import com.debesh.spring.budgetmanagementsystem.service.impl.ValidationErrorServiceImpl;
import com.debesh.spring.budgetmanagementsystem.service.impl.WalletServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallet")
@CrossOrigin
public class WalletController {

	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private WalletServiceImpl walletService;

	@Autowired
	private ValidationErrorServiceImpl validationErrorService;

	@PostMapping("/createwallet/{user_id}")
	public ResponseEntity<?> create(@PathVariable Long user_id, @Valid @RequestBody WalletInputModel walletInputModel,
			BindingResult result) throws UserNotFoundException, WalletException {
		logger.info("Creating wallet: {}", walletInputModel);
		ResponseEntity<?> errors = validationErrorService.validate(result);
		if (errors != null) {
			logger.warn("Validation errors occurred while creating wallet.");
			return errors;
		}
		WalletOutputModel walletSaved = walletService.create(user_id, walletInputModel);
		logger.info("Wallet created: {}", walletSaved);
		return new ResponseEntity<WalletOutputModel>(walletSaved, HttpStatus.CREATED);
	}

	@PutMapping("/updatewallet/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
			@Valid @RequestBody WalletInputModel walletInputModel, BindingResult result) throws WalletException{
		logger.info("Updating wallet with ID {}: {}", id, walletInputModel);
		ResponseEntity<?> errors = validationErrorService.validate(result);
		if (errors != null) {
			logger.warn("Validation errors occurred while updating wallet.");
			return errors;
		}
		WalletOutputModel walletSaved = walletService.update(walletInputModel, id);
		logger.info("Wallet updated: {}", walletSaved);
		return new ResponseEntity<WalletOutputModel>(walletSaved, HttpStatus.OK);
	}

	@GetMapping("/getwallet/{id}")
	public ResponseEntity<WalletOutputModel> getWallet(@PathVariable Long id) throws WalletException {
		logger.info("Retrieving wallet with ID: {}", id);
		WalletOutputModel walletOutput = walletService.getWalletById(id);
		logger.info("Retrieved wallet: {}", walletOutput);
		return new ResponseEntity<WalletOutputModel>(walletOutput, HttpStatus.OK);
	}

	@GetMapping("/getallwallet/{user_id}")
	public ResponseEntity<?> getAll(@PathVariable Long user_id) throws UserNotFoundException {
		logger.info("Retrieving all wallets for user ID: {}", user_id);
		List<WalletOutputModel> walletList = walletService.getAllWallets(user_id);
		logger.info("Retrieved {} wallets for user ID: {}", walletList.size(), user_id);
		return new ResponseEntity<>(walletList, HttpStatus.OK);
	}

	@DeleteMapping("/deletewallet/{id}")
	public ResponseEntity<?> deleteWallet(@PathVariable Long id) throws WalletException {
		logger.info("Deleting wallet with ID: {}", id);
		walletService.deleteWallet(id);
		logger.info("Wallet with ID {} deleted successfully.", id);
		return ResponseEntity.ok("Wallet with id " + id + " successfully deleted");
	}
}
