package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class TypeDetails {

    @SerializedName("is_business")
    private int isBusiness;

    @SerializedName("service_type")
    private int serviceType;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("type_image_url")
    private String typeImageUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("map_pin_image_url")
    private String mapPinImageUrl;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("_id")
    private String id;

    @SerializedName("priority")
    private int priority;

    @SerializedName("typename")
    private String typename;


    @SerializedName("server_time")
    private String serverTime;

    @SerializedName("surge_start_hour")
    private int surgeStartHour;

    @SerializedName("timezone")
    private String timezone;


    @SerializedName("provider_min_wallet_amount_set_for_received_cash_request")
    private int providerMinWalletAmountSetForReceivedCashRequest;

    @SerializedName("distance_price")
    private double distancePrice;

    @SerializedName("surge_end_hour")
    private int surgeEndHour;

    @SerializedName("unit")
    private int unit;

    @SerializedName("is_surge_hours")
    private int isSurgeHours;

    @SerializedName("base_price")
    private double basePrice;


    @SerializedName("is_auto_transfer")
    private boolean isAutoTransfer;

    @SerializedName("typeid")
    private String typeid;

    @SerializedName("currency")
    private String currency;

    @SerializedName("is_check_provider_wallet_amount_for_received_cash_request")
    private boolean isCheckProviderWalletAmountForReceivedCashRequest;

    @SerializedName("time_price")
    private double timePrice;


    @SerializedName("base_price_distance")
    private double basePriceDistance;

    @SerializedName("country_id")
    private String countryId;


    public int getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(int isBusiness) {
        this.isBusiness = isBusiness;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTypeImageUrl() {
        return typeImageUrl;
    }

    public void setTypeImageUrl(String typeImageUrl) {
        this.typeImageUrl = typeImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMapPinImageUrl() {
        return mapPinImageUrl;
    }

    public void setMapPinImageUrl(String mapPinImageUrl) {
        this.mapPinImageUrl = mapPinImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getSurgeStartHour() {
        return surgeStartHour;
    }

    public void setSurgeStartHour(int surgeStartHour) {
        this.surgeStartHour = surgeStartHour;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getProviderMinWalletAmountSetForReceivedCashRequest() {
        return providerMinWalletAmountSetForReceivedCashRequest;
    }

    public void setProviderMinWalletAmountSetForReceivedCashRequest(int providerMinWalletAmountSetForReceivedCashRequest) {
        this.providerMinWalletAmountSetForReceivedCashRequest = providerMinWalletAmountSetForReceivedCashRequest;
    }

    public double getDistancePrice() {
        return distancePrice;
    }

    public void setDistancePrice(double distancePrice) {
        this.distancePrice = distancePrice;
    }

    public int getSurgeEndHour() {
        return surgeEndHour;
    }

    public void setSurgeEndHour(int surgeEndHour) {
        this.surgeEndHour = surgeEndHour;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getIsSurgeHours() {
        return isSurgeHours;
    }

    public void setIsSurgeHours(int isSurgeHours) {
        this.isSurgeHours = isSurgeHours;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isAutoTransfer() {
        return isAutoTransfer;
    }

    public void setAutoTransfer(boolean autoTransfer) {
        isAutoTransfer = autoTransfer;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isCheckProviderWalletAmountForReceivedCashRequest() {
        return isCheckProviderWalletAmountForReceivedCashRequest;
    }

    public void setCheckProviderWalletAmountForReceivedCashRequest(boolean checkProviderWalletAmountForReceivedCashRequest) {
        isCheckProviderWalletAmountForReceivedCashRequest = checkProviderWalletAmountForReceivedCashRequest;
    }

    public double getTimePrice() {
        return timePrice;
    }

    public void setTimePrice(double timePrice) {
        this.timePrice = timePrice;
    }

    public double getBasePriceDistance() {
        return basePriceDistance;
    }

    public void setBasePriceDistance(double basePriceDistance) {
        this.basePriceDistance = basePriceDistance;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}