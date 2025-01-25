package com.dummy.nevmo.service;


import com.dummy.nevmo.entity.BankAccount;
import com.dummy.nevmo.entity.UserAccount;
import com.dummy.nevmo.repository.BankRepository;
import com.dummy.nevmo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankServiceImpl implements BankService{

    static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BigDecimal getBalance(String accountNumber) {
        BankAccount bank = bankRepository.findById(accountNumber).orElse(null);
        if(bank != null) {
            return bank.getBalance();
        }
        return null;
    }

    @Override
    public boolean deposit(String accountNumber, BigDecimal amount) {
        BankAccount bank = bankRepository.findById(accountNumber).orElse(null);
        if(bank != null) {
            bank.setBalance(bank.getBalance().add(amount));
            bankRepository.save(bank);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(BigDecimal amount, UserAccount account) {
        BankAccount bank = account.getBankAccounts().get(0);
        if (bank != null && bank.getBalance().compareTo(amount) >= 0) {
            bank.setBalance(bank.getBalance().subtract(amount));
            account.setBalance(account.getBalance().add(amount));
            bankRepository.save(bank);
            userRepository.save(account);
            return true;
        }
        return false;
    }
}
