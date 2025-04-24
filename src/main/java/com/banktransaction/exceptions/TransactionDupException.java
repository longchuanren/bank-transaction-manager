package com.banktransaction.exceptions;


import com.banktransaction.enums.ErrorCode;

import java.util.List;

public class TransactionDupException extends TransactionException {
    public TransactionDupException(List<String> additionMsg) {
        super(ErrorCode.TRANSACTION_DUPLICATE, additionMsg);
    }
}
