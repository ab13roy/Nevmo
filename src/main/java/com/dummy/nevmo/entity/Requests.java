package com.dummy.nevmo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class Requests {

    @Id
    private String requestId;

    private String fromUserId;

    private String toUserId;

    private String requestType;

    private String requestDescription;

    private BigDecimal amount;

    private String timestamp;

    private String status;

    @ManyToOne
    @JoinColumn(name = "from_user_user_id")
    private UserAccount fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_user_id")
    private UserAccount toUser;

    public UserAccount getToUser() {
        return toUser;
    }

    public void setToUser(UserAccount toUser) {
        this.toUser = toUser;
    }

    public UserAccount getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserAccount fromUser) {
        this.fromUser = fromUser;
    }


    public Requests() {

    }

    public Requests(String requestId, String fromUserId, String toUserId, String requestType, String requestDescription, BigDecimal amount, String timestamp, RequestType status) {
        this.requestId = requestId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.requestType = requestType;
        this.requestDescription = requestDescription;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = String.valueOf(status);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Requests{" +
                "requestId='" + requestId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", requestType='" + requestType + '\'' +
                ", requestDescription='" + requestDescription + '\'' +
                ", amount=" + amount +
                ", timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
