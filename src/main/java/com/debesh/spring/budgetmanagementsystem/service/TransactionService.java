package com.debesh.spring.budgetmanagementsystem.service;

import java.util.List;

import com.debesh.spring.budgetmanagementsystem.entity.Transaction;
import com.debesh.spring.budgetmanagementsystem.exception.TransactionException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.TransactionInputModel;

public interface TransactionService {
    List<Transaction> getAll(Long walletId) throws WalletException;

    Transaction getById(Long walletId, Long id) throws WalletException,TransactionException;

    Transaction create(Long walletId, TransactionInputModel transactionInputModel) throws WalletException;
    
    Transaction update(Long walletId, Long id, TransactionInputModel transactionInputModel) throws WalletException,TransactionException;

    boolean delete(Long walletId, Long id) throws WalletException,TransactionException;
}
