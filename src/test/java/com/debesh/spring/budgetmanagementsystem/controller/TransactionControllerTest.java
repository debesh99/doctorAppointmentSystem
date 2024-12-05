package com.debesh.spring.budgetmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.debesh.spring.budgetmanagementsystem.entity.Transaction;
import com.debesh.spring.budgetmanagementsystem.exception.TransactionException;
import com.debesh.spring.budgetmanagementsystem.exception.WalletException;
import com.debesh.spring.budgetmanagementsystem.model.TransactionInputModel;
import com.debesh.spring.budgetmanagementsystem.service.impl.TransactionServiceImpl;
import com.debesh.spring.budgetmanagementsystem.service.impl.ValidationErrorServiceImpl;

class TransactionControllerTest {

    @Mock
    private TransactionServiceImpl transactionService;

    @Mock
    private ValidationErrorServiceImpl validationErrorService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() throws WalletException {
        Long walletId = 1L;
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionService.getAll(walletId)).thenReturn(transactions);

        ResponseEntity<?> response = transactionController.getAll(walletId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(transactionService, times(1)).getAll(walletId);
    }

    @Test
    void testGetById() throws WalletException, TransactionException {
        Long walletId = 1L;
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionService.getById(walletId, transactionId)).thenReturn(transaction);

        ResponseEntity<?> response = transactionController.getById(walletId, transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
        verify(transactionService, times(1)).getById(walletId, transactionId);
    }

    @Test
    void testCreate() throws WalletException {
        Long walletId = 1L;
        TransactionInputModel inputModel = new TransactionInputModel();
        Transaction savedTransaction = new Transaction();
        when(transactionService.create(eq(walletId), any(TransactionInputModel.class))).thenReturn(savedTransaction);

        ResponseEntity<?> response = transactionController.create(walletId, inputModel, mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedTransaction, response.getBody());
        verify(transactionService, times(1)).create(walletId, inputModel);
        verify(validationErrorService, times(1)).validate(any(BindingResult.class));
    }

    @Test
    void testUpdate() throws WalletException, TransactionException {
        Long walletId = 1L;
        Long transactionId = 1L;
        TransactionInputModel inputModel = new TransactionInputModel();
        Transaction updatedTransaction = new Transaction();
        when(transactionService.update(eq(walletId), eq(transactionId), any(TransactionInputModel.class))).thenReturn(updatedTransaction);

        ResponseEntity<?> response = transactionController.update(walletId, transactionId, inputModel, mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTransaction, response.getBody());
        verify(transactionService, times(1)).update(walletId, transactionId, inputModel);
        verify(validationErrorService, times(1)).validate(any(BindingResult.class));
    }

    @Test
    void testDelete() throws WalletException, TransactionException {
        Long walletId = 1L;
        Long transactionId = 1L;

        ResponseEntity<?> response = transactionController.delete(walletId, transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction with id " + transactionId + " successfully deleted", response.getBody());
        verify(transactionService, times(1)).delete(walletId, transactionId);
    }
}
