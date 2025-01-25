package com.dummy.nevmo.service;

import com.dummy.nevmo.entity.Transactions;

import java.util.List;

public interface TransactionService {
    List<Transactions> getAllTransactions(String user);
}
