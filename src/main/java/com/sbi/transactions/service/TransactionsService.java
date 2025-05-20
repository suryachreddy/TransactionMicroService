package com.sbi.transactions.service;

import com.sbi.transactions.model.DepositMoneyEntity;
import com.sbi.transactions.model.DepositMoneyRequest;
import com.sbi.transactions.repository.DepositMoneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sbi.transactions.constants.ApiConstants.DEPOSIT_SUCCESSFUL;
import static com.sbi.transactions.constants.ApiConstants.INVALID_ACCOUNT_NUMBER;

@Slf4j
@Service
public class TransactionsService {

    @Autowired
     private DepositMoneyRepository moneyRepository;

    ModelMapper modelMapper = new ModelMapper();

    public String depositMoney(DepositMoneyRequest moneyRequest) {
        //fetch available balance
        Optional<DepositMoneyEntity> moneyEntity = moneyRepository.findByAccountNumber(moneyRequest.getAccountNumber());
        if (!(moneyEntity.isPresent())) {
            log.info(INVALID_ACCOUNT_NUMBER);
            return INVALID_ACCOUNT_NUMBER;
        }
        DepositMoneyEntity existDepositMoneyEntity = moneyEntity.get();
        existDepositMoneyEntity.setLastCreditedAmount(moneyRequest.getLastCreditedAmount());
        existDepositMoneyEntity.setAvailableBalance(calculateAvailableBalance(existDepositMoneyEntity, moneyRequest));
        moneyRepository.save(existDepositMoneyEntity);
        log.info("money deposited successfully!!!");
        return moneyRequest.getLastCreditedAmount() + DEPOSIT_SUCCESSFUL;
    }

    private double calculateAvailableBalance(DepositMoneyEntity existDepositMoneyEntity, DepositMoneyRequest moneyRequest) {
        return Optional.ofNullable(existDepositMoneyEntity.getAvailableBalance()).orElse(0.0) +
                Optional.ofNullable(moneyRequest.getLastCreditedAmount()).orElse(0.0);
    }
}
