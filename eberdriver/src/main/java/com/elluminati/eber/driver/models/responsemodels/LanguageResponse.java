package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Language;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguageResponse {
    @SerializedName("error_code")
    @Expose
    private int errorCode;
    @SerializedName("languages")
    private List<Language> languages;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
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

    @Override
    public String toString() {
        return "LanguageResponse{" + "languages = '" + languages + '\'' + ",success = '" + success + '\'' + ",message = '" + message + '\'' + "}";
    }
}