package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Card;
import com.elluminati.eber.driver.models.datamodels.PaymentGateway;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardsResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("client_secret")
    private String clientSecret;
    @SerializedName("is_use_wallet")
    private int isUseWallet;
    @SerializedName("wallet")
    private double wallet;
    @SerializedName("payment_gateway")
    private List<PaymentGateway> paymentGateway;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("wallet_currency_code")
    private String walletCurrencyCode;
    @SerializedName("card")
    private List<Card> card;
    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("authorization_url")
    private String authorizationUrl;

    @SerializedName("required_param")
    private String requiredParam;

    @SerializedName("reference")
    private String reference;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("payment_gateway_type")
    private int paymentGatewayType;

    @SerializedName("html_form")
    private String htmlForm;

    public int getIsUseWallet() {
        return isUseWallet;
    }

    public void setIsUseWallet(int isUseWallet) {
        this.isUseWallet = isUseWallet;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWalletCurrencyCode() {
        return walletCurrencyCode;
    }

    public void setWalletCurrencyCode(String walletCurrencyCode) {
        this.walletCurrencyCode = walletCurrencyCode;
    }

    public List<Card> getCard() {
        return card;
    }

    public void setCard(List<Card> card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "CardsResponse{" + "wallet = '" + wallet + '\'' + ",payment_gateway = '" + paymentGateway + '\'' + ",success = '" + success + '\'' + ",message = '" + message + '\'' + ",wallet_currency_code = '" + walletCurrencyCode + '\'' + ",card = '" + card + '\'' + "}";
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getError() {
        return error;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getRequiredParam() {
        return requiredParam;
    }

    public String getReference() {
        return reference;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getPaymentGatewayType() {
        return paymentGatewayType;
    }

    public String getHtmlForm() {
        return htmlForm;
    }
}