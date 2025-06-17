package com.sbi.transactions.model;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "BALANCE_ENQUIRY")
public class BalanceEnquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long accountNumber;
    private Double lastCreditedAmount;
    private Double availableBalance;
    private String email;
}
