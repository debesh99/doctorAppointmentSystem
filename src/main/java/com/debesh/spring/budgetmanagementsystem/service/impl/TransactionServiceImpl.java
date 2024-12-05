package com.debesh.spring.budgetmanagementsystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.debesh.spring.budgetmanagementsystem.entity.Transaction;
import com.debesh.spring.budgetmanagementsystem.entity.Wallet;
import com.debesh.spring.budgetmanagementsystem.exception.TransactionException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.TransactionInputModel;
import com.debesh.spring.budgetmanagementsystem.repository.TransactionRepository;
import com.debesh.spring.budgetmanagementsystem.repository.WalletRepository;
import com.debesh.spring.budgetmanagementsystem.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private WalletRepository walletRepository;

//	get
	public List<Transaction> getAll(Long walletId) throws WalletException {
		Wallet wallet = walletRepository.findById(walletId).orElse(null);
		if (wallet != null) {
			return transactionRepository.findByWallet(wallet);
		}
		throw new WalletException("Wallet with " + walletId + " does not exists!");
	}

	public Transaction getById(Long walletId, Long id) throws WalletException, TransactionException {
		Wallet wallet = walletRepository.findById(walletId).orElse(null);
		if (wallet != null) {
			Transaction transaction = transactionRepository.findById(id).orElse(null);
			if (transaction != null) {
				return transaction;
			} else {
				throw new TransactionException("Transaction with " + id + " does not exists!");
			}
		}
		throw new WalletException("Wallet with " + id + " does not exists!");
	}

//	create
	public Transaction create(Long walletId, TransactionInputModel transactionInputModel) throws WalletException {
		Wallet wallet = walletRepository.findById(walletId).orElse(null);
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionInputModel.getAmount());
		transaction.setDescription(transactionInputModel.getDescription());
		transaction.setType(transactionInputModel.getType());
		if (wallet != null) {
			transaction.setWallet(wallet);
			if (transaction.getType() == 1) {
				wallet.setCurrentBalance(wallet.getCurrentBalance() + transaction.getAmount());
			}
			if (transaction.getType() == 2) {
				wallet.setCurrentBalance(wallet.getCurrentBalance() - transaction.getAmount());
			}
			return transactionRepository.save(transaction);
		}
		throw new WalletException("Wallet with id " + walletId + " does not exist");
	}

//	delete
	public boolean delete(Long wallet_id, Long id) throws WalletException, TransactionException {
		Wallet wallet = walletRepository.findById(wallet_id).orElse(null);
		if (wallet != null) {
			Transaction transaction = transactionRepository.findById(id).orElse(null);
			if (transaction != null) {
				transactionRepository.delete(transaction);
				return true;
			} else {
				throw new TransactionException("Transaction with " + id + " does not exists!");
			}
		}
		throw new WalletException("Wallet with " + id + " does not exists!");
	}

//	update
	@Override
	public Transaction update(Long walletId, Long id, TransactionInputModel transactionInputModel)
			throws WalletException, TransactionException {
		Wallet wallet = walletRepository.findById(walletId).orElse(null);
		if (wallet != null) {
			Transaction transaction = transactionRepository.findById(id).orElse(null);
			if (transaction != null) {
				transaction.setAmount(transactionInputModel.getAmount());
				transaction.setDescription(transactionInputModel.getDescription());
				transaction.setType(transactionInputModel.getType());
				transaction.setWallet(wallet);
				if (transaction.getType() == 1) {
					wallet.setCurrentBalance(wallet.getCurrentBalance() + transaction.getAmount());
				}
				if (transaction.getType() == 2) {
					wallet.setCurrentBalance(wallet.getCurrentBalance() - transaction.getAmount());
				}
				return transactionRepository.save(transaction);
			} else {
				throw new TransactionException("Transaction with " + id + " does not exists!");
			}
		} else {
			throw new WalletException("Wallet with id " + walletId + " does not exists!");
		}
	}

}
