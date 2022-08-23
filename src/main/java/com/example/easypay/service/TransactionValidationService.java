package com.example.easypay.service;

import com.example.easypay.model.dto.TransactionDto;

public interface TransactionValidationService {

    boolean validateTransaction(TransactionDto transactionDto);
}
