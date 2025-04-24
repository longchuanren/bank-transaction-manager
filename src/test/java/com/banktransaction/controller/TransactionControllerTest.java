package com.banktransaction.controller;

import com.banktransaction.entity.Transaction;
import com.banktransaction.enums.TransactionCategory;
import com.banktransaction.enums.TransactionType;
import com.banktransaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() throws Exception {
        BigDecimal amount = BigDecimal.valueOf(100.0);
        Transaction transaction = new Transaction(1L, amount, TransactionType.INCOME, TransactionCategory.SALARY, LocalDateTime.now());

        when(transactionService.createTransaction(1L, amount, TransactionType.INCOME, TransactionCategory.SALARY)).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .param("id", "1")
                        .param("amount", amount.toString())
                        .param("type", TransactionType.INCOME.toString())
                        .param("category", TransactionCategory.SALARY.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction.id").value(transaction.getId()))
                .andExpect(jsonPath("$.transaction.amount").value(transaction.getAmount().doubleValue()))
                .andExpect(jsonPath("$.transaction.type").value(transaction.getType().toString()))
                .andExpect(jsonPath("$.transaction.category").value(transaction.getCategory().toString()));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Long id = 1L;

        doNothing().when(transactionService).deleteTransaction(id);

        mockMvc.perform(delete("/api/transactions/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Long id = 1L;
        BigDecimal amount = BigDecimal.valueOf(200.0);
        Transaction transaction = new Transaction(id, amount, TransactionType.INCOME, TransactionCategory.SALARY, LocalDateTime.now());

        when(transactionService.updateTransaction(id, amount, TransactionType.INCOME, TransactionCategory.SALARY)).thenReturn(transaction);

        mockMvc.perform(put("/api/transactions/{id}", id)
                        .param("amount", amount.toString())
                        .param("type", TransactionType.INCOME.toString())
                        .param("category", TransactionCategory.SALARY.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction.id").value(transaction.getId()))
                .andExpect(jsonPath("$.transaction.amount").value(transaction.getAmount().doubleValue()))
                .andExpect(jsonPath("$.transaction.type").value(transaction.getType().toString()))
                .andExpect(jsonPath("$.transaction.category").value(transaction.getCategory().toString()));
    }

    @Test
    public void testListAllTransactions() throws Exception {
        BigDecimal amount1 = BigDecimal.valueOf(100.0);
        Transaction transaction1 = new Transaction(1L, amount1, TransactionType.INCOME, TransactionCategory.SALARY, LocalDateTime.now());
        BigDecimal amount2 = BigDecimal.valueOf(200.0);
        Transaction transaction2 = new Transaction(2L, amount2, TransactionType.INCOME, TransactionCategory.BONUS, LocalDateTime.now());
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.listAllTransactions(0, 5)).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(transaction1.getId()))
                .andExpect(jsonPath("$[0].amount").value(transaction1.getAmount().doubleValue()))
                .andExpect(jsonPath("$[0].type").value(transaction1.getType().toString()))
                .andExpect(jsonPath("$[0].category").value(transaction1.getCategory().toString()))
                .andExpect(jsonPath("$[1].id").value(transaction2.getId()))
                .andExpect(jsonPath("$[1].amount").value(transaction2.getAmount().doubleValue()))
                .andExpect(jsonPath("$[1].type").value(transaction2.getType().toString()))
                .andExpect(jsonPath("$[1].category").value(transaction2.getCategory().toString()));
    }
}