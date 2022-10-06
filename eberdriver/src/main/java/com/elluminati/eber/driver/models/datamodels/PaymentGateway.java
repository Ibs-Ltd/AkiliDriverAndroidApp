package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class PaymentGateway {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PaymentGateway{" + "name = '" + name + '\'' + ",id = '" + id + '\'' + "}";
    }
}