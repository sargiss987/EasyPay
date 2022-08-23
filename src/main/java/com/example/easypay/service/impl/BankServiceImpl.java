package com.example.easypay.service.impl;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;
import com.example.easypay.repository.BankAccountRepository;
import com.example.easypay.repository.UserRepository;
import com.example.easypay.service.BankService;
import com.example.easypay.utils.mapper.BankAccountMapper;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

  private final UserRepository userRepository;
  private final BankAccountRepository bankAccountRepository;

  public BankServiceImpl(
      UserRepository userRepository, BankAccountRepository bankAccountRepository) {
    this.userRepository = userRepository;
    this.bankAccountRepository = bankAccountRepository;
  }

  @Override
  public User createAccount(BankAccountDto bankAccountDto) {
    String email = getUserEmailFromSecurityContext();
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.getAccounts().add(BankAccountMapper.toBankAccount(bankAccountDto, user));
      return userRepository.save(user);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public BankAccount getAccountById(Long id) {
    return bankAccountRepository
        .findById(id)
        .map(
            account -> {
              isAccountBelongsToCurrentUser(account);
              return account;
            })
        .orElseThrow(IllegalArgumentException::new);
  }

  private String getUserEmailFromSecurityContext() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  private void isAccountBelongsToCurrentUser(BankAccount bankAccount) {
    if (!bankAccount.getUser().getEmail().equals(getUserEmailFromSecurityContext())) {
      throw new IllegalArgumentException();
    }
  }
}
