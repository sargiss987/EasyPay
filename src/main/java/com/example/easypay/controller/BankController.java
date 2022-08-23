package com.example.easypay.controller;

import static com.example.easypay.constants.AuthControllerConstants.USER_ACCOUNT;
import static com.example.easypay.constants.AuthControllerConstants.USER_DATA_KEY;
import static com.example.easypay.constants.AuthControllerConstants.VALIDATION_ERROR_MESSAGE_KEY;
import static com.example.easypay.constants.BankControllerConstants.ACCOUNT_FORM;
import static com.example.easypay.constants.BankControllerConstants.BANK_ACCOUNT;
import static com.example.easypay.constants.BankControllerConstants.BANK_ACCOUNT_DATA_KEY;
import static com.example.easypay.constants.HomeControllerConstants.USER_DTO_KEY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.example.easypay.constants.AuthControllerConstants;
import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;
import com.example.easypay.model.validation.BankAccountValidationErrors;
import com.example.easypay.service.BankService;
import com.example.easypay.service.UserValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v1/bank")
public class BankController {

  private final BankService bankService;
  private final UserValidationService validatorService;

  public BankController(BankService bankService, UserValidationService validatorService) {
    this.bankService = bankService;
    this.validatorService = validatorService;
  }

  @GetMapping("/account")
  public ModelAndView createAccount(Model model) {
    model.addAttribute(USER_DTO_KEY, BankAccountDto.getInstance());
    return new ModelAndView(ACCOUNT_FORM);
  }

  @PostMapping("/account")
  public ModelAndView createAccount(@ModelAttribute BankAccountDto bankAccountDto, Model model) {
    BankAccountValidationErrors validationErrors =
        validatorService.validateBankAccountDto(bankAccountDto);
    if (validationErrors.hasErrors()) {
      model.addAttribute(AuthControllerConstants.USER_DTO_KEY, BankAccountDto.getInstance());
      model.addAttribute(VALIDATION_ERROR_MESSAGE_KEY, validationErrors);
      return new ModelAndView(ACCOUNT_FORM, BAD_REQUEST);
    }
    User user = bankService.createAccount(bankAccountDto);
    model.addAttribute(USER_DATA_KEY, user);
    return new ModelAndView(USER_ACCOUNT, CREATED);
  }

  @GetMapping("/account/page/{accountId}")
  public ModelAndView accountPage(@PathVariable Long accountId, Model model) {
    BankAccount bankAccount = bankService.getAccountById(accountId);
    model.addAttribute(BANK_ACCOUNT_DATA_KEY, bankAccount);
    return new ModelAndView(BANK_ACCOUNT);
  }
}
