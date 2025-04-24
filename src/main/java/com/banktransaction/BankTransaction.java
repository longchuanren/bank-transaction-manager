package com.banktransaction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BankTransaction {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BankTransaction.class).run(args);
    }
}