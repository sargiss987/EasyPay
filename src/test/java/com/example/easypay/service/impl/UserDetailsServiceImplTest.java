package com.example.easypay.service.impl;

import static com.example.easypay.constants.AuthServiceConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.easypay.model.entity.User;
import com.example.easypay.model.security.BankUserDetails;
import com.example.easypay.repository.UserRepository;
import com.example.easypay.util.UserGenerator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock UserRepository userRepository;

  private UserDetailsServiceImpl userDetailsService;

  @BeforeEach
  void setUp() {
    userDetailsService = new UserDetailsServiceImpl(userRepository);
  }

  @Test
  void loadUserByUsername_withCorrectUsername_returnUserPrincipal() {
    String email = "TestEmail";
    User bankUser = UserGenerator.generateBankUser(email);
    BankUserDetails userPrincipal = new BankUserDetails(bankUser);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(bankUser));

    assertEquals(
        userDetailsService.loadUserByUsername(email).getUsername(), userPrincipal.getUsername());
    assertEquals(
        userDetailsService.loadUserByUsername(email).getPassword(), userPrincipal.getPassword());
    assertEquals(
        userDetailsService.loadUserByUsername(email).getAuthorities(),
        userPrincipal.getAuthorities());
  }

  @Test
  void loadUserByUsername_withIncorrectUsername_throwUsernameNotFoundException() {
    String email = "TestEmail";
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userDetailsService.loadUserByUsername(email))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, email));
  }
}
