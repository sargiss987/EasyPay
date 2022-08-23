package com.example.easypay.util;

import com.example.easypay.model.security.BankUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class BankUserAuthenticator {

  private BankUserAuthenticator() {}

  public static void setAuthentication() {
    SecurityContextHolder.getContext()
            .setAuthentication(authenticate(BankUserDetailsGenerator.generateBankUserDetails()));
  }

  private static Authentication authenticate(BankUserDetails bankUserDetails) {
    return new UsernamePasswordAuthenticationToken(
            bankUserDetails.getUsername(),
            bankUserDetails.getPassword(),
            bankUserDetails.getAuthorities());
  }
}
