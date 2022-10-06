package com.elluminati.eber.driver.models.datamodels;

/**
 * Created by elluminati on 21-Aug-17.
 */

public class DailyEarning {

    private String date;
    private String amount;
    private String currency;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
