package com.debesh.spring.budgetmanagementsystem.service;

import java.util.List;

import com.debesh.spring.budgetmanagementsystem.exception.UserNotFoundException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.WalletInputModel;
import com.debesh.spring.budgetmanagementsystem.model.WalletOutputModel;

public interface WalletService {
	WalletOutputModel create(Long userId, WalletInputModel walletInputModel)throws UserNotFoundException,WalletException;
	
	WalletOutputModel update(WalletInputModel walletInputModel, Long id) throws WalletException;

    boolean deleteWallet(Long id) throws WalletException;

    WalletOutputModel getWalletById(Long id) throws WalletException;

    List<WalletOutputModel> getAllWallets(Long userId) throws UserNotFoundException;
}

