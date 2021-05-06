package com.iykeafrica.echange.ui.model.request;

public class UserUpdatePersonalRecordRequest {
    private String firstName;
    private String lastName;
    private String fcmMessageToken;

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

    public String getFcmMessageToken() {
        return fcmMessageToken;
    }

    public void setFcmMessageToken(String fcmMessageToken) {
        this.fcmMessageToken = fcmMessageToken;
    }
}
