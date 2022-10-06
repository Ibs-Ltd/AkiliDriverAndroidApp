package com.elluminati.eber.driver.models.datamodels;

public class SurgeResult {
    private int isSurge;
    private double surgeMultiplier;

    public int getIsSurge() {
        return isSurge;
    }

    public void setIsSurge(int isSurge) {
        this.isSurge = isSurge;
    }

    public double getSurgeMultiplier() {
        return surgeMultiplier;
    }

    public void setSurgeMultiplier(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }
}
