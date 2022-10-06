package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Bankdetails;
import com.google.gson.annotations.SerializedName;

public class BankDetailResponse {

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("bankdetails")
    private Bankdetails bankdetails;
    @SerializedName("message")
    private String message;

    @SerializedName("payment_gateway_type")
    private int paymentGatewayType;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Bankdetails getBankdetails() {
        return bankdetails;
    }

    public void setBankdetails(Bankdetails bankdetails) {
        this.bankdetails = bankdetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BankDetailResponse{" + "success = '" + success + '\'' + ",bankdetails = '" + bankdetails + '\'' + ",message = '" + message + '\'' + "}";
    }

    public int getPaymentGatewayType() {
        return paymentGatewayType;
    }
}