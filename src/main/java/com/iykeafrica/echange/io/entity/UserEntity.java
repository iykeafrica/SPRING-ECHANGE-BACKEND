package com.iykeafrica.echange.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "users_echange")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 7559215709534810197L;

    @Id
    @GeneratedValue
    private long id;//Autoincrement Id from db

    @Column(nullable = false)
    private String walletId; //public user Id which we will returned in the mobile application response

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private String fcmMessageToken;

    @Column(nullable = false)
    private double walletBalance;

    @Column(nullable = false)
    private double lastSentReceivedAmount;

    @Column(nullable = false)
    private String fcmAuthToken;

    @Column(nullable = false)
    private String encryptedTransactionPin;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private List<ExtrasEntity> extras;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String userId) {
        this.walletId = userId;
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

    public void setFcmMessageToken(String messageId) {
        this.fcmMessageToken = messageId;
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

    public void setFcmAuthToken(String fcmToken) {
        this.fcmAuthToken = fcmToken;
    }

    public String getEncryptedTransactionPin() {
        return encryptedTransactionPin;
    }

    public void setEncryptedTransactionPin(String transactionPin) {
        this.encryptedTransactionPin = transactionPin;
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

    public List<ExtrasEntity> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasEntity> extras) {
        this.extras = extras;
    }
}
