package com.example.easypay.service;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;

public interface BankService {

    User createAccount(BankAccountDto bankAccountDto);

    BankAccount getAccountById(Long id);
}
