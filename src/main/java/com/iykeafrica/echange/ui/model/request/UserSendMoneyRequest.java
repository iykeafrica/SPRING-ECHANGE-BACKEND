package com.iykeafrica.echange.ui.model.request;

import java.util.List;

public class UserSendMoneyRequest { //name is requester name

    private String name;
    private double lastSentReceivedAmount;
    private String transactionPin;
    private TransactionRequestModel transactions;

    public double getLastSentReceivedAmount() {
        return lastSentReceivedAmount;
    }

    public void setLastSentReceivedAmount(double lastSentReceivedAmount) {
        this.lastSentReceivedAmount = lastSentReceivedAmount;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }

    public TransactionRequestModel getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionRequestModel transactions) {
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
