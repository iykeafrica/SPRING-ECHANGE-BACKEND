package com.iykeafrica.echange.ui.model.response;

public class UserVerifyWalletIdResponse {
    private String walletId;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
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
}
