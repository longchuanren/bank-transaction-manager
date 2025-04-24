package com.banktransaction.controller;

import com.banktransaction.entity.Transaction;
import com.banktransaction.enums.TransactionCategory;
import com.banktransaction.enums.TransactionType;
import com.banktransaction.exceptions.TransactionException;
import com.banktransaction.response.TransactionResponse;
import com.banktransaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestParam Long id, @RequestParam(required = true) BigDecimal amount, @RequestParam(required = true) TransactionType type, @RequestParam(required = true) TransactionCategory category) {
        TransactionResponse response = new TransactionResponse();
        Transaction transaction = null;
        try {
            transaction = transactionService.createTransaction(id, amount, type, category);
        } catch (TransactionException e) {
            response.setMessage(e.toString());
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        response.setTransaction(transaction);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionResponse> deleteTransaction(@PathVariable Long id) {
        TransactionResponse response = new TransactionResponse();
        try {
            transactionService.deleteTransaction(id);
        } catch (TransactionException e) {
            response.setMessage(e.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id, @RequestParam(required = false) BigDecimal amount, @RequestParam(required = false) TransactionType type, @RequestParam(required = false) TransactionCategory category) {
        TransactionResponse response = new TransactionResponse();
        Transaction transaction = null;
        try {
            transaction = transactionService.updateTransaction(id, amount, type, category);
        } catch (TransactionException e) {
            response.setMessage(e.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (transaction != null) {
            response.setTransaction(transaction);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> listAllTransactions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<Transaction> transactions = transactionService.listAllTransactions(page, size);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}