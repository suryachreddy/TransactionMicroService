package com.sbi.transactions.model;

import lombok.Data;

@Data
public class BalanceEnquiryRequest {
    private long accountNumber;
    private Double lastCreditedAmount;
    private Double availableBalance;
    private String email;
}
