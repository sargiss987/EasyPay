package com.example.easypay.service.impl;

import static com.example.easypay.constants.AuthServiceConstants.USER_EMAIL_EXIST_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.easypay.model.entity.User;
import com.example.easypay.model.security.BankUserDetails;
import com.example.easypay.repository.UserRepository;
import com.example.easypay.security.UserAuthenticationManager;
import com.example.easypay.util.BankUserDetailsGenerator;
import com.example.easypay.util.UserGenerator;
import com.example.easypay.util.UserLoginDtoGenerator;
import com.example.easypay.util.UserRegistrationDtoGenerator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  @Mock UserRepository userRepository;
  @Mock BCryptPasswordEncoder passwordEncoder;
  @Mock UserAuthenticationManager authenticationManager;

  private AuthServiceImpl authService;

  @BeforeEach
  void setUp() {
    authService = new AuthServiceImpl(userRepository, passwordEncoder, authenticationManager);
  }

  @Test
  void register_withCorrectUsername_returnUser() {
    User savedUser = UserGenerator.generateBankUser();
    String email = "dummy";
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any())).thenReturn(savedUser);

    assertEquals(
        authService.register(UserRegistrationDtoGenerator.generateUserRegistrationDto()),
        savedUser);
    assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), email);
  }

  @Test
  void register_withIncorrectUsername_throwUserEmailAlreadyExistException() {
    String email = "TestEmail";
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(UserGenerator.generateBankUser(email)));

    assertThatThrownBy(
            () -> authService.register(UserRegistrationDtoGenerator.generateUserRegistrationDto()))
        .hasMessage(String.format(USER_EMAIL_EXIST_EXCEPTION_MESSAGE, email));
  }

  @Test
  void authenticateLoggedUser_returnUserCredentialsFromSecurityContextHolder() {
    String email = "dummy";
    when(authenticationManager.authenticate(any()))
        .thenReturn(authenticate(BankUserDetailsGenerator.generateBankUserDetails()));

    authService.authenticateLoggedUser(UserLoginDtoGenerator.generateUserLoginDto());

    assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), email);
  }

  @Test
  void getLoggedUserByEmail_withCorrectCredentials_returnBankUser() {
    User user = UserGenerator.generateBankUser();
    String email = "dummy";
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    assertEquals(authService.getLoggedUserByEmail(email), user);
  }

  @Test
  void getLoggedUserByEmail_withInCorrectCredentials_throwsIllegalArgumentException() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> authService.getLoggedUserByEmail(anyString()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private Authentication authenticate(BankUserDetails bankUserDetails) {
    return new UsernamePasswordAuthenticationToken(
        bankUserDetails.getUsername(),
        bankUserDetails.getPassword(),
        bankUserDetails.getAuthorities());
  }
}
