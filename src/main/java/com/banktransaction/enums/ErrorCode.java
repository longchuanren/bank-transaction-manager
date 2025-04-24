package com.banktransaction.enums;

public enum ErrorCode {
    TRANSACTION_DUPLICATE(1000, "DUPLICATE"),
    TRANSACTION_NOT_EXIST(1001, "NOT EXIST"),
    TRANSACTION_INVALID(1002, "INVALID");

    private final int code;
    private final String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
