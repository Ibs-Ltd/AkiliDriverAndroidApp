package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("country")
    private String country;

    @SerializedName("total_request")
    private int totalRequest;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("gender")
    private String gender;

    @SerializedName("city")
    private String city;

    @SerializedName("device_timezone")
    private String deviceTimezone;

    @SerializedName("is_document_uploaded")
    private int isDocumentUploaded;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("bio")
    private String bio;

    @SerializedName("device_type")
    private String deviceType;

    @SerializedName("is_referral")
    private int isReferral;

    @SerializedName("work_location")
    private List<Double> workLocation;

    @SerializedName("wallet_currency_code")
    private String walletCurrencyCode;

    @SerializedName("refferal_credit")
    private double refferalCredit;

    @SerializedName("password")
    private String password;

    @SerializedName("user_type")
    private int userType;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("home_address")
    private String homeAddress;

    @SerializedName("rate")
    private double rate;

    @SerializedName("cancelled_request")
    private int cancelledRequest;

    @SerializedName("social_unique_id")
    private String socialUniqueId;

    @SerializedName("country_phone_code")
    private String countryPhoneCode;

    @SerializedName("total_referrals")
    private int totalReferrals;

    @SerializedName("home_location")
    private List<Double> homeLocation;

    @SerializedName("user_type_id")
    private Object userTypeId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("unique_id")
    private int uniqueId;

    @SerializedName("wallet")
    private double wallet;

    @SerializedName("address")
    private String address;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("completed_request")
    private int completedRequest;

    @SerializedName("picture")
    private String picture;

    @SerializedName("token")
    private String token;

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("current_trip_id")
    private String currentTripId;

    @SerializedName("rate_count")
    private int rateCount;

    @SerializedName("work_address")
    private String workAddress;

    @SerializedName("phone")
    private String phone;

    @SerializedName("referral_code")
    private String referralCode;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("promo_count")
    private int promoCount;

    @SerializedName("is_approved")
    private int isApproved;

    @SerializedName("_id")
    private String id;

    @SerializedName("login_by")
    private String loginBy;

    @SerializedName("is_use_wallet")
    private int isUseWallet;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(int totalRequest) {
        this.totalRequest = totalRequest;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeviceTimezone() {
        return deviceTimezone;
    }

    public void setDeviceTimezone(String deviceTimezone) {
        this.deviceTimezone = deviceTimezone;
    }

    public int getIsDocumentUploaded() {
        return isDocumentUploaded;
    }

    public void setIsDocumentUploaded(int isDocumentUploaded) {
        this.isDocumentUploaded = isDocumentUploaded;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(int isReferral) {
        this.isReferral = isReferral;
    }

    public List<Double> getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(List<Double> workLocation) {
        this.workLocation = workLocation;
    }

    public String getWalletCurrencyCode() {
        return walletCurrencyCode;
    }

    public void setWalletCurrencyCode(String walletCurrencyCode) {
        this.walletCurrencyCode = walletCurrencyCode;
    }

    public double getRefferalCredit() {
        return refferalCredit;
    }

    public void setRefferalCredit(double refferalCredit) {
        this.refferalCredit = refferalCredit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getCancelledRequest() {
        return cancelledRequest;
    }

    public void setCancelledRequest(int cancelledRequest) {
        this.cancelledRequest = cancelledRequest;
    }

    public String getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(String socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public int getTotalReferrals() {
        return totalReferrals;
    }

    public void setTotalReferrals(int totalReferrals) {
        this.totalReferrals = totalReferrals;
    }

    public List<Double> getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(List<Double> homeLocation) {
        this.homeLocation = homeLocation;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCompletedRequest() {
        return completedRequest;
    }

    public void setCompletedRequest(int completedRequest) {
        this.completedRequest = completedRequest;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCurrentTripId() {
        return currentTripId;
    }

    public void setCurrentTripId(String currentTripId) {
        this.currentTripId = currentTripId;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public int getPromoCount() {
        return promoCount;
    }

    public void setPromoCount(int promoCount) {
        this.promoCount = promoCount;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public int getIsUseWallet() {
        return isUseWallet;
    }

    public void setIsUseWallet(int isUseWallet) {
        this.isUseWallet = isUseWallet;
    }

    @Override
    public String toString() {
        return "User{" + "country = '" + country + '\'' + ",total_request = '" + totalRequest + '\'' + ",app_version = '" + appVersion + '\'' + ",gender = '" + gender + '\'' + ",city = '" + city + '\'' + ",device_timezone = '" + deviceTimezone + '\'' + ",is_document_uploaded = '" + isDocumentUploaded + '\'' + ",created_at = '" + createdAt + '\'' + ",bio = '" + bio + '\'' + ",device_type = '" + deviceType + '\'' + ",is_referral = '" + isReferral + '\'' + ",work_location = '" + workLocation + '\'' + ",wallet_currency_code = '" + walletCurrencyCode + '\'' + ",refferal_credit = '" + refferalCredit + '\'' + ",password = '" + password + '\'' + ",user_type = '" + userType + '\'' + ",updated_at = '" + updatedAt + '\'' + ",home_address = '" + homeAddress + '\'' + ",rate = '" + rate + '\'' + ",cancelled_request = '" + cancelledRequest + '\'' + ",social_unique_id = '" + socialUniqueId + '\'' + ",country_phone_code = '" + countryPhoneCode + '\'' + ",total_referrals = '" + totalReferrals + '\'' + ",home_location = '" + homeLocation + '\'' + ",user_type_id = '" + userTypeId + '\'' + ",first_name = '" + firstName + '\'' + ",email = '" + email + '\'' + ",unique_id = '" + uniqueId + '\'' + ",wallet = '" + wallet + '\'' + ",address = '" + address + '\'' + ",last_name = '" + lastName + '\'' + ",completed_request = '" + completedRequest + '\'' + ",picture = '" + picture + '\'' + ",token = '" + token + '\'' + ",zipcode = '" + zipcode + '\'' + ",current_trip_id = '" + currentTripId + '\'' + ",rate_count = '" + rateCount + '\'' + ",work_address = '" + workAddress + '\'' + ",phone = '" + phone + '\'' + ",referral_code = '" + referralCode + '\'' + ",device_token = '" + deviceToken + '\'' + ",promo_count = '" + promoCount + '\'' + ",is_approved = '" + isApproved + '\'' + ",_id = '" + id + '\'' + ",login_by = '" + loginBy + '\'' + ",is_use_wallet = '" + isUseWallet + '\'' + "}";
    }
}