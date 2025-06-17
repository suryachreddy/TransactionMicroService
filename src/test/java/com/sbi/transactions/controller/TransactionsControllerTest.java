package com.sbi.transactions.controller;

import com.sbi.transactions.model.AccountDetails;
import com.sbi.transactions.model.BalanceEnquiryRequest;
import com.sbi.transactions.model.BalanceEnquiryResponse;
import com.sbi.transactions.service.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.sbi.transactions.constants.ApiConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionsControllerTest {

    @Mock
    TransactionsService transactionsService;

    @InjectMocks
    TransactionsController transactionsController;

    private static final String EMAIL = "test@gmail.com";
    private static final long ACCOUNT_NUMBER = 1234;
    private static final String ACCOUNT_CREATED = "Account Created";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDepositMoney_validAccount() {
        BalanceEnquiryRequest request = new BalanceEnquiryRequest();
        when(transactionsService.depositMoney(request)).thenReturn("Success");

        ResponseEntity<Object> response = transactionsController.depositMoney(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Success", response.getBody());
    }

    @Test
    void testDepositMoney_invalidAccount() {
        BalanceEnquiryRequest request = new BalanceEnquiryRequest();
        when(transactionsService.depositMoney(request)).thenReturn(INVALID_ACCOUNT_NUMBER);

        ResponseEntity<Object> response = transactionsController.depositMoney(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(INVALID_ACCOUNT_NUMBER, response.getBody());
    }

    @Test
    void testSaveAccount() {
        AccountDetails accountDetails = new AccountDetails();
        when(transactionsService.saveAccountDetails(accountDetails)).thenReturn(ACCOUNT_CREATED);

        ResponseEntity<Object> response = transactionsController.saveAccount(accountDetails);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(ACCOUNT_CREATED, response.getBody());
    }

    @Test
    void testAccountDetails() {
        BalanceEnquiryResponse balanceEnquiryResponse = new BalanceEnquiryResponse();
        balanceEnquiryResponse.setAccountNumber(ACCOUNT_NUMBER);

        when(transactionsService.balanceEnquiryResponse(EMAIL)).thenReturn(balanceEnquiryResponse);
        ResponseEntity<BalanceEnquiryResponse> response = transactionsController.getAccountDetails(EMAIL);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(ACCOUNT_NUMBER, Objects.requireNonNull(response.getBody()).getAccountNumber());
    }

    @Test
    void testDeleteAccount_failure() {
        when(transactionsService.deleteAccount(EMAIL)).thenReturn(INVALID_EMAIL);
        ResponseEntity<Object> response = transactionsController.deleteAccount(EMAIL);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(INVALID_EMAIL, Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    void testDeleteAccount_success() {
        when(transactionsService.deleteAccount(EMAIL)).thenReturn(ACCOUNT_DELETED_SUCCESSFULLY);
        ResponseEntity<Object> response = transactionsController.deleteAccount(EMAIL);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(ACCOUNT_DELETED_SUCCESSFULLY, Objects.requireNonNull(response.getBody()).toString());
    }
}
