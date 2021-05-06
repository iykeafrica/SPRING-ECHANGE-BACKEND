package com.iykeafrica.echange.ui.model.response;

import java.util.List;

public class UserRest {
    private String walletId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private double walletBalance;
    private String fcmMessageToken;
    private List<ExtrasRest> extras;

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

    public String getFcmMessageToken() {
        return fcmMessageToken;
    }

    public void setFcmMessageToken(String fcmMessageToken) {
        this.fcmMessageToken = fcmMessageToken;
    }

    public List<ExtrasRest> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasRest> extras) {
        this.extras = extras;
    }
}
