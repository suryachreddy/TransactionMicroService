package com.sbi.transactions.service;

import com.sbi.transactions.model.AccountDetails;
import com.sbi.transactions.model.BalanceEnquiryEntity;
import com.sbi.transactions.model.BalanceEnquiryRequest;
import com.sbi.transactions.model.BalanceEnquiryResponse;
import com.sbi.transactions.repository.BalanceEnquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.sbi.transactions.constants.ApiConstants.*;
import static com.sbi.transactions.util.TestData.*;
import static com.sbi.transactions.util.TestData.balanceEnquiryEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TransactionsServiceTest {

    @InjectMocks
    private TransactionsService transactionsService;

    @Mock
    private BalanceEnquiryRepository repository;

    @Mock
    private org.modelmapper.ModelMapper modelMapper;

    private static final long ACCOUNT_NUMBER = 1234;
    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDepositMoney_ValidAccount_ShouldUpdateBalance() {
        BalanceEnquiryRequest request = balanceEnquiryRequest();
        BalanceEnquiryEntity existingEntity = balanceEnquiryEntity();

        when(repository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(existingEntity));

        String result = transactionsService.depositMoney(request);

        assertEquals("500.0 Money deposited successfully !!!", result);
        verify(repository, times(1)).save(existingEntity);
        assertEquals(1500.0, existingEntity.getAvailableBalance());
    }

    @Test
    public void testDepositMoney_inValidAccount() {
        BalanceEnquiryRequest request = balanceEnquiryRequest();

        when(repository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.empty());

        String result = transactionsService.depositMoney(request);

        assertEquals(INVALID_ACCOUNT_NUMBER, result);
        verify(repository, times(1));
    }

    @Test
    public void testSaveAccountDetails() {
        AccountDetails accountDetails = accountDetails();

        String result = transactionsService.saveAccountDetails(accountDetails);
        assertEquals(ACCOUNT_DETAILS_SAVED_SUCCESSFULLY, result);
    }

    @Test
    public void testBalanceEnquiryResponse_WhenEmailExists_ShouldReturnResponse() {
        // Arrange
        BalanceEnquiryEntity entity = new BalanceEnquiryEntity();
        entity.setEmail(EMAIL);
        entity.setAccountNumber(1234);

        BalanceEnquiryResponse expectedResponse = new BalanceEnquiryResponse();
        expectedResponse.setEmail(EMAIL);
        expectedResponse.setAccountNumber(1234);

        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, BalanceEnquiryResponse.class)).thenReturn(expectedResponse);

        // Act
        BalanceEnquiryResponse actualResponse = transactionsService.balanceEnquiryResponse(EMAIL);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(1234, actualResponse.getAccountNumber());
        assertEquals(EMAIL, actualResponse.getEmail());

        verify(repository, times(1)).findByEmail(EMAIL);
        verify(modelMapper, times(1)).map(entity, BalanceEnquiryResponse.class);
    }

    @Test
    public void testDeleteAccount_when_invalidEmail() {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        String response  = transactionsService.deleteAccount(EMAIL);
        assertEquals(INVALID_EMAIL, response);
    }

    @Test
    public void testDeleteAccount_whenBalanceAvailable() {
        BalanceEnquiryEntity balanceEnquiry = balanceEnquiryEntity();
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(balanceEnquiry));
        String response  = transactionsService.deleteAccount(EMAIL);
        assertEquals(ACCOUNT_DELETION_MESSAGE, response);
    }

    @Test
    public void testDeleteAccount() {
        BalanceEnquiryEntity balanceEnquiry = balanceEnquiryEntity();
        double value = 0;
        balanceEnquiry.setAvailableBalance(value);
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(balanceEnquiry));
        String response  = transactionsService.deleteAccount(EMAIL);
        assertEquals(ACCOUNT_DELETED_SUCCESSFULLY, response);
    }
}
