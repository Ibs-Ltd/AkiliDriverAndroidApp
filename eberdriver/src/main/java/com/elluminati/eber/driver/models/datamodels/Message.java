package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    private String id;
    @SerializedName("message")
    private String message;
    @SerializedName("time")
    private String time;
    @SerializedName("type")
    private int type;
    @SerializedName("is_read")
    private boolean is_read;

    public Message() {
    }

    public Message(String id, String message, String time, int type, boolean is_read) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.type = type;
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
