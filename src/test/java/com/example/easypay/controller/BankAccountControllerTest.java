package com.example.easypay.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.easypay.exception.TransactionFailedException;
import com.example.easypay.service.TransactionService;
import com.example.easypay.util.BankUserDetailsGenerator;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean TransactionService transactionService;

  @Test
  void deposit_withCorrectTransactionDto_returnUpdatedBalance() throws Exception {
    BigDecimal balance = BigDecimal.valueOf(123);
    when(transactionService.deposit(any())).thenReturn(balance);

    String requestBody = "{ \"email\": \"Test\",\"accountId\": \"1\"," + "\"amount\": \"20\" }";
    mockMvc
        .perform(
            post("/api/v1/account/deposit")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void deposit_withInCorrectTransactionDto_isBadRequest() throws Exception {
    when(transactionService.deposit(any())).thenThrow(TransactionFailedException.class);

    String requestBody = "{ \"email\": \"Test\",\"accountId\": \"1\"," + "\"amount\": \"20\" }";
    mockMvc
        .perform(
            post("/api/v1/account/deposit")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deposit_unauthorizedRequest_isForbidden() throws Exception {
    String requestBody = "{ \"email\": \"Test\",\"accountId\": \"1\"," + "\"amount\": \"20\" }";
    mockMvc
        .perform(
            post("/api/v1/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isForbidden());
  }

  @Test
  void withdraw_withCorrectTransactionDto_returnUpdatedBalance() throws Exception {
    BigDecimal balance = BigDecimal.valueOf(123);
    when(transactionService.withdraw(any())).thenReturn(balance);

    String requestBody = "{ \"email\": \"Test\",\"accountId\": \"1\"," + "\"amount\": \"20\" }";
    mockMvc
        .perform(
            post("/api/v1/account/withdraw")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void withdraw_withInCorrectTransactionDto_isBadRequest() throws Exception {
    when(transactionService.withdraw(any())).thenThrow(TransactionFailedException.class);

    String requestBody = "{ \"email\": \"Test\",\"accountId\": \"1\"," + "\"amount\": \"20\" }";
    mockMvc
        .perform(
            post("/api/v1/account/withdraw")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void withdraw_unauthorizedRequest_isForbidden() throws Exception {
    String requestBody = "{ \"email\": \"Test\",\"accountId\": \"1\"," + "\"amount\": \"20\" }";
    mockMvc
        .perform(
            post("/api/v1/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isForbidden());
  }
}
