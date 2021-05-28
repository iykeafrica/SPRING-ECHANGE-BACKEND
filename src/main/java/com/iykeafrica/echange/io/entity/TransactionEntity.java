package com.iykeafrica.echange.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "transactions")
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = -8352499831094663598L;
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 30)
    private String transactionId;

    @Column(nullable = false, length = 15)
    private String alert;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String description;

    @Column(nullable = false, length = 120)
    private double amount;

    @Column(nullable = false, length = 120)
    private double previousBalance = 0.0;

    @Column(nullable = false, length = 120)
    private double availableBalance = 1000.0;

    @Column(nullable = false, length = 120)
    private long date;

    @ManyToOne
    @JoinColumn(name = "users_echange_id")
    private UserEntity userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}