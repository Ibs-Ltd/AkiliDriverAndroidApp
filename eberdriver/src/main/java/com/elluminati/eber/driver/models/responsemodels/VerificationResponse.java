package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

public class VerificationResponse {
    @SerializedName("userEmailVerification")
    private boolean userEmailVerification;
    @SerializedName("userSms")
    private boolean userSms;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("otpForSMS")
    private String otpForSMS;
    @SerializedName("otpForEmail")
    private String otpForEmail;

    public boolean isUserEmailVerification() {
        return userEmailVerification;
    }

    public void setUserEmailVerification(boolean userEmailVerification) {
        this.userEmailVerification = userEmailVerification;
    }

    public boolean isUserSms() {
        return userSms;
    }

    public void setUserSms(boolean userSms) {
        this.userSms = userSms;
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

    public String getOtpForSMS() {
        return otpForSMS;
    }

    public void setOtpForSMS(String otpForSMS) {
        this.otpForSMS = otpForSMS;
    }

    public String getOtpForEmail() {
        return otpForEmail;
    }

    public void setOtpForEmail(String otpForEmail) {
        this.otpForEmail = otpForEmail;
    }
}
