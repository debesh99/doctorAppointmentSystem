package com.debesh.spring.budgetmanagementsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.debesh.spring.budgetmanagementsystem.entity.User;
import com.debesh.spring.budgetmanagementsystem.entity.Wallet;
import com.debesh.spring.budgetmanagementsystem.exception.UserNotFoundException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.WalletInputModel;
import com.debesh.spring.budgetmanagementsystem.model.WalletOutputModel;
import com.debesh.spring.budgetmanagementsystem.repository.UserRepository;
import com.debesh.spring.budgetmanagementsystem.repository.WalletRepository;

class WalletServiceImplTest {

	@Mock
	private WalletRepository walletRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private WalletServiceImpl walletService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreate() throws UserNotFoundException, WalletException {
		Long userId = 1L;

		WalletInputModel inputModel = new WalletInputModel();
		inputModel.setAccName("Test Wallet");
		inputModel.setAccNumber("1234567890");
		inputModel.setDescription("Test wallet description");
		inputModel.setPriority(1);

		User user = new User();
		user.setId(userId);

		Wallet wallet = new Wallet();
		wallet.setId(1L);
		wallet.setAccName(inputModel.getAccName());
		wallet.setAccNumber(inputModel.getAccNumber());
		wallet.setDescription(inputModel.getDescription());
		wallet.setPriority(inputModel.getPriority());
		wallet.setUser(user);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(walletRepository.findByAccNumber(inputModel.getAccNumber())).thenReturn(Optional.empty());
		when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

		WalletOutputModel createdWallet = walletService.create(userId, inputModel);

		assertNotNull(createdWallet);
//		assertEquals(wallet.getId(), createdWallet.getId());
		assertEquals(wallet.getAccName(), createdWallet.getAccName());
		assertEquals(wallet.getAccNumber(), createdWallet.getAccNumber());
		assertEquals(wallet.getDescription(), createdWallet.getDescription());
//		assertEquals(wallet.getPriority(), createdWallet.getPriority());

		verify(userRepository, times(1)).findById(userId);
		verify(walletRepository, times(1)).findByAccNumber(inputModel.getAccNumber());
		verify(walletRepository, times(1)).save(any(Wallet.class));
	}

	@Test
	void testCreate_WalletAlreadyExists() {
		Long userId = 1L;

		WalletInputModel inputModel = new WalletInputModel();
		inputModel.setAccName("Test Wallet");
		inputModel.setAccNumber("1234567890");
		inputModel.setDescription("Test wallet description");
		inputModel.setPriority(1);

		Wallet existingWallet = new Wallet();
		existingWallet.setId(1L);

		when(walletRepository.findByAccNumber(inputModel.getAccNumber())).thenReturn(Optional.of(existingWallet));

		assertThrows(WalletException.class, () -> walletService.create(userId, inputModel));

		verify(userRepository, never()).findById(anyLong());
		verify(walletRepository, times(1)).findByAccNumber(inputModel.getAccNumber());
		verify(walletRepository, never()).save(any(Wallet.class));
	}

	@Test
	void testDeleteWallet() throws WalletException {
		Long id = 1L;

		when(walletRepository.findById(id)).thenReturn(Optional.of(new Wallet()));

		boolean result = walletService.deleteWallet(id);

		assertTrue(result);
		verify(walletRepository, times(1)).findById(id);
		verify(walletRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteWallet_WalletNotFound() {
		Long id = 1L;

		when(walletRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(WalletException.class, () -> walletService.deleteWallet(id));
		verify(walletRepository, times(1)).findById(id);
		verify(walletRepository, never()).deleteById(id);
	}

	@Test
	void testGetWalletById() throws WalletException {
		Long id = 1L;

		Wallet wallet = new Wallet();
		wallet.setId(id);

		when(walletRepository.findById(id)).thenReturn(Optional.of(wallet));

		WalletOutputModel walletOutput = walletService.getWalletById(id);

		assertNotNull(walletOutput);
		assertEquals(wallet.getId(), walletOutput.getId());
		verify(walletRepository, times(1)).findById(id);
	}

	@Test
	void testGetWalletById_WalletNotFound() {
		Long id = 1L;

		when(walletRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(WalletException.class, () -> walletService.getWalletById(id));
		verify(walletRepository, times(1)).findById(id);
	}

	@Test
	void testGetAllWallets() throws UserNotFoundException {
		Long userId = 1L;

		User user = new User();
		user.setId(userId);

		List<Wallet> walletList = new ArrayList<>();
		Wallet wallet1 = new Wallet();
		wallet1.setId(1L);
		wallet1.setUser(user);
		walletList.add(wallet1);
		Wallet wallet2 = new Wallet();
		wallet2.setId(2L);
		wallet2.setUser(user);
		walletList.add(wallet2);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(walletRepository.findAllByUserIdOrderByPriority(userId)).thenReturn(walletList);

		List<WalletOutputModel> walletOutputList = walletService.getAllWallets(userId);

		assertNotNull(walletOutputList);
		assertEquals(walletList.size(), walletOutputList.size());
		verify(userRepository, times(1)).findById(userId);
		verify(walletRepository, times(1)).findAllByUserIdOrderByPriority(userId);
	}

	@Test
	void testGetAllWallets_UserNotFound() {
		Long userId = 1L;

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> walletService.getAllWallets(userId));
		verify(userRepository, times(1)).findById(userId);
		verify(walletRepository, never()).findAllByUserIdOrderByPriority(anyLong());
	}

	@Test
	void testUpdate_WalletNotFound() {
		Long id = 1L;

		WalletInputModel inputModel = new WalletInputModel();
		inputModel.setAccName("Updated Wallet");
		inputModel.setAccNumber("0987654321");
		inputModel.setDescription("Updated wallet description");
		inputModel.setPriority(2);

		when(walletRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(WalletException.class, () -> walletService.update(inputModel, id));
		verify(walletRepository, times(1)).findById(id);
		verify(walletRepository, never()).save(any(Wallet.class));
	}

	@Test
	void testUpdate() throws WalletException {
		Long id = 1L;

		WalletInputModel inputModel = new WalletInputModel();
		inputModel.setAccName("Updated Wallet");
		inputModel.setAccNumber("0987654321");
		inputModel.setDescription("Updated wallet description");
		inputModel.setPriority(2);

		Wallet wallet = new Wallet();
		wallet.setId(id);
		wallet.setAccName(inputModel.getAccName());
		wallet.setAccNumber(inputModel.getAccNumber());
		wallet.setDescription(inputModel.getDescription());
		wallet.setPriority(inputModel.getPriority());

		when(walletRepository.findById(id)).thenReturn(Optional.of(wallet));
		when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

		WalletOutputModel updatedWallet = walletService.update(inputModel, id);

		assertNotNull(updatedWallet);
		assertEquals(wallet.getId(), updatedWallet.getId());
		assertEquals(wallet.getAccName(), updatedWallet.getAccName());
		assertEquals(wallet.getAccNumber(), updatedWallet.getAccNumber());
		assertEquals(wallet.getDescription(), updatedWallet.getDescription());
//		assertEquals(wallet.getPriority(), updatedWallet.getPriority());

		verify(walletRepository, times(1)).findById(id);
		verify(walletRepository, times(1)).save(any(Wallet.class));
	}
}
