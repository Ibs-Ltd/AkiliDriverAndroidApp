package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderDailyAnalytic {

    @SerializedName("rejection_ratio")
    private double rejectionRatio;

    @SerializedName("date_server_timezone")
    private String dateServerTimezone;

    @SerializedName("unique_id")
    private int uniqueId;

    @SerializedName("completed_ratio")
    private double completedRatio;

    @SerializedName("online_times")
    private List<Object> onlineTimes;

    @SerializedName("rejected")
    private int rejected;

    @SerializedName("cancellation_ratio")
    private double cancellationRatio;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("accepted")
    private int accepted;

    @SerializedName("date_tag")
    private String dateTag;

    @SerializedName("received")
    private int received;

    @SerializedName("completed")
    private int completed;

    @SerializedName("not_answered")
    private int notAnswered;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("total_online_time")
    private int totalOnlineTime;

    @SerializedName("provider_id")
    private String providerId;

    @SerializedName("cancelled")
    private int cancelled;

    @SerializedName("_id")
    private String id;

    @SerializedName("acception_ratio")
    private double acceptionRatio;

    public double getRejectionRatio() {
        return rejectionRatio;
    }

    public void setRejectionRatio(double rejectionRatio) {
        this.rejectionRatio = rejectionRatio;
    }

    public String getDateServerTimezone() {
        return dateServerTimezone;
    }

    public void setDateServerTimezone(String dateServerTimezone) {
        this.dateServerTimezone = dateServerTimezone;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getCompletedRatio() {
        return completedRatio;
    }

    public void setCompletedRatio(double completedRatio) {
        this.completedRatio = completedRatio;
    }

    public List<Object> getOnlineTimes() {
        return onlineTimes;
    }

    public void setOnlineTimes(List<Object> onlineTimes) {
        this.onlineTimes = onlineTimes;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }

    public double getCancellationRatio() {
        return cancellationRatio;
    }

    public void setCancellationRatio(double cancellationRatio) {
        this.cancellationRatio = cancellationRatio;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getDateTag() {
        return dateTag;
    }

    public void setDateTag(String dateTag) {
        this.dateTag = dateTag;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getNotAnswered() {
        return notAnswered;
    }

    public void setNotAnswered(int notAnswered) {
        this.notAnswered = notAnswered;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(int totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAcceptionRatio() {
        return acceptionRatio;
    }

    public void setAcceptionRatio(double acceptionRatio) {
        this.acceptionRatio = acceptionRatio;
    }

    @Override
    public String toString() {
        return "ProviderDailyAnalytic{" + "rejection_ratio = '" + rejectionRatio + '\'' + ",date_server_timezone = '" + dateServerTimezone + '\'' + ",unique_id = '" + uniqueId + '\'' + ",completed_ratio = '" + completedRatio + '\'' + ",online_times = '" + onlineTimes + '\'' + ",rejected = '" + rejected + '\'' + ",cancellation_ratio = '" + cancellationRatio + '\'' + ",created_at = '" + createdAt + '\'' + ",accepted = '" + accepted + '\'' + ",date_tag = '" + dateTag + '\'' + ",received = '" + received + '\'' + ",completed = '" + completed + '\'' + ",not_answered = '" + notAnswered + '\'' + ",updated_at = '" + updatedAt + '\'' + ",total_online_time = '" + totalOnlineTime + '\'' + ",provider_id = '" + providerId + '\'' + ",cancelled = '" + cancelled + '\'' + ",_id = '" + id + '\'' + ",acception_ratio = '" + acceptionRatio + '\'' + "}";
    }
}