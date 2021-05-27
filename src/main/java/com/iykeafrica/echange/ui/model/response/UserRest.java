package com.iykeafrica.echange.ui.model.response;

import java.util.List;

public class UserRest {
    private String walletId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private double walletBalance;
    private List<TransactionRest> transactions;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

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

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public List<TransactionRest> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRest> transaction) {
        this.transactions = transaction;
    }
}
