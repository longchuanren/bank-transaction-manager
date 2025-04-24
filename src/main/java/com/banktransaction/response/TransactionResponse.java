package com.banktransaction.response;

import com.banktransaction.entity.Transaction;

public class TransactionResponse {
    private String message = "success";
    private Transaction transaction;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
