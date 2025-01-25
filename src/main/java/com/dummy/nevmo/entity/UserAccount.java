package com.dummy.nevmo.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "UserAccount")
public class UserAccount {

    @Id
    private String userId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private char[] password;

    @JsonProperty("accountStatus")
    private String accountStatus;

    private BigDecimal balance;

    @JsonProperty("Accounts")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BankAccount> bankAccounts;

    public UserAccount() {
        this.balance = new BigDecimal(0);
    }

    public UserAccount(String userId, String userName, String email, String password, AccountStatus status) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password.toCharArray();
        this.accountStatus = String.valueOf(status);
        this.balance = new BigDecimal(0);
        this.bankAccounts = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Arrays.toString(password);
    }

    public void setPassword(String password) {
        this.password = password.toCharArray();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = String.valueOf(accountStatus);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public void addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
    }

    public void removeBankAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
    }

    @Override
    public String toString() {

        return "UserAccount{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*".repeat(password.length) + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", balance=" + balance +
                ", bankAccounts=" + bankAccounts.stream().map(BankAccount:: toString).collect(Collectors.joining(", ")) +
                '}';
    }
}