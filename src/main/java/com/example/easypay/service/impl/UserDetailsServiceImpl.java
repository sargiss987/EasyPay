package com.example.easypay.service.impl;

import static com.example.easypay.constants.AuthServiceConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;

import com.example.easypay.model.security.BankUserDetails;
import com.example.easypay.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return new BankUserDetails(
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, email))));
  }
}
