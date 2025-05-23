package com.sbi.transactions.repository;

import com.sbi.transactions.model.BalanceEnquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceEnquiryRepository extends JpaRepository<BalanceEnquiryEntity, Long> {

    Optional<BalanceEnquiryEntity> findByAccountNumber(long accountNumber);

    Optional<BalanceEnquiryEntity> findByEmail(String email);
}
