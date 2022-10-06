package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

public class IsSuccessResponse {


    @SerializedName("total_referral_credit")
    private double totalReferralCredit;

    @SerializedName("stripe_error")
    private String stripeError;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("is_referral")
    private int isReferral;
    @SerializedName("is_provider_accepted")
    private int isProviderAccepted;
    @SerializedName("is_active")
    private int isActive;

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getStripeError() {
        return stripeError;
    }

    public void setStripeError(String stripeError) {
        this.stripeError = stripeError;
    }

    public int getIsProviderAccepted() {
        return isProviderAccepted;
    }

    public void setIsProviderAccepted(int isProviderAccepted) {
        this.isProviderAccepted = isProviderAccepted;
    }

    public int getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(int isReferral) {
        this.isReferral = isReferral;
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

    public double getTotalReferralCredit() {
        return totalReferralCredit;
    }

    public void setTotalReferralCredit(double totalReferralCredit) {
        this.totalReferralCredit = totalReferralCredit;
    }
}
