package com.dummy.nevmo.service;

import com.dummy.nevmo.entity.UserAccount;

import java.math.BigDecimal;

public interface BankService {
    BigDecimal getBalance(String accountNumber);
    boolean deposit(String accountNumber, BigDecimal amount);
    boolean withdraw(BigDecimal amount, UserAccount account);

}
