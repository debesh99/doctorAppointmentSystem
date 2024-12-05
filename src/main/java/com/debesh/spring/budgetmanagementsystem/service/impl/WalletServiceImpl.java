package com.debesh.spring.budgetmanagementsystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.debesh.spring.budgetmanagementsystem.entity.User;
import com.debesh.spring.budgetmanagementsystem.entity.Wallet;
import com.debesh.spring.budgetmanagementsystem.exception.UserNotFoundException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.WalletInputModel;
import com.debesh.spring.budgetmanagementsystem.model.WalletOutputModel;
import com.debesh.spring.budgetmanagementsystem.repository.UserRepository;
import com.debesh.spring.budgetmanagementsystem.repository.WalletRepository;
import com.debesh.spring.budgetmanagementsystem.service.WalletService;
import com.debesh.spring.budgetmanagementsystem.util.MapWallet;

@Service
public class WalletServiceImpl implements WalletService {
	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository userRepository;

//	create or update
	@Transactional
	public WalletOutputModel create(Long userId, WalletInputModel walletInputModel)
			throws UserNotFoundException, WalletException {
		Wallet wallet2 = walletRepository.findByAccNumber(walletInputModel.getAccNumber()).orElse(null);
	    if (wallet2 != null) {
	        throw new WalletException("Account already exists");
	    }
		Wallet wallet = new Wallet();
		wallet.setAccName(walletInputModel.getAccName());
		wallet.setAccNumber(walletInputModel.getAccNumber());
		wallet.setDescription(walletInputModel.getDescription());
		wallet.setPriority(walletInputModel.getPriority());

		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
		
				wallet.setUser(user);
				walletRepository.save(wallet);
				return MapWallet.mapToWalletOutput(wallet);
			}
		throw new UserNotFoundException("User not found");
	}

	// delete
	@Transactional
	public boolean deleteWallet(Long id) throws WalletException {
		Wallet wallet = walletRepository.findById(id).orElse(null);
		if (wallet != null) {
			walletRepository.deleteById(id);
			return true;
		} else {
			throw new WalletException("Wallet not found with id: " + id);
		}
	}

//  read
	@Transactional
	public WalletOutputModel getWalletById(Long id) throws WalletException {
		Wallet wallet = walletRepository.findById(id).orElse(null);
		if (wallet != null) {
			return MapWallet.mapToWalletOutput(wallet);
		} else {
			throw new WalletException("Wallet not found with id: " + id);
		}
	}

	@Transactional
	public List<WalletOutputModel> getAllWallets(Long userId) throws UserNotFoundException {
		User user = userRepository.findById(userId).orElse(null);

		if (user != null) {
			List<Wallet> walletList = walletRepository.findAllByUserIdOrderByPriority(userId);
			List<WalletOutputModel> walletOutputModels = new ArrayList<>();
			WalletOutputModel walletOutputModel = new WalletOutputModel();
			for (Wallet wallet : walletList) {
				walletOutputModel = MapWallet.mapToWalletOutput(wallet);
				walletOutputModels.add(walletOutputModel);
			}
			return walletOutputModels;
		} else {
			throw new UserNotFoundException("User with id " + userId + " not found");

		}
	}

	@Override
	public WalletOutputModel update(WalletInputModel walletInputModel, Long id)
			throws WalletException {
		Wallet wallet = walletRepository.findById(id).orElse(null);
		if (wallet != null) {
			wallet.setAccName(walletInputModel.getAccName());
			wallet.setAccNumber(walletInputModel.getAccNumber());
			wallet.setDescription(walletInputModel.getDescription());
			wallet.setPriority(walletInputModel.getPriority());
			walletRepository.save(wallet);
			return MapWallet.mapToWalletOutput(wallet);
		} else {
			throw new WalletException("Wallet not found with id: " + id);
		}
	}

}
