package com.iykeafrica.echange.ui.model.request;

public class TransactionRequestModel {
    private String alert;
    private String name;
    private String description;
    private double previousBalance;
    private double availableBalance;
    private long date;

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
}