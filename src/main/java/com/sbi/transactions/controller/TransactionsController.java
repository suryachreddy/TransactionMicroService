package com.sbi.transactions.controller;

import com.sbi.transactions.model.DepositMoneyRequest;
import com.sbi.transactions.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sbi.transactions.constants.ApiConstants.INVALID_ACCOUNT_NUMBER;

@RestController
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/deposit")
    public ResponseEntity<Object> depositMoney(@RequestBody DepositMoneyRequest moneyRequest) {
        String response = transactionsService.depositMoney(moneyRequest);
        if (response.equals(INVALID_ACCOUNT_NUMBER)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
