package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravi Bhalodi on 06,March,2020 in Elluminati
 */
public class PaymentResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("reference")
    private String reference;

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("payment_status")
    private int paymentStatus;

    public String getPaymentMethod() {
        return paymentMethod;
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


    public String getClientSecret() {
        return clientSecret;
    }

    public String getError() {
        return error;
    }

    public String getReference() {
        return reference;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }
}
