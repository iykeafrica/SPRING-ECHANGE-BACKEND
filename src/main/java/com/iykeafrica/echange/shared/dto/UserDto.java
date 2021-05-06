package com.iykeafrica.echange.shared.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 7261182389483658612L;
    private long id;  //Autoincrement Id from db
    private String walletId; //public user Id which we will returned in the mobile application response
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String fcmMessageToken;
    private double walletBalance;
    private double lastSentReceivedAmount;
    private String fcmAuthToken;
    private String transactionPin;
    private String encryptedTransactionPin;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private List<ExtrasDTO> extras;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFcmAuthToken() {
        return fcmAuthToken;
    }

    public void setFcmAuthToken(String fcmAuthToken) {
        this.fcmAuthToken = fcmAuthToken;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }


    public String getEncryptedTransactionPin() {
        return encryptedTransactionPin;
    }

    public void setEncryptedTransactionPin(String encryptedTransactionPin) {
        this.encryptedTransactionPin = encryptedTransactionPin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public List<ExtrasDTO> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasDTO> extras) {
        this.extras = extras;
    }
}
