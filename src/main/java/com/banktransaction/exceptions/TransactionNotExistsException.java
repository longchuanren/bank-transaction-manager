package com.banktransaction.exceptions;


import com.banktransaction.enums.ErrorCode;

import java.util.List;

public class TransactionNotExistsException extends TransactionException {
    public TransactionNotExistsException(List<String> additionMsg) {
        super(ErrorCode.TRANSACTION_NOT_EXIST, additionMsg);
    }
}
