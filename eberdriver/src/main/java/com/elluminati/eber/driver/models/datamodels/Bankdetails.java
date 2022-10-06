package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class Bankdetails {

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("account_holder_name")
    private String accountHolderName;

    @SerializedName("account_holder_type")
    private String accountHolderType;

    @SerializedName("routing_number")
    private String routingNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderType() {
        return accountHolderType;
    }

    public void setAccountHolderType(String accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    @Override
    public String toString() {
        return "Bankdetails{" + "account_number = '" + accountNumber + '\'' + ",account_holder_name = '" + accountHolderName + '\'' + ",account_holder_type = '" + accountHolderType + '\'' + ",routing_number = '" + routingNumber + '\'' + "}";
    }
}