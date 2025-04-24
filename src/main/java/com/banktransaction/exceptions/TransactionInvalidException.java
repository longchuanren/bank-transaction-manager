package com.banktransaction.exceptions;


import com.banktransaction.enums.ErrorCode;

import java.util.List;

public class TransactionInvalidException extends TransactionException {
    public TransactionInvalidException(List<String> additionMsg) {
        super(ErrorCode.TRANSACTION_INVALID, additionMsg);
    }
}
