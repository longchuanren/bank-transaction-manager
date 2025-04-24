package com.banktransaction.exceptions;


import com.banktransaction.enums.ErrorCode;

import java.util.List;

public class TransactionException extends RuntimeException {
    private final ErrorCode errorCode;
    private final List<String> additionMsg;

    public TransactionException(ErrorCode errorCode, List<String> additionMsg) {
        this.errorCode = errorCode;
        this.additionMsg = additionMsg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public List<String> getAdditionMsg() {
        return additionMsg;
    }

    @Override
    public String toString() {
        return errorCode.getDescription() + " " + additionMsg;
    }
}
