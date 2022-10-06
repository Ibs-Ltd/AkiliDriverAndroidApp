package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.CurrentVehicle;
import com.elluminati.eber.driver.models.datamodels.PartnerDetail;
import com.elluminati.eber.driver.models.datamodels.Provider;
import com.elluminati.eber.driver.models.datamodels.TypeDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderDetailResponse {

    @SerializedName("error_code")
    @Expose
    private int errorCode;
    @SerializedName("partner_detail")
    private PartnerDetail partnerDetail;
    @SerializedName("provider")

    private Provider provider;
    @SerializedName("success")
    private boolean success;
    @SerializedName("current_vehicle")
    private CurrentVehicle currentVehicle;
    @SerializedName("message")
    private String message;
    @SerializedName("type_details")
    private TypeDetails typeDetails;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public PartnerDetail getPartnerDetail() {
        return partnerDetail;
    }

    public void setPartnerDetail(PartnerDetail partnerDetail) {
        this.partnerDetail = partnerDetail;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CurrentVehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(CurrentVehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeDetails getTypeDetails() {
        return typeDetails;
    }

    public void setTypeDetails(TypeDetails typeDetails) {
        this.typeDetails = typeDetails;
    }

}