package com.dummy.nevmo.controllerV1;

import com.dummy.nevmo.entity.AccountStatus;
import com.dummy.nevmo.entity.AccountType;
import com.dummy.nevmo.entity.BankAccount;
import com.dummy.nevmo.entity.UserAccount;
import com.dummy.nevmo.repository.BankRepository;
import com.dummy.nevmo.repository.UserRepository;
import com.dummy.nevmo.service.BankServiceImpl;
import com.dummy.nevmo.service.TransactionServiceImpl;
import com.dummy.nevmo.service.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerV1Test {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private TransferServiceImpl transferService;

    @Mock
    private BankServiceImpl bankService;

    @Mock
    private TransactionServiceImpl transactionService;

    @InjectMocks
    private ControllerV1 controller;

    @BeforeEach
    public void _init_() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfileShouldReturnUser() {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(createUserAccount()));
        ResponseEntity<UserAccount> response =  controller.getProfile("123");

        assertEquals("123", response.getBody().getUserId());
    }

    @Test
    public void testGetProfileShouldReturnBadRequest() {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<UserAccount> response =  controller.getProfile("123");

        assertNull(response.getBody());
    }

    @Test
    public void testAddUserShouldReturnUser() {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(null));
        when(userRepository.save(any())).thenReturn(createUserAccount());
        ResponseEntity<UserAccount> response =  controller.addUser("123", createUserAccount());

        assertEquals("123", response.getBody().getUserId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddUserShouldReturnConflict() {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(createUserAccount()));
        ResponseEntity<UserAccount> response =  controller.addUser("123", createUserAccount());

        assertNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    private UserAccount createUserAccount() {
        UserAccount user = new UserAccount();
        user.setUserId("123");
        user.setUserName("John Doe");
        user.setEmail("john.doe@aol.com");
        user.setPassword("password");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setBalance(new BigDecimal(100));
        user.setBankAccounts(List.of(createBankAccount()));
        return user;
    }

    private BankAccount createBankAccount() {
        BankAccount bank = new BankAccount();
        bank.setId(123L);
        bank.setBalance(new BigDecimal("100.50"));
        bank.setAccountType(String.valueOf(AccountType.CHECKING));
        bank.setBankName("Bank of America");
        bank.setAccountNumber("1234567890");
        bank.setRoutingNumber("123456789");
        return bank;
    }
}
