package com.dummy.nevmo.service;

import com.dummy.nevmo.entity.UserAccount;
import com.dummy.nevmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferServiceImpl implements TransferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BankServiceImpl bankService;

    @Override
    public boolean transfer(UserAccount sender, UserAccount recipient, BigDecimal amount) {
        if(sender.getBalance().compareTo(amount) < 0) {
            if(bankService.withdraw(amount, sender)) {
                recipient.setBalance(recipient.getBalance().add(amount));
                userRepository.save(recipient);
                return true;
            }
            return false;
        } else {
            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount));
            userRepository.save(sender);
            userRepository.save(recipient);
            return true;
        }
    }
}
