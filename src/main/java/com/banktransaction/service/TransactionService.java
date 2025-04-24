package com.banktransaction.service;

import com.banktransaction.entity.Transaction;
import com.banktransaction.enums.TransactionCategory;
import com.banktransaction.enums.TransactionType;
import com.banktransaction.exceptions.TransactionNotExistsException;
import com.banktransaction.repository.TransactionRepositoryImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepositoryImpl transactionRepositoryImpl;

    public TransactionService(TransactionRepositoryImpl transactionRepositoryImpl) {
        this.transactionRepositoryImpl = transactionRepositoryImpl;
    }

    public Transaction createTransaction(Long id, BigDecimal amount, TransactionType type, TransactionCategory category) {
        Transaction transaction = new Transaction(id, amount, type, category, LocalDateTime.now());
        return transactionRepositoryImpl.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepositoryImpl.deleteById(id);
    }

    public Transaction updateTransaction(Long id, BigDecimal amount, TransactionType type, TransactionCategory category) {
        Optional<Transaction> optionalTransaction = transactionRepositoryImpl.findById(id);
        if (optionalTransaction.isEmpty()) {
            throw new TransactionNotExistsException(List.of(id.toString()));
        }
        Transaction transaction = optionalTransaction.get();
        if (amount == null && type ==null && category == null) {
            return transaction;
        }
        if (amount != null) {
            transaction.setAmount(amount);
        }
        if (type != null) {
            transaction.setType(type);
        }
        if (category != null) {
            transaction.setCategory(category);
        }
        return transactionRepositoryImpl.update(transaction);
    }

    public List<Transaction> listAllTransactions(int page, int size) {
        return transactionRepositoryImpl.listTransactionPagination(page, size);
    }

    public int count() {
        return transactionRepositoryImpl.count();
    }
}