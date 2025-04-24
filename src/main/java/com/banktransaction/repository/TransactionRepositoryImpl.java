package com.banktransaction.repository;

import com.banktransaction.entity.Transaction;
import com.banktransaction.enums.TransactionType;
import com.banktransaction.exceptions.TransactionDupException;
import com.banktransaction.exceptions.TransactionInvalidException;
import com.banktransaction.exceptions.TransactionNotExistsException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements ITransactionRepository{
    private final ConcurrentHashMap<Long, Transaction> transactions = new ConcurrentHashMap<Long, Transaction>();

    @Override
    public Transaction save(Transaction transaction) {
        List<String> errorMsg = validate(transaction);
        if (!errorMsg.isEmpty()) {
            throw new TransactionInvalidException(errorMsg);
        }

        if (transactions.containsKey(transaction.getId())) {
            throw new TransactionDupException(List.of(transaction.getId().toString()));
        }

        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        List<String> errorMsg = validate(transaction);
        if (!errorMsg.isEmpty()) {
            throw new TransactionInvalidException(errorMsg);
        }

        if (!transactions.containsKey(transaction.getId())) {
            throw new TransactionNotExistsException(List.of(transaction.getId().toString()));
        }

        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public List<String> validate(Transaction transaction) {
        List<String> errorMsg = new ArrayList<>();
        if (transaction.getId() == null) {
            errorMsg.add("Transaction id is required");
        }

        if (transaction.getAmount() == null || transaction.getType() == null || transaction.getCategory() == null) {
            errorMsg.add("Transaction amount is required");
        }

        if (transaction.getType() == null) {
            errorMsg.add("Transaction type is required");
        }

        if (transaction.getCategory() == null) {
            errorMsg.add("Transaction category is required");
        }

        if (transaction.getCategory().isIncome()) {
            if (transaction.getType() != TransactionType.INCOME) {
                errorMsg.add("Invalid transaction type: " + transaction.getType());
            }
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                errorMsg.add("Income amount should be greater than zero");
            }
        }

        if (transaction.getCategory().isPayment()) {
            if (transaction.getType() != TransactionType.PAYMENT) {
                errorMsg.add("Invalid transaction type: " + transaction.getType());
            }
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
                errorMsg.add("Payment amount should be less than zero");
            }
        }
        return errorMsg;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public void deleteById(Long id) {
        if (!transactions.containsKey(id)) {
            throw new TransactionNotExistsException(List.of(id.toString()));
        }
        transactions.remove(id);
    }

    @Override
    public List<Transaction> listTransactionPagination(int page, int size) {
        int total = transactions.size();
        int start = Math.min(page * size, total);
        return transactions.values().stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return transactions.size();
    }
}