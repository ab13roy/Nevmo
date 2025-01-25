package com.dummy.nevmo.service;

import com.dummy.nevmo.entity.Transactions;
import com.dummy.nevmo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<Transactions> getAllTransactions(String user) {
        return transactionRepository.findForUser(user);
    }
}
