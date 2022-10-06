package com.elluminati.eber.driver.models.datamodels;

/**
 * Created by elluminati on 27-Jun-17.
 */

public class EarningData {
    private String titleMain;
    private String title;
    private String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitleMain() {
        return titleMain;
    }

    public void setTitleMain(String titleMain) {
        this.titleMain = titleMain;
    }
}
