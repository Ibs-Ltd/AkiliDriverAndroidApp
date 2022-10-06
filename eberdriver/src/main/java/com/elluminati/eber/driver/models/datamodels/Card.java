package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class Card {

    private boolean isSelectedCard;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("last_four")
    private String lastFour;
    @SerializedName("payment_token")
    private String paymentToken;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("_id")
    private String id;
    @SerializedName("type")
    private int type;
    @SerializedName("is_default")
    private int isDefault;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("card_type")
    private String cardType;


    public boolean isSelectedCard() {
        return isSelectedCard;
    }

    public void setSelectedCard(boolean selectedCard) {
        isSelectedCard = selectedCard;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "Card{" + "updated_at = '" + updatedAt + '\'' + ",user_id = '" + userId + '\'' + ",last_four = '" + lastFour + '\'' + ",__v = '" + '\'' + ",payment_token = '" + paymentToken + '\'' + ",created_at = '" + createdAt + '\'' + ",_id = '" + id + '\'' + ",type = '" + type + '\'' + ",is_default = '" + isDefault + '\'' + ",customer_id = '" + customerId + '\'' + ",card_type = '" + cardType + '\'' + "}";
    }
}