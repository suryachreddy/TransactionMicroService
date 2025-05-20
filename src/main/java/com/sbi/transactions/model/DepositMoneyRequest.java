package com.sbi.transactions.model;

import lombok.Data;

@Data
public class DepositMoneyRequest {
    private int id;
    private long accountNumber;
    private Double lastCreditedAmount;
    private Double availableBalance;
}
