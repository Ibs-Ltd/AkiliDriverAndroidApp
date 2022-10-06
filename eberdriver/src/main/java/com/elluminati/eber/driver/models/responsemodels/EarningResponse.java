package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.ProviderDailyAnalytic;
import com.elluminati.eber.driver.models.datamodels.ProviderEarning;
import com.elluminati.eber.driver.models.datamodels.TripsEarning;
import com.elluminati.eber.driver.models.datamodels.WeekData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarningResponse {
    @SerializedName("currency")
    @Expose
    private String currency = "";
    @SerializedName("message")
    @Expose
    private int message;
    @SerializedName("error_code")
    @Expose
    private int errorCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("trips")
    private List<TripsEarning> trips;
    @SerializedName("provider_daily_analytic")
    private ProviderDailyAnalytic providerDailyAnalytic;
    @SerializedName("provider_weekly_analytic")
    private ProviderDailyAnalytic providerWeekAnalytic;
    @SerializedName("provider_daily_earning")
    private ProviderEarning providerDayEarning;
    @SerializedName("provider_weekly_earning")
    private ProviderEarning providerWeekEarning;
    @SerializedName("date")
    private WeekData dayOfWeekDate;
    @SerializedName("trip_day_total")
    private WeekData dayOfTripTotal;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ProviderEarning getProviderWeekEarning() {
        return providerWeekEarning;
    }

    public void setProviderWeekEarning(ProviderEarning providerWeekEarning) {
        this.providerWeekEarning = providerWeekEarning;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ProviderDailyAnalytic getProviderWeekAnalytic() {
        return providerWeekAnalytic;
    }

    public void setProviderWeekAnalytic(ProviderDailyAnalytic providerWeekAnalytic) {
        this.providerWeekAnalytic = providerWeekAnalytic;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TripsEarning> getTrips() {
        return trips;
    }

    public void setTrips(List<TripsEarning> trips) {
        this.trips = trips;
    }

    public ProviderDailyAnalytic getProviderDailyAnalytic() {
        return providerDailyAnalytic;
    }

    public void setProviderDailyAnalytic(ProviderDailyAnalytic providerDailyAnalytic) {
        this.providerDailyAnalytic = providerDailyAnalytic;
    }

    public ProviderEarning getProviderDayEarning() {
        return providerDayEarning;
    }

    public void setProviderDayEarning(ProviderEarning providerDayEarning) {
        this.providerDayEarning = providerDayEarning;
    }

    @Override
    public String toString() {
        return "EarningResponse{" + "success = '" + success + '\'' + ",trips = '" + trips + '\'' + ",provider_daily_analytic = '" + providerDailyAnalytic + '\'' + ",provider_daily_earning = '" + providerDayEarning + '\'' + "}";
    }

    public WeekData getDayOfWeekDate() {
        return dayOfWeekDate;
    }

    public void setDayOfWeekDate(WeekData dayOfWeekDate) {
        this.dayOfWeekDate = dayOfWeekDate;
    }

    public WeekData getDayOfTripTotal() {
        return dayOfTripTotal;
    }

    public void setDayOfTripTotal(WeekData dayOfTripTotal) {
        this.dayOfTripTotal = dayOfTripTotal;
    }
}