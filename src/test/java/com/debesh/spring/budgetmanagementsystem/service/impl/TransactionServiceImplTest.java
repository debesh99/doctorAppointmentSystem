package com.debesh.spring.budgetmanagementsystem.service.impl;

// Additional tests for other methods (create, update, delete) can be added similarly
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.debesh.spring.budgetmanagementsystem.entity.Transaction;
import com.debesh.spring.budgetmanagementsystem.entity.Wallet;
import com.debesh.spring.budgetmanagementsystem.exception.TransactionException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.repository.TransactionRepository;
import com.debesh.spring.budgetmanagementsystem.repository.WalletRepository;

public class TransactionServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private WalletRepository walletRepository;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAll_WhenWalletExists_ReturnsTransactions() throws WalletException {
		// Arrange
		Long walletId = 1L;
		Wallet wallet = new Wallet();
		wallet.setId(walletId);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction());
		transactions.add(new Transaction());

		when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
		when(transactionRepository.findByWallet(wallet)).thenReturn(transactions);

		// Act
		List<Transaction> result = transactionService.getAll(walletId);

		// Assert
		assertEquals(transactions, result);
	}

	@Test
	public void testGetAll_WhenWalletDoesNotExist_ThrowsWalletException() {
		// Arrange
		Long walletId = 1L;

		when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(WalletException.class, () -> transactionService.getAll(walletId));
	}

	@Test
	public void testGetById_WhenWalletAndTransactionExist_ReturnsTransaction()
			throws WalletException, TransactionException {
		// Arrange
		Long walletId = 1L;
		Long transactionId = 2L;

		Wallet wallet = new Wallet();
		wallet.setId(walletId);

		Transaction transaction = new Transaction();
		transaction.setId(transactionId);

		when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
		when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

		// Act
		Transaction result = transactionService.getById(walletId, transactionId);

		// Assert
		assertEquals(transaction, result);
	}

	@Test
	public void testGetById_WhenWalletDoesNotExist_ThrowsWalletException() {
		// Arrange
		Long walletId = 1L;
		Long transactionId = 2L;

		when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(WalletException.class, () -> transactionService.getById(walletId, transactionId));
	}

	@Test
	public void testGetById_WhenTransactionDoesNotExist_ThrowsTransactionException() throws WalletException {
		// Arrange
		Long walletId = 1L;
		Long transactionId = 2L;

		Wallet wallet = new Wallet();
		wallet.setId(walletId);

		when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
		when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(TransactionException.class, () -> transactionService.getById(walletId, transactionId));
	}

	// Additional tests for other methods (create, update, delete) can be added
	// similarly

}
