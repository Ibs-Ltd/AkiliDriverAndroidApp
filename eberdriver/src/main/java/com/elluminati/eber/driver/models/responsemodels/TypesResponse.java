package com.elluminati.eber.driver.models.responsemodels;


import com.elluminati.eber.driver.models.datamodels.CityDetail;
import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.PaymentGateway;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypesResponse {

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("server_time")
    private String serverTime;
    @SerializedName("citytypes")
    private List<CityType> cityTypes;
    @SerializedName("payment_gateway")
    private List<PaymentGateway> paymentGateway;
    @SerializedName("success")
    private boolean success;
    @SerializedName("currency")
    private String currency;
    @SerializedName("message")
    private String message;
    @SerializedName("city_detail")
    private CityDetail cityDetail;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<CityType> getCityTypes() {
        return cityTypes;
    }

    public void setCityTypes(List<CityType> cityTypes) {
        this.cityTypes = cityTypes;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public List<PaymentGateway> getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(List<PaymentGateway> paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CityDetail getCityDetail() {
        return cityDetail;
    }

    public void setCityDetail(CityDetail cityDetail) {
        this.cityDetail = cityDetail;
    }

    @Override
    public String toString() {
        return "TypesResponse{" + "server_time = '" + serverTime + '\'' + ",citytypes = '" + '\'' + ",payment_gateway = '" + paymentGateway + '\'' + ",success = '" + success + '\'' + ",currency = '" + currency + '\'' + ",message = '" + message + '\'' + ",city_detail = '" + cityDetail + '\'' + "}";
    }
}