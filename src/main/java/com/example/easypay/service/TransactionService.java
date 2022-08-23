package com.example.easypay.service;

import com.example.easypay.model.dto.TransactionDto;
import java.math.BigDecimal;

public interface TransactionService {

    BigDecimal deposit(TransactionDto transactionDto);

    BigDecimal withdraw(TransactionDto transactionDto);
}
