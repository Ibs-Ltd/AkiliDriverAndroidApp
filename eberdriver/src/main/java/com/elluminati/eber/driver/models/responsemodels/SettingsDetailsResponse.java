package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.AdminSettings;
import com.elluminati.eber.driver.models.datamodels.ProviderData;
import com.google.gson.annotations.SerializedName;

public class SettingsDetailsResponse {


    @SerializedName("trip_detail")
    private TripsResponse tripsResponse;
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("setting_detail")
    private AdminSettings adminSettings;
    @SerializedName("provider_detail")
    private ProviderData providerData;


    public TripsResponse getTripsResponse() {
        return tripsResponse;
    }

    public void setTripsResponse(TripsResponse tripsResponse) {
        this.tripsResponse = tripsResponse;
    }

    public ProviderData getProviderData() {
        return providerData;
    }

    public void setProviderData(ProviderData providerData) {
        this.providerData = providerData;
    }

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

    public AdminSettings getAdminSettings() {
        return adminSettings;
    }

    public void setAdminSettings(AdminSettings adminSettings) {
        this.adminSettings = adminSettings;
    }


}
