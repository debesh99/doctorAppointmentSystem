package com.debesh.spring.budgetmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.debesh.spring.budgetmanagementsystem.exception.UserNotFoundException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.WalletInputModel;
import com.debesh.spring.budgetmanagementsystem.model.WalletOutputModel;
import com.debesh.spring.budgetmanagementsystem.service.impl.ValidationErrorServiceImpl;
import com.debesh.spring.budgetmanagementsystem.service.impl.WalletServiceImpl;

class WalletControllerTest {

    @Mock
    private WalletServiceImpl walletService;

    @Mock
    private ValidationErrorServiceImpl validationErrorService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() throws UserNotFoundException, WalletException {
        Long userId = 1L;
        WalletInputModel inputModel = new WalletInputModel();
        WalletOutputModel savedWallet = new WalletOutputModel();
        when(walletService.create(userId, inputModel)).thenReturn(savedWallet);

        ResponseEntity<?> response = walletController.create(userId, inputModel, mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedWallet, response.getBody());
        verify(walletService, times(1)).create(userId, inputModel);
        verify(validationErrorService, times(1)).validate(any(BindingResult.class));
    }

    @Test
    void testUpdate() throws UserNotFoundException, WalletException {
        Long id = 1L;
//        Long id = 1L;
        WalletInputModel inputModel = new WalletInputModel();
        WalletOutputModel updatedWallet = new WalletOutputModel();
        when(walletService.update(inputModel, id)).thenReturn(updatedWallet);

        ResponseEntity<?> response = walletController.update(id, inputModel, mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedWallet, response.getBody());
        verify(walletService, times(1)).update(inputModel, id);
        verify(validationErrorService, times(1)).validate(any(BindingResult.class));
    }

    @Test
    void testGetWallet() throws WalletException {
        Long id = 1L;
        WalletOutputModel walletOutput = new WalletOutputModel();
        when(walletService.getWalletById(id)).thenReturn(walletOutput);

        ResponseEntity<WalletOutputModel> response = walletController.getWallet(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(walletOutput, response.getBody());
        verify(walletService, times(1)).getWalletById(id);
    }

    @Test
    void testGetAll() throws UserNotFoundException {
        Long userId = 1L;
        List<WalletOutputModel> walletList = new ArrayList<>();
        walletList.add(new WalletOutputModel());
        when(walletService.getAllWallets(userId)).thenReturn(walletList);

        ResponseEntity<?> response = walletController.getAll(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(walletList, response.getBody());
        verify(walletService, times(1)).getAllWallets(userId);
    }

    @Test
    void testDeleteWallet() throws WalletException {
        Long id = 1L;

        ResponseEntity<?> response = walletController.deleteWallet(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wallet with id " + id + " successfully deleted", response.getBody());
        verify(walletService, times(1)).deleteWallet(id);
    }
}
