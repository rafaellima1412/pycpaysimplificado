package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pycpaysimplificado.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
