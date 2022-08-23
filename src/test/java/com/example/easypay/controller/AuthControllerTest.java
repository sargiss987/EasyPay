package com.example.easypay.controller;

import static com.example.easypay.constants.AuthControllerConstants.EMAIL_ALREADY_EXIST_EXCEPTION_MESSAGE_KEY;
import static com.example.easypay.constants.AuthControllerConstants.LOGIN_FORM;
import static com.example.easypay.constants.AuthControllerConstants.REGISTER_FORM;
import static com.example.easypay.constants.AuthControllerConstants.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE_KEY;
import static com.example.easypay.constants.AuthControllerConstants.USER_ACCOUNT;
import static com.example.easypay.constants.AuthControllerConstants.USER_DATA_KEY;
import static com.example.easypay.constants.AuthControllerConstants.USER_PASS_INCORRECT_EXCEPTION_MESSAGE_KEY;
import static com.example.easypay.constants.AuthControllerConstants.VALIDATION_ERROR_MESSAGE_KEY;
import static com.example.easypay.constants.AuthServiceConstants.USER_EMAIL_EXIST_EXCEPTION_MESSAGE;
import static com.example.easypay.constants.AuthServiceConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.example.easypay.constants.UserAuthenticationManagerConstants.INCORRECT_PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.easypay.exception.UserEmailAlreadyExistException;
import com.example.easypay.exception.UserPasswordIncorrectException;
import com.example.easypay.model.entity.User;
import com.example.easypay.model.validation.UserRegistrationValidationErrors;
import com.example.easypay.service.AuthService;
import com.example.easypay.service.UserValidationService;
import com.example.easypay.util.UserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean AuthService authService;

  @MockBean
  UserValidationService validatorService;

  @Test
  void register_withCorrectCredentials_returnUserAccountWithRegisteredUser() throws Exception {
    User bankUser = UserGenerator.generateBankUser();
    when(validatorService.validateUserRegistrationDto(any()))
        .thenReturn(new UserRegistrationValidationErrors());
    when(authService.register(any())).thenReturn(bankUser);

    mockMvc
        .perform(post("/api/v1/auth/register").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().isCreated())
        .andExpect(view().name(USER_ACCOUNT))
        .andExpect(model().attribute(USER_DATA_KEY, bankUser));
  }

  @Test
  void register_withExistingEmail_returnResisterFormWithUserEmailAlreadyExistExceptionMessage()
      throws Exception {
    String email = "TestEmail";
    String exceptionMessage = String.format(USER_EMAIL_EXIST_EXCEPTION_MESSAGE, email);
    when(validatorService.validateUserRegistrationDto(any()))
        .thenReturn(new UserRegistrationValidationErrors());
    when(authService.register(any()))
        .thenThrow(new UserEmailAlreadyExistException(exceptionMessage));

    mockMvc
        .perform(post("/api/v1/auth/register").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().isBadRequest())
        .andExpect(view().name(REGISTER_FORM))
        .andExpect(model().attribute(EMAIL_ALREADY_EXIST_EXCEPTION_MESSAGE_KEY, exceptionMessage));
  }

  @Test
  void register_withIncorrectCredentials_returnResisterFormWithValidationErrors() throws Exception {
    UserRegistrationValidationErrors validationErrors = new UserRegistrationValidationErrors();
    validationErrors.setHasErrors(true);
    when(validatorService.validateUserRegistrationDto(any())).thenReturn(validationErrors);

    mockMvc
        .perform(post("/api/v1/auth/register").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().isBadRequest())
        .andExpect(view().name(REGISTER_FORM))
        .andExpect(model().attribute(VALIDATION_ERROR_MESSAGE_KEY, validationErrors));
  }

  @Test
  void register_unauthorizedRequest_isForbidden() throws Exception {
    mockMvc
        .perform(post("/api/v1/auth/register").contentType(MediaType.TEXT_HTML))
        .andExpect(status().isForbidden());
  }

  @Test
  void login_withCorrectCredentials_returnUserAccountTemplate() throws Exception {
    User bankUser = UserGenerator.generateBankUser();
    doNothing().when(authService).authenticateLoggedUser(any());
    when(authService.getLoggedUserByEmail(any())).thenReturn(bankUser);

    mockMvc
        .perform(post("/api/v1/auth/login").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(view().name(USER_ACCOUNT))
        .andExpect(model().attribute(USER_DATA_KEY, bankUser));
  }

  @Test
  void login_withProblemOfGettingLoggedUserData_throwsIllegalArgumentException() throws Exception {
    doNothing().when(authService).authenticateLoggedUser(any());
    when(authService.getLoggedUserByEmail(any())).thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(post("/api/v1/auth/login").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void login_withIncorrectEmail_isBadRequest() throws Exception {
    String email = "TestEmail";
    String exceptionMessage = String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, email);
    doThrow(new UsernameNotFoundException(exceptionMessage))
        .when(authService)
        .authenticateLoggedUser(any());

    mockMvc
        .perform(post("/api/v1/auth/login").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().isBadRequest())
        .andExpect(view().name(LOGIN_FORM))
        .andExpect(model().attribute(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE_KEY, exceptionMessage));
  }

  @Test
  void login_withIncorrectPassword_isBadRequest() throws Exception {
    doThrow(new UserPasswordIncorrectException(INCORRECT_PASSWORD))
        .when(authService)
        .authenticateLoggedUser(any());

    mockMvc
        .perform(post("/api/v1/auth/login").with(csrf()).contentType(MediaType.TEXT_HTML))
        .andExpect(status().isBadRequest())
        .andExpect(view().name(LOGIN_FORM))
        .andExpect(
            model().attribute(USER_PASS_INCORRECT_EXCEPTION_MESSAGE_KEY, INCORRECT_PASSWORD));
  }

  @Test
  void login_unauthorizedRequest_isForbidden() throws Exception {
    mockMvc
        .perform(post("/api/v1/auth/login").contentType(MediaType.TEXT_HTML))
        .andExpect(status().isForbidden());
  }
}
