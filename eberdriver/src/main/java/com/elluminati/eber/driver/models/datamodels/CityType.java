package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityType {

    public boolean isSelected;
    @SerializedName("surge_hours")
    private List<SurgeTime> surgeHours;
    @SerializedName("price_per_unit_distance")
    private double pricePerUnitDistance;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("price_for_waiting_time")
    private double priceForWaitingTime;
    @SerializedName("cancellation_fee")
    private double cancellationFee;
    @SerializedName("provider_profit")
    private double providerProfit;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("min_fare")
    private double minFare;
    @SerializedName("user_tax")
    private double userTax;
    @SerializedName("is_surge_hours")
    private int isSurgeHours;
    @SerializedName("surge_multiplier")
    private double surgeMultiplier;
    @SerializedName("provider_tax")
    private double providerTax;
    @SerializedName("zone_multiplier")
    private double zoneMultiplier;
    @SerializedName("base_price")
    private double basePrice;
    @SerializedName("waiting_time_start_after_minute")
    private int waitingTimeStartAfterMinute;
    @SerializedName("provider_miscellaneous_fee")
    private double providerMiscellaneousFee;
    @SerializedName("typeid")
    private String typeid;
    @SerializedName("user_miscellaneous_fee")
    private double userMiscellaneousFee;
    @SerializedName("type_details")
    private TypeDetails typeDetails;
    @SerializedName("price_for_total_time")
    private double priceForTotalTime;
    @SerializedName("base_price_distance")
    private double basePriceDistance;
    @SerializedName("is_buiesness")
    private int isBuiesness;
    @SerializedName("surge_start_hour")
    private int surgeStartHour;
    @SerializedName("cityname")
    private String cityname;
    @SerializedName("countryname")
    private String countryname;
    @SerializedName("tax")
    private double tax;
    @SerializedName("cityid")
    private String cityid;
    @SerializedName("max_space")
    private int maxSpace;
    @SerializedName("countryid")
    private String countryid;
    @SerializedName("surge_end_hour")
    private int surgeEndHour;
    @SerializedName("_id")
    private String id;
    @SerializedName("is_zone")
    private int isZone;
    @SerializedName("service_type_id")
    private String serviceTypeId;
    @SerializedName("service_type_name")
    private String serviceTypeName;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("rich_area_surge_multiplier")

    private double richAreaSurgeMultiplier;
    @SerializedName("is_car_rental_business")
    private int isCarRentalBusiness;
    @SerializedName("base_price_time")
    private double basePriceTime;
    @SerializedName("car_rental_ids")
    private List<Object> carRentalIds;
    @SerializedName("typename")
    private String typename;

    public List<SurgeTime> getSurgeHours() {
        return surgeHours;
    }

    public void setSurgeHours(List<SurgeTime> surgeHours) {
        this.surgeHours = surgeHours;
    }

    public double getRichAreaSurgeMultiplier() {
        return richAreaSurgeMultiplier;
    }

    public void setRichAreaSurgeMultiplier(double richAreaSurgeMultiplier) {
        this.richAreaSurgeMultiplier = richAreaSurgeMultiplier;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public double getPricePerUnitDistance() {
        return pricePerUnitDistance;
    }

    public void setPricePerUnitDistance(double pricePerUnitDistance) {
        this.pricePerUnitDistance = pricePerUnitDistance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getPriceForWaitingTime() {
        return priceForWaitingTime;
    }

    public void setPriceForWaitingTime(double priceForWaitingTime) {
        this.priceForWaitingTime = priceForWaitingTime;
    }

    public double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public double getProviderProfit() {
        return providerProfit;
    }

    public void setProviderProfit(double providerProfit) {
        this.providerProfit = providerProfit;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getMinFare() {
        return minFare;
    }

    public void setMinFare(double minFare) {
        this.minFare = minFare;
    }

    public double getUserTax() {
        return userTax;
    }

    public void setUserTax(double userTax) {
        this.userTax = userTax;
    }

    public int getIsSurgeHours() {
        return isSurgeHours;
    }

    public void setIsSurgeHours(int isSurgeHours) {
        this.isSurgeHours = isSurgeHours;
    }

    public double getSurgeMultiplier() {
        return surgeMultiplier;
    }

    public void setSurgeMultiplier(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    public double getProviderTax() {
        return providerTax;
    }

    public void setProviderTax(double providerTax) {
        this.providerTax = providerTax;
    }

    public double getZoneMultiplier() {
        return zoneMultiplier;
    }

    public void setZoneMultiplier(double zoneMultiplier) {
        this.zoneMultiplier = zoneMultiplier;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getWaitingTimeStartAfterMinute() {
        return waitingTimeStartAfterMinute;
    }

    public void setWaitingTimeStartAfterMinute(int waitingTimeStartAfterMinute) {
        this.waitingTimeStartAfterMinute = waitingTimeStartAfterMinute;
    }

    public double getProviderMiscellaneousFee() {
        return providerMiscellaneousFee;
    }

    public void setProviderMiscellaneousFee(double providerMiscellaneousFee) {
        this.providerMiscellaneousFee = providerMiscellaneousFee;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public double getUserMiscellaneousFee() {
        return userMiscellaneousFee;
    }

    public void setUserMiscellaneousFee(double userMiscellaneousFee) {
        this.userMiscellaneousFee = userMiscellaneousFee;
    }

    public TypeDetails getTypeDetails() {
        return typeDetails;
    }

    public void setTypeDetails(TypeDetails typeDetails) {
        this.typeDetails = typeDetails;
    }

    public double getPriceForTotalTime() {
        return priceForTotalTime;
    }

    public void setPriceForTotalTime(double priceForTotalTime) {
        this.priceForTotalTime = priceForTotalTime;
    }

    public double getBasePriceDistance() {
        return basePriceDistance;
    }

    public void setBasePriceDistance(double basePriceDistance) {
        this.basePriceDistance = basePriceDistance;
    }

    public int getIsBuiesness() {
        return isBuiesness;
    }

    public void setIsBuiesness(int isBuiesness) {
        this.isBuiesness = isBuiesness;
    }

    public int getSurgeStartHour() {
        return surgeStartHour;
    }

    public void setSurgeStartHour(int surgeStartHour) {
        this.surgeStartHour = surgeStartHour;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public int getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(int maxSpace) {
        this.maxSpace = maxSpace;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public int getSurgeEndHour() {
        return surgeEndHour;
    }

    public void setSurgeEndHour(int surgeEndHour) {
        this.surgeEndHour = surgeEndHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsZone() {
        return isZone;
    }

    public void setIsZone(int isZone) {
        this.isZone = isZone;
    }

    @Override
    public String toString() {
        return "CityType{" + "price_per_unit_distance = '" + pricePerUnitDistance + '\'' + ",created_at = '" + createdAt + '\'' + ",price_for_waiting_time = '" + priceForWaitingTime + '\'' + ",cancellation_fee = '" + cancellationFee + '\'' + ",provider_profit = '" + providerProfit + '\'' + ",updated_at = '" + updatedAt + '\'' + ",min_fare = '" + minFare + '\'' + ",user_tax = '" + userTax + '\'' + ",is_surge_hours = '" + isSurgeHours + '\'' + ",surge_multiplier = '" + surgeMultiplier + '\'' + ",provider_tax = '" + providerTax + '\'' + ",zone_multiplier = '" + zoneMultiplier + '\'' + ",base_price = '" + basePrice + '\'' + ",waiting_time_start_after_minute = '" + waitingTimeStartAfterMinute + '\'' + ",provider_miscellaneous_fee = '" + providerMiscellaneousFee + '\'' + ",typeid = '" + typeid + '\'' + ",user_miscellaneous_fee = '" + userMiscellaneousFee + '\'' + ",type_details = '" + typeDetails + '\'' + ",price_for_total_time = '" + priceForTotalTime + '\'' + ",base_price_distance = '" + basePriceDistance + '\'' + ",is_buiesness = '" + isBuiesness + '\'' + ",surge_start_hour = '" + surgeStartHour + '\'' + ",cityname = '" + cityname + '\'' + ",countryname = '" + countryname + '\'' + ",tax = '" + tax + '\'' + ",cityid = '" + cityid + '\'' + ",max_space = '" + maxSpace + '\'' + ",countryid = '" + countryid + '\'' + ",surge_end_hour = '" + surgeEndHour + '\'' + ",_id = '" + id + '\'' + ",is_zone = '" + isZone + '\'' + "}";
    }

    public double getBasePriceTime() {
        return basePriceTime;
    }

    public void setBasePriceTime(double basePriceTime) {
        this.basePriceTime = basePriceTime;
    }

    public List<Object> getCarRentalIds() {
        return carRentalIds;
    }

    public void setCarRentalIds(List<Object> carRentalIds) {
        this.carRentalIds = carRentalIds;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

}