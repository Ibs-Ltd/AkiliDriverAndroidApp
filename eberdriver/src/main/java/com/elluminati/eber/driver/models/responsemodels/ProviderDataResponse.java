package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.ProviderData;
import com.google.gson.annotations.SerializedName;

public class ProviderDataResponse {
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;
    @SerializedName("provider_detail")
    private ProviderData providerData;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProviderData getProviderData() {
        return providerData;
    }

    public void setProviderData(ProviderData providerData) {
        this.providerData = providerData;
    }
}
