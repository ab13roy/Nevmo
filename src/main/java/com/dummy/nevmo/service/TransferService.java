package com.dummy.nevmo.service;

import com.dummy.nevmo.entity.UserAccount;

import java.math.BigDecimal;

public interface TransferService {
    boolean transfer(UserAccount sender, UserAccount recipient, BigDecimal amount);
}
