package com.debesh.spring.budgetmanagementsystem.util;

import com.debesh.spring.budgetmanagementsystem.entity.Wallet;
import com.debesh.spring.budgetmanagementsystem.model.WalletOutputModel;

public class MapWallet {
	public static WalletOutputModel mapToWalletOutput(Wallet wallet) {
		WalletOutputModel walletOutputModel = new WalletOutputModel();
		walletOutputModel.setId(wallet.getId());
		walletOutputModel.setAccName(wallet.getAccName());
		walletOutputModel.setAccNumber(wallet.getAccNumber());
		walletOutputModel.setDescription(wallet.getDescription());
		walletOutputModel.setCurrentBalance(wallet.getCurrentBalance());
		
		return walletOutputModel;
	}
}
