package com.iykeafrica.echange.ui.model.response;

public class UserSendMoneyResponse { //for requester
    private String messageId;
    private String walletId;
    private String firstName;
    private String lastName;
    private String fcmToken;
    private double walletBalance;
    private double lastSentReceivedAmount;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

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

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public double getLastSentReceivedAmount() {
        return lastSentReceivedAmount;
    }

    public void setLastSentReceivedAmount(double lastSentReceivedAmount) {
        this.lastSentReceivedAmount = lastSentReceivedAmount;
    }
}
