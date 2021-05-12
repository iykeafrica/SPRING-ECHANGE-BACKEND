package com.iykeafrica.echange.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "extras")
public class ExtrasEntity implements Serializable {

    private static final long serialVersionUID = -8352499831094663598L;
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 30)
    private String extrasId;

    @Column(nullable = false, length = 120)
    private String address;

    @Column(nullable = false, length = 10)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_echange_id")
    private UserEntity userDetails;

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

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
