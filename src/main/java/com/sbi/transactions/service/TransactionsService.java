package com.sbi.transactions.service;

import com.sbi.transactions.model.AccountDetails;
import com.sbi.transactions.model.BalanceEnquiryEntity;
import com.sbi.transactions.model.BalanceEnquiryRequest;
import com.sbi.transactions.model.BalanceEnquiryResponse;
import com.sbi.transactions.repository.BalanceEnquiryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sbi.transactions.constants.ApiConstants.*;

@Slf4j
@Service
public class TransactionsService {

    @Autowired
    private BalanceEnquiryRepository repository;

    ModelMapper modelMapper = new ModelMapper();

    public String depositMoney(BalanceEnquiryRequest moneyRequest) {
        //fetch available balance
        Optional<BalanceEnquiryEntity> moneyEntity = repository.findByAccountNumber(moneyRequest.getAccountNumber());
        if (!(moneyEntity.isPresent())) {
            log.info(INVALID_ACCOUNT_NUMBER);
            return INVALID_ACCOUNT_NUMBER;
        }
        BalanceEnquiryEntity existDepositMoneyEntity = moneyEntity.get();
        existDepositMoneyEntity.setLastCreditedAmount(moneyRequest.getLastCreditedAmount());
        existDepositMoneyEntity.setAvailableBalance(calculateAvailableBalance(existDepositMoneyEntity, moneyRequest));
        repository.save(existDepositMoneyEntity);
        log.info("Money deposited successfully!!!");
        return moneyRequest.getLastCreditedAmount() + DEPOSIT_SUCCESSFUL;
    }

    private double calculateAvailableBalance(BalanceEnquiryEntity existDepositMoneyEntity, BalanceEnquiryRequest moneyRequest) {
        return Optional.ofNullable(existDepositMoneyEntity.getAvailableBalance()).orElse(0.0) +
                Optional.ofNullable(moneyRequest.getLastCreditedAmount()).orElse(0.0);
    }

    public String saveAccountDetails(AccountDetails accountDetails) {
        BalanceEnquiryEntity balanceEnquiry = new BalanceEnquiryEntity();
        balanceEnquiry.setAccountNumber(accountDetails.getAccountNumber());
        balanceEnquiry.setEmail(accountDetails.getEmail());
        repository.save(balanceEnquiry);
        log.info(ACCOUNT_DETAILS_SAVED_SUCCESSFULLY);
        return ACCOUNT_DETAILS_SAVED_SUCCESSFULLY;
    }

    public BalanceEnquiryResponse balanceEnquiryResponse(String email) {
        BalanceEnquiryResponse balanceEnquiryResponse = null;
        Optional<BalanceEnquiryEntity> optionalBalanceEnquiryEntity = repository.findByEmail(email);
        if (optionalBalanceEnquiryEntity.isPresent()) {
            BalanceEnquiryEntity balanceEnquiry = optionalBalanceEnquiryEntity.get();
            balanceEnquiryResponse = modelMapper.map(balanceEnquiry, BalanceEnquiryResponse.class);
        }
        log.info("account details : {}", balanceEnquiryResponse);
        return balanceEnquiryResponse;
    }

    @Transactional
    public String deleteAccount(String email) {
      Optional<BalanceEnquiryEntity> optionalBalance  = repository.findByEmail(email);
      if (optionalBalance.isPresent()) {
          BalanceEnquiryEntity balanceEnquiry = optionalBalance.get();
          if (null != balanceEnquiry.getAvailableBalance() && balanceEnquiry.getAvailableBalance() > 0) {
              log.info(ACCOUNT_DELETION_MESSAGE);
              return ACCOUNT_DELETION_MESSAGE;
          }
          repository.deleteByEmail(email);
          log.info(ACCOUNT_DELETED_SUCCESSFULLY);
      } else {
          log.info(INVALID_EMAIL);
          return INVALID_EMAIL;
      }
      return ACCOUNT_DELETED_SUCCESSFULLY;
    }
}
