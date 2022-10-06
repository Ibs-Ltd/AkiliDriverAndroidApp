package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Document;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentResponse {
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("providerdocument")
    private List<Document> documents;
    @SerializedName("message")
    private String message;

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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DocumentResponse{" + "success = '" + success + '\'' + ",userdocument = '" + documents + '\'' + ",message = '" + message + '\'' + "}";
    }
}