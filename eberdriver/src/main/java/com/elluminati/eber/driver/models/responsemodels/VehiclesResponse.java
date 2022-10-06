package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.VehicleDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehiclesResponse {

    @SerializedName("vehicle_list")
    private List<VehicleDetail> vehicleList;

    @SerializedName("success")
    private boolean success;

    @SerializedName("error_code")
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<VehicleDetail> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleDetail> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "VehiclesResponse{" + "vehicle_list = '" + vehicleList + '\'' + ",success = '" + success + '\'' + "}";
    }
}