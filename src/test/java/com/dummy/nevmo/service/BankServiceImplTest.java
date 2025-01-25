package com.dummy.nevmo.service;


import com.dummy.nevmo.entity.BankAccount;
import com.dummy.nevmo.entity.UserAccount;
import com.dummy.nevmo.exceptions.CustomException;
import com.dummy.nevmo.repository.BankRepository;
import com.dummy.nevmo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankServiceImpl bankService;

    @BeforeEach
    public void _init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBalanceShouldReturnValue() {
        BankAccount bank = createBankAccount();
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));

        assertEquals(new BigDecimal(100), bankService.getBalance("123"));
    }

    @Test
    public void testGetBalanceShouldReturnNull() {
        when(bankRepository.findById(any())).thenReturn(Optional.empty());

        assertNull(bankService.getBalance("123"));
    }

    @Test
    public void testDepositShouldReturnTrue() {
        BankAccount bank = createBankAccount();
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));

        assertTrue(bankService.deposit("123", new BigDecimal(100)));
        assertEquals(new BigDecimal(200), bank.getBalance());
    }

    @Test
    public void testDepositShouldReturnFalse() {
        when(bankRepository.findById(any())).thenReturn(Optional.empty());

        assertFalse(bankService.deposit("123", new BigDecimal(100)));
        assertNull(bankService.getBalance("123"));
    }

    @Test
    public void testWithdrawShouldReturnTrue() throws Exception {
        UserAccount user = new UserAccount();
        user.setBalance(new BigDecimal(50));
        user.setBankAccounts(List.of(createBankAccount()));

        assertTrue(bankService.withdraw(new BigDecimal(50), user));
        assertEquals(new BigDecimal(100), user.getBalance());
    }

    @Test
    public void testWithdrawShouldReturnFalse() throws Exception {
        UserAccount user = new UserAccount();
        user.setBalance(new BigDecimal(50));
        user.setBankAccounts(List.of(createBankAccount()));

        assertFalse(bankService.withdraw(new BigDecimal(200), user));
        assertEquals(new BigDecimal(50), user.getBalance());
    }

    private BankAccount createBankAccount() {
        BankAccount bank = new BankAccount();
        bank.setAccountNumber("123");
        bank.setBalance(new BigDecimal(100));
        return bank;
    }

}
