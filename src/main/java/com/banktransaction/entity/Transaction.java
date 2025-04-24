package com.banktransaction.entity;

import com.banktransaction.enums.TransactionCategory;
import com.banktransaction.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionCategory category;
    private LocalDateTime timestamp;

    public Transaction(Long id, BigDecimal amount, TransactionType type, TransactionCategory category, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionCategory getCategory() { return category; }

    public void setCategory(TransactionCategory category) { this.category = category; }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}