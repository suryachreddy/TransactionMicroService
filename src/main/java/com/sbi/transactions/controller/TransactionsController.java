package com.sbi.transactions.controller;

import com.sbi.transactions.model.AccountDetails;
import com.sbi.transactions.model.BalanceEnquiryRequest;
import com.sbi.transactions.model.BalanceEnquiryResponse;
import com.sbi.transactions.service.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sbi.transactions.constants.ApiConstants.INVALID_ACCOUNT_NUMBER;

@RestController
@Slf4j
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/deposit")
    public ResponseEntity<Object> depositMoney(@RequestBody BalanceEnquiryRequest moneyRequest) {
        String response = transactionsService.depositMoney(moneyRequest);
        if (response.equals(INVALID_ACCOUNT_NUMBER)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/account")
    public ResponseEntity<Object> saveAccount(@RequestBody AccountDetails accountDetails) {
        log.info("account details to save : {}", accountDetails);
        String response = transactionsService.saveAccountDetails(accountDetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/account/{email}")
    public ResponseEntity<BalanceEnquiryResponse> getAccountDetails(@PathVariable String email) {
        log.info("Request received for account details :{}", email);
        BalanceEnquiryResponse response = transactionsService.balanceEnquiryResponse(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
