package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleDetail {


    @SerializedName("type_image_url")
    private String typeImageUrl;
    @SerializedName("passing_year")
    private String passingYear;
    @SerializedName("service_type")
    private String serviceType = "";
    @SerializedName("color")
    private String color;
    @SerializedName("accessibility")
    private List<String> accessibility;
    @SerializedName("is_selected")
    private boolean isSelected;
    @SerializedName("is_documents_expired")
    private boolean isDocumentsExpired;
    @SerializedName("plate_no")
    private String plateNo;
    @SerializedName("admin_type_id")
    private String adminTypeId;
    @SerializedName("name")
    private String name;
    @SerializedName("model")
    private String model;
    @SerializedName("_id")
    private String id;

    @SerializedName("is_document_uploaded")
    private boolean isDocumentUploaded = true;

    public boolean getIsDocumentUploaded() {
        return isDocumentUploaded;
    }

    public void setIsDocumentUploaded(boolean isDocumentUploaded) {
        this.isDocumentUploaded = isDocumentUploaded;
    }

    public String getTypeImageUrl() {
        return typeImageUrl;
    }

    public void setTypeImageUrl(String typeImageUrl) {
        this.typeImageUrl = typeImageUrl;
    }

    public String getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear = passingYear;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(List<String> accessibility) {
        this.accessibility = accessibility;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isIsDocumentsExpired() {
        return isDocumentsExpired;
    }

    public void setIsDocumentsExpired(boolean isDocumentsExpired) {
        this.isDocumentsExpired = isDocumentsExpired;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getAdminTypeId() {
        return adminTypeId;
    }

    public void setAdminTypeId(String adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "VehicleDetail{" + "passing_year = '" + passingYear + '\'' + ",service_type = '" + serviceType + '\'' + ",color = '" + color + '\'' + ",accessibility = '" + accessibility + '\'' + ",is_selected = '" + isSelected + '\'' + ",is_documents_expired = '" + isDocumentsExpired + '\'' + ",plate_no = '" + plateNo + '\'' + ",admin_type_id = '" + adminTypeId + '\'' + ",name = '" + name + '\'' + ",model = '" + model + '\'' + ",_id = '" + id + '\'' + ",is_vehicle_documents_expired = '" + '\'' + "}";
    }
}