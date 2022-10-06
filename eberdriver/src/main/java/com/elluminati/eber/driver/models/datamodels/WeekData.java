package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by elluminati on 19-Aug-17.
 */

public class WeekData {

    public boolean isSelected;
    private ArrayList<Date> listWeekDate;
    @SerializedName("date7")
    private String date7 = "0.0";

    @SerializedName("date6")
    private String date6 = "0.0";

    @SerializedName("date5")
    private String date5 = "0.0";

    @SerializedName("date4")
    private String date4 = "0.0";

    @SerializedName("date3")
    private String date3 = "0.0";

    @SerializedName("date2")
    private String date2 = "0.0";

    @SerializedName("date1")
    private String date1 = "0.0";

    public String getDate7() {
        return date7;
    }

    public void setDate7(String date7) {
        this.date7 = date7;
    }

    public String getDate6() {
        return date6;
    }

    public void setDate6(String date6) {
        this.date6 = date6;
    }

    public String getDate5() {
        return date5;
    }

    public void setDate5(String date5) {
        this.date5 = date5;
    }

    public String getDate4() {
        return date4;
    }

    public void setDate4(String date4) {
        this.date4 = date4;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public ArrayList<Date> getListWeekDate() {
        return listWeekDate;
    }

    public void setListWeekDate(ArrayList<Date> listWeekDate) {
        this.listWeekDate = listWeekDate;
    }
}
