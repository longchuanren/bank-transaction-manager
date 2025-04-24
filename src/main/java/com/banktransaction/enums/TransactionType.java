package com.banktransaction.enums;

public enum TransactionType {
    INCOME(0, "收入"),
    PAYMENT(1, "支出");

    private final int type;
    private final String description;

    TransactionType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
