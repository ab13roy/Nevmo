package com.dummy.nevmo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "BankAccount")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @JsonProperty("bankName")
    private String bankName;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("routingNumber")
    private String routingNumber;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("accountType")
    private String accountType;

    public BankAccount() {
        this.balance = new BigDecimal("0");
    }

    public BankAccount(UserAccount user, String bankName, String accountNumber, String routingNumber, BigDecimal balance, AccountType accountType) {
        this.userAccount = user;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.balance = balance;
        this.accountType = String.valueOf(accountType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference
    public UserAccount getUser() {
        return userAccount;
    }

    public void setUser(UserAccount user) {
        this.userAccount = user;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "userId='" + userAccount.getUserId() + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + "*".repeat(accountNumber.length()) + '\'' +
                ", routingNumber='" + "*".repeat(routingNumber.length()) + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}

