package com.example.easypay.controller;

import static com.example.easypay.constants.AuthControllerConstants.USER_ACCOUNT;
import static com.example.easypay.constants.AuthControllerConstants.USER_DATA_KEY;
import static com.example.easypay.constants.AuthControllerConstants.VALIDATION_ERROR_MESSAGE_KEY;
import static com.example.easypay.constants.BankControllerConstants.ACCOUNT_FORM;
import static com.example.easypay.constants.BankControllerConstants.BANK_ACCOUNT;
import static com.example.easypay.constants.BankControllerConstants.BANK_ACCOUNT_DATA_KEY;
import static com.example.easypay.constants.HomeControllerConstants.USER_DTO_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;
import com.example.easypay.model.validation.BankAccountValidationErrors;
import com.example.easypay.service.BankService;
import com.example.easypay.service.UserValidationService;
import com.example.easypay.util.BankAccountGenerator;
import com.example.easypay.util.BankUserDetailsGenerator;
import com.example.easypay.util.UserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean BankService bankService;

  @MockBean
  UserValidationService validatorService;

  @Test
  void createAccount_returnAccountFormTemplate() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/bank/account")
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(view().name(ACCOUNT_FORM))
        .andExpect(model().attributeExists(USER_DTO_KEY));
  }

  @Test
  void createAccount_withCorrectCredentials_returnUserAccountWithUser() throws Exception {
    BankAccountValidationErrors validationErrors = new BankAccountValidationErrors();
    User user = UserGenerator.generateBankUserWithBankAccount();
    when(validatorService.validateBankAccountDto(any())).thenReturn(validationErrors);
    when(bankService.createAccount(any())).thenReturn(user);

    mockMvc
        .perform(
            post("/api/v1/bank/account")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isCreated())
        .andExpect(view().name(USER_ACCOUNT))
        .andExpect(model().attribute(USER_DATA_KEY, user));
  }

  @Test
  void createAccount_withIncorrectCredentials_returnAccountFormWithValidationErrors()
      throws Exception {
    BankAccountValidationErrors validationErrors = new BankAccountValidationErrors();
    validationErrors.setHasErrors(true);
    when(validatorService.validateBankAccountDto(any())).thenReturn(validationErrors);

    mockMvc
        .perform(
            post("/api/v1/bank/account")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isBadRequest())
        .andExpect(view().name(ACCOUNT_FORM))
        .andExpect(model().attribute(VALIDATION_ERROR_MESSAGE_KEY, validationErrors));
  }

  @Test
  void createAccount_unauthorizedRequest_isForbidden() throws Exception {
    mockMvc
        .perform(post("/api/v1/bank/account").contentType(MediaType.TEXT_HTML))
        .andExpect(status().isForbidden());
  }

  @Test
  void accountPage_withCorrectAccountId_returnBankAccount() throws Exception {
    BankAccount bankAccount =
        BankAccountGenerator.generateBankAccountWithUser(UserGenerator.generateBankUser());

    when(bankService.getAccountById(any())).thenReturn(bankAccount);

    mockMvc
        .perform(
            get("/api/v1/bank/account/page/1")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(view().name(BANK_ACCOUNT))
        .andExpect(model().attribute(BANK_ACCOUNT_DATA_KEY, bankAccount));
  }

  @Test
  void accountPage_withIncorrectAccountId_throwsIllegalArgumentException() throws Exception {
    when(bankService.getAccountById(any())).thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(
            get("/api/v1/bank/account/page/1")
                .with(csrf())
                .with(user(BankUserDetailsGenerator.generateBankUserDetails()))
                .contentType(MediaType.TEXT_HTML))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void accountPage_unauthorizedRequest_isForbidden() throws Exception {
    mockMvc
        .perform(get("/api/v1/bank/account/page/1").contentType(MediaType.TEXT_HTML))
        .andExpect(status().is3xxRedirection());
  }
}
