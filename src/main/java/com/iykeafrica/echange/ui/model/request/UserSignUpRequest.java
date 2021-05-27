package com.iykeafrica.echange.ui.model.request;

import java.util.List;

public class UserSignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String fcmMessageToken;
    private String password;
    private String transactionPin;
    private List<TransactionRequestModel> transactions;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFcmMessageToken() {
        return fcmMessageToken;
    }

    public void setFcmMessageToken(String fcmMessageToken) {
        this.fcmMessageToken = fcmMessageToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }

    public List<TransactionRequestModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRequestModel> transactions) {
        this.transactions = transactions;
    }
}
