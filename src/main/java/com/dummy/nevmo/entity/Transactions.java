package com.dummy.nevmo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "Transactions")
public class Transactions {

    @Id
    private String transactionId;

    private String timestamp;

    private BigDecimal amount;

    private String description;

    private String fromAccount;

    private String toAccount;

    private String method;

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Transactions() {

    }

    public Transactions(String transactionId, String timestamp, BigDecimal amount, String description, String fromAccount, String toAccount, String method) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.description = description;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.method = method;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionId='" + transactionId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", method='" + method + '\'' +
                '}';
    }
}
