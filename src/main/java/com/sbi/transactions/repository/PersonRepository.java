package com.sbi.transactions.repository;

import com.sbi.transactions.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
