package com.banktransaction.enums;

public enum TransactionCategory {
    SALARY(0, "工资"),
    BONUS(1, "奖金"),
    SHOPPING(3, "购物");

    private final int type;
    private final String description;

    TransactionCategory(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIncome() {
        return this == SALARY || this == BONUS;
    }

    public boolean isPayment() {
        return this == SHOPPING;
    }
}
