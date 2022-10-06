package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderData {
    @SerializedName("country_detail")
    private Country countryDetail;
    @SerializedName("is_referral")
    private int isReferral;
    @SerializedName("social_ids")
    private List<String> socialIds;
    @SerializedName("trip_id")
    private String tripId = "";
    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("is_partner_approved_by_admin")
    private int isPartnerApprovedByAdmin;
    @SerializedName("provider_type_id")
    private String providerTypeId;
    @SerializedName("provider_type")
    private int providerType;
    @SerializedName("country")
    private String country;
    @SerializedName("partner_email")
    private String partnerEmail;
    @SerializedName("gender")
    private String gender;
    @SerializedName("app_version")
    private String appVersion;
    @SerializedName("city")
    private String city;
    @SerializedName("device_timezone")
    private String deviceTimezone;
    @SerializedName("is_document_uploaded")
    private int isDocumentUploaded;
    @SerializedName("bio")
    private String bio;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("country_phone_code")
    private String countryPhoneCode;
    @SerializedName("social_unique_id")
    private String socialUniqueId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private String address;
    @SerializedName("is_active")
    private int isActive;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("message")
    private String message;
    @SerializedName("picture")
    private String picture;
    @SerializedName("is_available")
    private int isAvailable;
    @SerializedName("token")
    private String token;
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("phone")
    private String phone;
    @SerializedName("success")
    private boolean success;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("_id")
    private String providerId;
    @SerializedName("is_approved")
    private int isApproved;
    @SerializedName("login_by")
    private String loginBy;
    @SerializedName("wallet_currency_code")
    private String walletCurrencyCode;
    @SerializedName("referral_code")
    private String referralCode;

    public Country getCountryDetail() {
        return countryDetail;
    }

    public void setCountryDetail(Country countryDetail) {
        this.countryDetail = countryDetail;
    }

    public String getWalletCurrencyCode() {
        return walletCurrencyCode;
    }

    public void setWalletCurrencyCode(String walletCurrencyCode) {
        this.walletCurrencyCode = walletCurrencyCode;
    }

    public List<String> getSocialIds() {
        return socialIds;
    }

    public void setSocialIds(List<String> socialIds) {
        this.socialIds = socialIds;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public int getIsPartnerApprovedByAdmin() {
        return isPartnerApprovedByAdmin;
    }

    public void setIsPartnerApprovedByAdmin(int isPartnerApprovedByAdmin) {
        this.isPartnerApprovedByAdmin = isPartnerApprovedByAdmin;
    }

    public String getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(String providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    public int getProviderType() {
        return providerType;
    }

    public void setProviderType(int providerType) {
        this.providerType = providerType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
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

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(String socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ProviderData{" + "is_partner_approved_by_admin = '" + isPartnerApprovedByAdmin + '\'' + ",provider_type_id = '" + providerTypeId + '\'' + ",provider_type = '" + providerType + '\'' + ",country = '" + country + '\'' + ",partner_email = '" + partnerEmail + '\'' + ",gender = '" + gender + '\'' + ",app_version = '" + appVersion + '\'' + ",city = '" + city + '\'' + ",device_timezone = '" + deviceTimezone + '\'' + ",is_document_uploaded = '" + isDocumentUploaded + '\'' + ",bio = '" + bio + '\'' + ",device_type = '" + deviceType + '\'' + ",country_phone_code = '" + countryPhoneCode + '\'' + ",social_unique_id = '" + socialUniqueId + '\'' + ",first_name = '" + firstName + '\'' + ",email = '" + email + '\'' + ",address = '" + address + '\'' + ",is_active = '" + isActive + '\'' + ",last_name = '" + lastName + '\'' + ",message = '" + message + '\'' + ",picture = '" + picture + '\'' + ",is_available = '" + isAvailable + '\'' + ",token = '" + token + '\'' + ",zipcode = '" + zipcode + '\'' + ",phone = '" + phone + '\'' + ",success = '" + success + '\'' + ",device_token = '" + deviceToken + '\'' + ",provider_id = '" + providerId + '\'' + ",is_approved = '" + isApproved + '\'' + ",login_by = '" + loginBy + '\'' + "}";
    }

    public int getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(int isReferral) {
        this.isReferral = isReferral;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}