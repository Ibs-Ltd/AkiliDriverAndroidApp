package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class ProviderEarning {
    @SerializedName("total_transferred_amount")
    private double totalTransferredAmount;
    @SerializedName("remaining_amount_to_paid")
    private double remainingAmountToPaid;
    @SerializedName("total_admin_paid")
    private double totalAdminPaid;
    @SerializedName("total")
    private double total;
    @SerializedName("total_toll_amount")
    private double totalTollAmount;
    @SerializedName("total_tip_amount")
    private double totalTipAmount;
    @SerializedName("total_provider_have_cash")
    private double totalProviderHaveCash;
    @SerializedName("total_added_wallet_amount")
    private double totalAddedWalletAmount;
    @SerializedName("total_pay_to_provider")
    private double totalPayToProvider;
    @SerializedName("service_total")
    private double serviceTotal;
    @SerializedName("total_provider_miscellaneous_fees")
    private double totalProviderMiscellaneousFees;
    @SerializedName("total_provider_service_fees")
    private double totalProviderServiceFees;
    @SerializedName("total_waiting_time")
    private double totalWaitingTime;
    @SerializedName("total_service_surge_fees")
    private double totalServiceSurgeFees;
    @SerializedName("unit")
    private int unit;
    @SerializedName("total_paid_in_wallet_payment")
    private double totalPaidInWalletPayment;
    @SerializedName("total_deduct_wallet_amount")
    private double totalDeductWalletAmount;
    @SerializedName("total_distance")
    private double totalDistance;
    @SerializedName("currency")
    private String currency;
    @SerializedName("_id")
    private Object id;
    @SerializedName("total_time")
    private int totalTime;
    @SerializedName("statement_number")
    private String statementNumber;
    @SerializedName("total_provider_tax_fees")
    private double totalProviderTaxFees;

    public double getTotalTransferredAmount() {
        return totalTransferredAmount;
    }

    public void setTotalTransferredAmount(double totalTransferredAmount) {
        this.totalTransferredAmount = totalTransferredAmount;
    }

    public double getRemainingAmountToPaid() {
        return remainingAmountToPaid;
    }

    public void setRemainingAmountToPaid(double remainingAmountToPaid) {
        this.remainingAmountToPaid = remainingAmountToPaid;
    }

    public double getTotalAdminPaid() {
        return totalAdminPaid;
    }

    public void setTotalAdminPaid(double totalAdminPaid) {
        this.totalAdminPaid = totalAdminPaid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalTollAmount() {
        return totalTollAmount;
    }

    public void setTotalTollAmount(double totalTollAmount) {
        this.totalTollAmount = totalTollAmount;
    }

    public double getTotalTipAmount() {
        return totalTipAmount;
    }

    public void setTotalTipAmount(double totalTipAmount) {
        this.totalTipAmount = totalTipAmount;
    }

    public double getTotalProviderHaveCash() {
        return totalProviderHaveCash;
    }

    public void setTotalProviderHaveCash(double totalProviderHaveCash) {
        this.totalProviderHaveCash = totalProviderHaveCash;
    }

    public double getTotalAddedWalletAmount() {
        return totalAddedWalletAmount;
    }

    public void setTotalAddedWalletAmount(double totalAddedWalletAmount) {
        this.totalAddedWalletAmount = totalAddedWalletAmount;
    }

    public double getTotalPayToProvider() {
        return totalPayToProvider;
    }

    public void setTotalPayToProvider(double totalPayToProvider) {
        this.totalPayToProvider = totalPayToProvider;
    }

    public double getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(double serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public double getTotalProviderMiscellaneousFees() {
        return totalProviderMiscellaneousFees;
    }

    public void setTotalProviderMiscellaneousFees(double totalProviderMiscellaneousFees) {
        this.totalProviderMiscellaneousFees = totalProviderMiscellaneousFees;
    }

    public double getTotalProviderServiceFees() {
        return totalProviderServiceFees;
    }

    public void setTotalProviderServiceFees(double totalProviderServiceFees) {
        this.totalProviderServiceFees = totalProviderServiceFees;
    }

    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(double totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public double getTotalServiceSurgeFees() {
        return totalServiceSurgeFees;
    }

    public void setTotalServiceSurgeFees(double totalServiceSurgeFees) {
        this.totalServiceSurgeFees = totalServiceSurgeFees;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getTotalPaidInWalletPayment() {
        return totalPaidInWalletPayment;
    }

    public void setTotalPaidInWalletPayment(double totalPaidInWalletPayment) {
        this.totalPaidInWalletPayment = totalPaidInWalletPayment;
    }

    public double getTotalDeductWalletAmount() {
        return totalDeductWalletAmount;
    }

    public void setTotalDeductWalletAmount(double totalDeductWalletAmount) {
        this.totalDeductWalletAmount = totalDeductWalletAmount;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }

    public double getTotalProviderTaxFees() {
        return totalProviderTaxFees;
    }

    public void setTotalProviderTaxFees(double totalProviderTaxFees) {
        this.totalProviderTaxFees = totalProviderTaxFees;
    }

    @Override
    public String toString() {
        return "ProviderEarning{" + "total_toll_amount = '" + totalTollAmount + '\'' + ",total_tip_amount = '" + totalTipAmount + '\'' + ",total_provider_have_cash = '" + totalProviderHaveCash + '\'' + ",total_added_wallet_amount = '" + totalAddedWalletAmount + '\'' + ",total_pay_to_provider = '" + totalPayToProvider + '\'' + ",service_total = '" + serviceTotal + '\'' + ",total_provider_miscellaneous_fees = '" + totalProviderMiscellaneousFees + '\'' + ",total_provider_service_fees = '" + totalProviderServiceFees + '\'' + ",total_waiting_time = '" + totalWaitingTime + '\'' + ",total_service_surge_fees = '" + totalServiceSurgeFees + '\'' + ",unit = '" + unit + '\'' + ",total_paid_in_wallet_payment = '" + totalPaidInWalletPayment + '\'' + ",total_deduct_wallet_amount = '" + totalDeductWalletAmount + '\'' + ",total_distance = '" + totalDistance + '\'' + ",currency = '" + currency + '\'' + ",_id = '" + id + '\'' + ",total_time = '" + totalTime + '\'' + ",statement_number = '" + statementNumber + '\'' + ",total_provider_tax_fees = '" + totalProviderTaxFees + '\'' + "}";
    }
}