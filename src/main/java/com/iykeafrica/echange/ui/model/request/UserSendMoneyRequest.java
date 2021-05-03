package com.iykeafrica.echange.ui.model.request;

public class UserSendMoneyRequest {
    private String userWalletId;
    private String requesterWalletId;
    private double amount;
    private String transactionPin;

    public String getUserWalletId() {
        return userWalletId;
    }

    public void setUserWalletId(String userWalletId) {
        this.userWalletId = userWalletId;
    }

    public String getRequesterWalletId() {
        return requesterWalletId;
    }

    public void setRequesterWalletId(String requesterWalletId) {
        this.requesterWalletId = requesterWalletId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }
}
