package com.iykeafrica.echange.shared.dto;

import java.io.Serializable;

public class ExtrasDTO implements Serializable {
    private static final long serialVersionUID = 8860109737955540494L;
    private long id;
    private String extrasId;
    private String address;
    private String type;
    private UserDto userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtrasId() {
        return extrasId;
    }

    public void setExtrasId(String extrasId) {
        this.extrasId = extrasId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }
}
