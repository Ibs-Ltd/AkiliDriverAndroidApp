package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class TripsEarning {

    @SerializedName("provider_income_set_in_wallet")
    private double providerIncomeSetInWallet;
    @SerializedName("pay_to_provider")
    private double payToProvider;
    @SerializedName("provider_have_cash")
    private double providerHaveCash;
    @SerializedName("payment_mode")
    private int paymentMode;
    @SerializedName("total")
    private double total;
    @SerializedName("unique_id")
    private int uniqueId;
    @SerializedName("provider_service_fees")
    private double providerServiceFees;

    public double getProviderIncomeSetInWallet() {
        return providerIncomeSetInWallet;
    }

    public void setProviderIncomeSetInWallet(double providerIncomeSetInWallet) {
        this.providerIncomeSetInWallet = providerIncomeSetInWallet;
    }

    public double getPayToProvider() {
        return payToProvider;
    }

    public void setPayToProvider(double payToProvider) {
        this.payToProvider = payToProvider;
    }

    public double getProviderHaveCash() {
        return providerHaveCash;
    }

    public void setProviderHaveCash(double providerHaveCash) {
        this.providerHaveCash = providerHaveCash;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getProviderServiceFees() {
        return providerServiceFees;
    }

    public void setProviderServiceFees(double providerServiceFees) {
        this.providerServiceFees = providerServiceFees;
    }

    @Override
    public String toString() {
        return "TripsEarning{" + "payment_mode = '" + paymentMode + '\'' + ",total = '" + total + '\'' + ",unique_id = '" + uniqueId + '\'' + ",provider_service_fees = '" + providerServiceFees + '\'' + "}";
    }
}