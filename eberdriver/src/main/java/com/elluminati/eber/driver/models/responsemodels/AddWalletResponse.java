package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

public class AddWalletResponse {

    @SerializedName("wallet")
    private double wallet;
    @SerializedName("wallet_currency_code")
    private String walletCurrencyCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getWalletCurrencyCode() {
        return walletCurrencyCode;
    }

    public void setWalletCurrencyCode(String walletCurrencyCode) {
        this.walletCurrencyCode = walletCurrencyCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
