package com.sbi.transactions.util;

import com.sbi.transactions.model.AccountDetails;
import com.sbi.transactions.model.BalanceEnquiryEntity;
import com.sbi.transactions.model.BalanceEnquiryRequest;

public class TestData {

    private static final long ACCOUNT_NUMBER = 1234;

    public static BalanceEnquiryRequest balanceEnquiryRequest() {
        BalanceEnquiryRequest request = new BalanceEnquiryRequest();
        request.setAccountNumber(ACCOUNT_NUMBER);
        request.setLastCreditedAmount(500.0);
        return request;
    }

    public static BalanceEnquiryEntity balanceEnquiryEntity () {
        BalanceEnquiryEntity existingEntity = new BalanceEnquiryEntity();
        existingEntity.setAccountNumber(ACCOUNT_NUMBER);
        existingEntity.setEmail("user@example.com");
        existingEntity.setAvailableBalance(1000.0);
        return existingEntity;
    }

    public static AccountDetails accountDetails() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNumber(ACCOUNT_NUMBER);
        accountDetails.setEmail("user@example.com");
        return accountDetails;
    }
}
