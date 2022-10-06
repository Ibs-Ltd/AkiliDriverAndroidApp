package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class Language {


    private boolean isSelected;
    @SerializedName("unique_id")
    private int uniqueId;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("_id")
    private String id;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Language{" + "unique_id = '" + uniqueId + '\'' + ",code = '" + code + '\'' + ",name = '" + name + '\'' + ",_id = '" + id + '\'' + "}";
    }
}