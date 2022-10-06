package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Document;
import com.elluminati.eber.driver.models.datamodels.VehicleDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleDetailResponse {

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("document_list")
    private List<Document> documentList;
    @SerializedName("vehicle_detail")
    private VehicleDetail vehicleDetail;

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

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public VehicleDetail getVehicleDetail() {
        return vehicleDetail;
    }

    public void setVehicleDetail(VehicleDetail vehicleDetail) {
        this.vehicleDetail = vehicleDetail;
    }

    @Override
    public String toString() {
        return "VehicleDetailResponse{" + "success = '" + success + '\'' + ",document_list = '" + documentList + '\'' + ",vehicle_detail = '" + vehicleDetail + '\'' + "}";
    }
}