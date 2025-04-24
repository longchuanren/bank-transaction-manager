package com.banktransaction.repository;

import com.banktransaction.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {
    Transaction save(Transaction transaction);
    Transaction update(Transaction transaction);
    List<String> validate(Transaction transaction);
    Optional<Transaction> findById(Long id);
    void deleteById(Long id);
    List<Transaction> listTransactionPagination(int page, int size);
    int count();
}