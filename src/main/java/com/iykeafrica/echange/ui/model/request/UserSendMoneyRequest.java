package com.iykeafrica.echange.ui.model.request;

public class UserSendMoneyRequest {
    private String walletId;
    private double lastSentReceivedAmount;
    private String transactionPin;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

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
}
