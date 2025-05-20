package com.sbi.transactions.repository;

import com.sbi.transactions.model.DepositMoneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositMoneyRepository extends JpaRepository<DepositMoneyEntity, Integer> {

    Optional<DepositMoneyEntity> findByAccountNumber(long accountNumber);
}
