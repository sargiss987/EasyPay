package com.example.easypay.service.impl;

import static com.example.easypay.constants.AuthServiceConstants.USER_EMAIL_EXIST_EXCEPTION_MESSAGE;

import com.example.easypay.exception.UserEmailAlreadyExistException;
import com.example.easypay.model.dto.UserLoginDto;
import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.entity.User;
import com.example.easypay.model.security.BankUserDetails;
import com.example.easypay.repository.UserRepository;
import com.example.easypay.security.UserAuthenticationManager;
import com.example.easypay.service.AuthService;
import com.example.easypay.utils.mapper.UserMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final UserAuthenticationManager authenticationManager;

  public AuthServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      UserAuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public User register(UserRegistrationDto userRegistrationDto) {
    userRepository
        .findByEmail(userRegistrationDto.getEmail())
        .ifPresent(
            user -> {
              throw new UserEmailAlreadyExistException(
                  String.format(USER_EMAIL_EXIST_EXCEPTION_MESSAGE, user.getEmail()));
            });
    User user = UserMapper.ToUser(userRegistrationDto, passwordEncoder);
    authenticateRegisteredUser(user);
    return userRepository.save(user);
  }

  @Override
  public void authenticateLoggedUser(UserLoginDto userLoginDto) {
    SecurityContextHolder.getContext()
        .setAuthentication(
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userLoginDto.getEmail(), userLoginDto.getPassword())));
  }

  @Override
  public User getLoggedUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
  }

  private void authenticateRegisteredUser(User user) {
    BankUserDetails principal = new BankUserDetails(user);
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                principal.getUsername(), principal.getPassword(), principal.getAuthorities()));
  }
}
