package com.banktransaction.service;

import com.banktransaction.entity.Transaction;
import com.banktransaction.enums.TransactionCategory;
import com.banktransaction.enums.TransactionType;
import com.banktransaction.exceptions.TransactionDupException;
import com.banktransaction.exceptions.TransactionInvalidException;
import com.banktransaction.exceptions.TransactionNotExistsException;
import com.banktransaction.repository.TransactionRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private final TransactionService transactionService = new TransactionService(new TransactionRepositoryImpl());

    @Test
    void createTransactionSuccess() {
        Transaction transaction = transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY);
        assertNotNull(transaction);
        assertEquals(1L, transaction.getId());
        assertEquals(BigDecimal.valueOf(100.0), transaction.getAmount());
        assertEquals(TransactionType.INCOME, transaction.getType());
        assertEquals(TransactionCategory.SALARY, transaction.getCategory());
        assertEquals(1, transactionService.count());
    }

    @Test
    void createTransactionWithNullId() {
        assertThrows(TransactionInvalidException.class, ()->transactionService.createTransaction(null, BigDecimal.valueOf(-100.0), TransactionType.INCOME, TransactionCategory.SALARY));
    }

    @Test
    void createTransactionWithDupId() {
        transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY);
        assertThrows(TransactionDupException.class, ()->transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY));
    }

    @Test
    void createTransactionWithInvalid() {
        assertThrows(TransactionInvalidException.class, ()->transactionService.createTransaction(1L, BigDecimal.valueOf(0), TransactionType.INCOME, TransactionCategory.SALARY));
        assertThrows(TransactionInvalidException.class, ()->transactionService.createTransaction(1L, BigDecimal.valueOf(-100.0), TransactionType.INCOME, TransactionCategory.SALARY));
        assertThrows(TransactionInvalidException.class, ()->transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SHOPPING));
        assertThrows(TransactionInvalidException.class, ()->transactionService.createTransaction(1L, BigDecimal.valueOf(-100.0), TransactionType.PAYMENT, TransactionCategory.SALARY));
        assertThrows(TransactionInvalidException.class, ()->transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.PAYMENT, TransactionCategory.SHOPPING));
    }

    @Test
    void deleteTransaction() {
        assertThrows(TransactionNotExistsException.class, ()->transactionService.deleteTransaction(1L));
        transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY);
        transactionService.deleteTransaction(1L);
        assertEquals(0, transactionService.count());
    }

    @Test
    void updateTransaction() {
        assertThrows(TransactionNotExistsException.class, ()->transactionService.updateTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY));
        transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY);
        Transaction transaction = transactionService.updateTransaction(1L, BigDecimal.valueOf(-100.0), TransactionType.PAYMENT, TransactionCategory.SHOPPING);
        assertEquals(1L, transaction.getId());
        assertEquals(BigDecimal.valueOf(-100.0), transaction.getAmount());
        assertEquals(TransactionType.PAYMENT, transaction.getType());
        assertEquals(TransactionCategory.SHOPPING, transaction.getCategory());
    }

    @Test
    void updateTransactionWithInvalid() {
        transactionService.createTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY);
        assertThrows(TransactionInvalidException.class, ()->transactionService.updateTransaction(1L, BigDecimal.valueOf(-100.0), TransactionType.INCOME, TransactionCategory.SALARY));
        assertThrows(TransactionInvalidException.class, ()->transactionService.updateTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SHOPPING));
        assertThrows(TransactionInvalidException.class, ()->transactionService.updateTransaction(1L, BigDecimal.valueOf(-100.0), TransactionType.PAYMENT, TransactionCategory.SALARY));
        assertThrows(TransactionInvalidException.class, ()->transactionService.updateTransaction(1L, BigDecimal.valueOf(100.0), TransactionType.PAYMENT, TransactionCategory.SHOPPING));
    }

    @Test
    void listAllTransactions() {
        for (long id = 1; id <= 100; id++) {
            transactionService.createTransaction(id, BigDecimal.valueOf(100.0), TransactionType.INCOME, TransactionCategory.SALARY);
        }
        assertEquals(100, transactionService.count());
        List<Transaction> transactions = transactionService.listAllTransactions(0, 20);
        assertEquals(20, transactions.size());
        transactions = transactionService.listAllTransactions(1, 20);
        assertEquals(20, transactions.size());
        transactions = transactionService.listAllTransactions(2, 20);
        assertEquals(20, transactions.size());
        transactions = transactionService.listAllTransactions(3, 20);
        assertEquals(20, transactions.size());
        transactions = transactionService.listAllTransactions(4, 20);
        assertEquals(20, transactions.size());
        transactions = transactionService.listAllTransactions(5, 20);
        assertEquals(0, transactions.size());
        transactions = transactionService.listAllTransactions(0, 200);
        assertEquals(100, transactions.size());
    }
}