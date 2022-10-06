package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateFirebaseTokenResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("error_code")
    private int errorCode;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
