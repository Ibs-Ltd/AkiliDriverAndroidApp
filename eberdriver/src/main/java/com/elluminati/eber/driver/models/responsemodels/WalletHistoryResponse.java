package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.WalletHistory;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WalletHistoryResponse {

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("success")
    private boolean success;
    @SerializedName("wallet_history")
    private List<WalletHistory> walletHistory;
    @SerializedName("message")
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<WalletHistory> getWalletHistory() {
        return walletHistory;
    }

    public void setWalletHistory(List<WalletHistory> walletHistory) {
        this.walletHistory = walletHistory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WalletHistoryResponse{" + "success = '" + success + '\'' + ",wallet_history = '" + walletHistory + '\'' + ",message = '" + message + '\'' + "}";
    }
}