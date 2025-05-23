package com.sbi.transactions.model;

import lombok.Data;

@Data
public class BalanceEnquiryResponse {
    private long accountNumber;
    private Double lastCreditedAmount;
    private Double availableBalance;
    private String email;
}
