package com.sbi.transactions.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "BALANCE_ENQUIRY")
public class DepositMoneyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long accountNumber;
    private Double lastCreditedAmount;
    private Double availableBalance;
}
