package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trip {


    @SerializedName("payment_status")
    private int paymentStatus;

    @SerializedName("is_favourite_provider")
    private boolean isFavouriteProvider;
    @SerializedName("car_rental_id")
    private String carRentalId;
    @SerializedName("provider_trip_end_time")
    private String providerTripEndTime;
    @SerializedName("total_after_referral_payment")
    private double totalAfterReferralPayment;
    @SerializedName("current_provider")
    private String currentProvider;
    @SerializedName("is_provider_invoice_show")
    private int isProviderInvoiceShow;
    @SerializedName("user_type")
    private int userType;
    @SerializedName("wallet_payment")
    private double walletPayment;
    @SerializedName("user_miscellaneous_fee")
    private double userMiscellaneousFee;
    @SerializedName("total_time")
    private int totalTime;
    @SerializedName("invoice_number")
    private String invoiceNumber;
    @SerializedName("providerLocation")
    private List<Double> providerLocation;
    @SerializedName("payment_transaction")
    private List<Object> paymentTransaction;
    @SerializedName("user_create_time")
    private String userCreateTime;
    @SerializedName("room_number")
    private String roomNumber;
    @SerializedName("fixed_price")
    private double fixedPrice;
    @SerializedName("cash_payment")
    private double cashPayment;
    @SerializedName("is_toll")
    private boolean isToll;
    @SerializedName("is_cancellation_fee")
    private int isCancellationFee;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("total_after_tax_fees")
    private double totalAfterTaxFees;
    @SerializedName("is_amount_refund")
    private boolean isAmountRefund;
    @SerializedName("provider_id")
    private String providerId;
    @SerializedName("country_id")
    private String countryId;
    @SerializedName("schedule_start_time")
    private String scheduleStartTime;
    @SerializedName("total_after_wallet_payment")
    private double totalAfterWalletPayment;
    @SerializedName("confirmed_provider")
    private String confirmedProvider;
    @SerializedName("user_last_name")
    private String userLastName;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("provider_tax_fee")
    private double providerTaxFee;
    @SerializedName("is_provider_status")
    private int isProviderStatus;
    @SerializedName("trip_type")
    private int tripType;
    @SerializedName("tax_fee")
    private double taxFee;
    @SerializedName("is_provider_rated")
    private int isProviderRated;
    @SerializedName("user_first_name")
    private String userFirstName;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("is_provider_accepted")
    private int isProviderAccepted;
    @SerializedName("surge_fee")
    private double surgeFee;
    @SerializedName("refund_amount")
    private double refundAmount;
    @SerializedName("currency")
    private String currency;
    @SerializedName("distance_cost")
    private double distanceCost;
    @SerializedName("toll_amount")
    private double tollAmount;
    @SerializedName("is_fixed_fare")
    private boolean isFixedFare;
    @SerializedName("payment_mode")
    private int paymentMode;
    @SerializedName("is_trip_completed")
    private int isTripCompleted;
    @SerializedName("is_trip_cancelled")
    private int isTripCancelled;
    @SerializedName("complete_date_tag")
    private String completeDateTag;
    @SerializedName("is_trip_end")
    private int isTripEnd;
    @SerializedName("payment_error")
    private String paymentError;
    @SerializedName("tip_amount")
    private double tipAmount;
    @SerializedName("service_type_id")
    private String serviceTypeId;
    @SerializedName("provider_type")
    private int providerType;
    @SerializedName("provider_type_id")
    private String providerTypeId;
    @SerializedName("referral_payment")
    private double referralPayment;
    @SerializedName("server_start_time_for_schedule")
    private String serverStartTimeForSchedule;
    @SerializedName("is_pending_payments")
    private int isPendingPayments;
    @SerializedName("is_transfered")
    private boolean isTransfered;
    @SerializedName("currencycode")
    private String currencycode;
    @SerializedName("total_after_promo_payment")
    private double totalAfterPromoPayment;
    @SerializedName("total_service_fees")
    private double totalServiceFees;
    @SerializedName("total_waiting_time")
    private double totalWaitingTime;
    @SerializedName("trip_service_city_type_id")
    private String tripServiceCityTypeId;
    @SerializedName("total_distance")
    private double totalDistance;
    @SerializedName("card_payment")
    private double cardPayment;
    @SerializedName("unique_id")
    private int uniqueId;
    @SerializedName("provider_service_fees")
    private double providerServiceFees;
    @SerializedName("is_tip")
    private boolean isTip;
    @SerializedName("no_of_time_send_request")
    private int noOfTimeSendRequest;
    @SerializedName("trip_type_amount")
    private double tripTypeAmount;
    @SerializedName("unit")
    private int unit;
    @SerializedName("provider_arrived_time")
    private String providerArrivedTime;
    @SerializedName("waiting_time_cost")
    private double waitingTimeCost;
    @SerializedName("_id")
    private String id;
    @SerializedName("provider_service_fees_in_admin_currency")
    private double providerServiceFeesInAdminCurrency;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("accepted_time")
    private String acceptedTime;
    @SerializedName("service_total_in_admin_currency")
    private double serviceTotalInAdminCurrency;
    @SerializedName("is_trip_cancelled_by_user")
    private int isTripCancelledByUser;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("destinationLocation")
    private List<Double> destinationLocation;
    @SerializedName("total_after_surge_fees")
    private double totalAfterSurgeFees;
    @SerializedName("base_distance_cost")
    private double baseDistanceCost;
    @SerializedName("is_schedule_trip")
    private boolean isScheduleTrip;
    @SerializedName("speed")
    private int speed;
    @SerializedName("provider_first_name")
    private String providerFirstName;
    @SerializedName("total")
    private double total;
    @SerializedName("provider_trip_start_time")
    private String providerTripStartTime;
    @SerializedName("provider_income_set_in_wallet")
    private double providerIncomeSetInWallet;
    @SerializedName("is_trip_cancelled_by_provider")
    private int isTripCancelledByProvider;
    @SerializedName("is_surge_hours")
    private int isSurgeHours;
    @SerializedName("source_address")
    private String sourceAddress;
    @SerializedName("provider_miscellaneous_fee")
    private double providerMiscellaneousFee;
    @SerializedName("time_cost")
    private double timeCost;
    @SerializedName("user_type_id")
    private Object userTypeId;
    @SerializedName("is_paid")
    private int isPaid;
    @SerializedName("floor")
    private int floor;
    @SerializedName("is_user_rated")
    private int isUserRated;
    @SerializedName("promo_payment")
    private double promoPayment;
    @SerializedName("destination_address")
    private String destinationAddress;
    @SerializedName("payment_error_message")
    private String paymentErrorMessage;
    @SerializedName("total_in_admin_currency")
    private double totalInAdminCurrency;
    @SerializedName("admin_currency")
    private String adminCurrency;
    @SerializedName("is_min_fare_used")
    private int isMinFareUsed;
    @SerializedName("provider_last_name")
    private String providerLastName;
    @SerializedName("provider_have_cash")
    private double providerHaveCash;
    @SerializedName("promo_referral_amount")
    private double promoReferralAmount;
    @SerializedName("user_tax_fee")
    private double userTaxFee;
    @SerializedName("is_user_invoice_show")
    private int isUserInvoiceShow;
    @SerializedName("is_provider_earning_set_in_wallet")
    private boolean isProviderEarningSetInWallet;
    @SerializedName("cancel_reason")
    private String cancelReason;
    @SerializedName("admin_currencycode")
    private String adminCurrencycode;
    @SerializedName("remaining_payment")
    private double remainingPayment;
    @SerializedName("sourceLocation")
    private List<Double> sourceLocation;
    @SerializedName("pay_to_provider")
    private double payToProvider;
    @SerializedName("surge_multiplier")
    private double surgeMultiplier;

    public boolean isFavouriteProvider() {
        return isFavouriteProvider;
    }

    public double getSurgeMultiplier() {
        return surgeMultiplier;
    }

    public void setSurgeMultiplier(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    public String getCarRentalId() {
        return carRentalId;
    }

    public void setCarRentalId(String carRentalId) {
        this.carRentalId = carRentalId;
    }

    public String getProviderTripEndTime() {
        return providerTripEndTime;
    }

    public void setProviderTripEndTime(String providerTripEndTime) {
        this.providerTripEndTime = providerTripEndTime;
    }

    public double getTotalAfterReferralPayment() {
        return totalAfterReferralPayment;
    }

    public void setTotalAfterReferralPayment(double totalAfterReferralPayment) {
        this.totalAfterReferralPayment = totalAfterReferralPayment;
    }

    public String getCurrentProvider() {
        return currentProvider;
    }

    public void setCurrentProvider(String currentProvider) {
        this.currentProvider = currentProvider;
    }

    public int getIsProviderInvoiceShow() {
        return isProviderInvoiceShow;
    }

    public void setIsProviderInvoiceShow(int isProviderInvoiceShow) {
        this.isProviderInvoiceShow = isProviderInvoiceShow;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public double getWalletPayment() {
        return walletPayment;
    }

    public void setWalletPayment(double walletPayment) {
        this.walletPayment = walletPayment;
    }

    public double getUserMiscellaneousFee() {
        return userMiscellaneousFee;
    }

    public void setUserMiscellaneousFee(double userMiscellaneousFee) {
        this.userMiscellaneousFee = userMiscellaneousFee;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<Double> getProviderLocation() {
        return providerLocation;
    }

    public void setProviderLocation(List<Double> providerLocation) {
        this.providerLocation = providerLocation;
    }

    public List<Object> getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(List<Object> paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public String getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public void setIsToll(boolean isToll) {
        this.isToll = isToll;
    }

    public boolean isToll() {
        return isToll;
    }

    public int getIsCancellationFee() {
        return isCancellationFee;
    }

    public void setIsCancellationFee(int isCancellationFee) {
        this.isCancellationFee = isCancellationFee;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalAfterTaxFees() {
        return totalAfterTaxFees;
    }

    public void setTotalAfterTaxFees(double totalAfterTaxFees) {
        this.totalAfterTaxFees = totalAfterTaxFees;
    }

    public void setIsAmountRefund(boolean isAmountRefund) {
        this.isAmountRefund = isAmountRefund;
    }

    public boolean isAmountRefund() {
        return isAmountRefund;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public double getTotalAfterWalletPayment() {
        return totalAfterWalletPayment;
    }

    public void setTotalAfterWalletPayment(double totalAfterWalletPayment) {
        this.totalAfterWalletPayment = totalAfterWalletPayment;
    }

    public String getConfirmedProvider() {
        return confirmedProvider;
    }

    public void setConfirmedProvider(String confirmedProvider) {
        this.confirmedProvider = confirmedProvider;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getProviderTaxFee() {
        return providerTaxFee;
    }

    public void setProviderTaxFee(double providerTaxFee) {
        this.providerTaxFee = providerTaxFee;
    }

    public int getIsProviderStatus() {
        return isProviderStatus;
    }

    public void setIsProviderStatus(int isProviderStatus) {
        this.isProviderStatus = isProviderStatus;
    }

    public int getTripType() {
        return tripType;
    }

    public void setTripType(int tripType) {
        this.tripType = tripType;
    }

    public double getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(double taxFee) {
        this.taxFee = taxFee;
    }

    public int getIsProviderRated() {
        return isProviderRated;
    }

    public void setIsProviderRated(int isProviderRated) {
        this.isProviderRated = isProviderRated;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getIsProviderAccepted() {
        return isProviderAccepted;
    }

    public void setIsProviderAccepted(int isProviderAccepted) {
        this.isProviderAccepted = isProviderAccepted;
    }

    public double getSurgeFee() {
        return surgeFee;
    }

    public void setSurgeFee(double surgeFee) {
        this.surgeFee = surgeFee;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getDistanceCost() {
        return distanceCost;
    }

    public void setDistanceCost(double distanceCost) {
        this.distanceCost = distanceCost;
    }

    public double getTollAmount() {
        return tollAmount;
    }

    public void setTollAmount(double tollAmount) {
        this.tollAmount = tollAmount;
    }

    public void setIsFixedFare(boolean isFixedFare) {
        this.isFixedFare = isFixedFare;
    }

    public boolean isFixedFare() {
        return isFixedFare;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getIsTripCompleted() {
        return isTripCompleted;
    }

    public void setIsTripCompleted(int isTripCompleted) {
        this.isTripCompleted = isTripCompleted;
    }

    public int getIsTripCancelled() {
        return isTripCancelled;
    }

    public void setIsTripCancelled(int isTripCancelled) {
        this.isTripCancelled = isTripCancelled;
    }

    public String getCompleteDateTag() {
        return completeDateTag;
    }

    public void setCompleteDateTag(String completeDateTag) {
        this.completeDateTag = completeDateTag;
    }

    public int getIsTripEnd() {
        return isTripEnd;
    }

    public void setIsTripEnd(int isTripEnd) {
        this.isTripEnd = isTripEnd;
    }

    public String getPaymentError() {
        return paymentError;
    }

    public void setPaymentError(String paymentError) {
        this.paymentError = paymentError;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public int getProviderType() {
        return providerType;
    }

    public void setProviderType(int providerType) {
        this.providerType = providerType;
    }

    public String getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(String providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    public double getReferralPayment() {
        return referralPayment;
    }

    public void setReferralPayment(double referralPayment) {
        this.referralPayment = referralPayment;
    }

    public String getServerStartTimeForSchedule() {
        return serverStartTimeForSchedule;
    }

    public void setServerStartTimeForSchedule(String serverStartTimeForSchedule) {
        this.serverStartTimeForSchedule = serverStartTimeForSchedule;
    }

    public int getIsPendingPayments() {
        return isPendingPayments;
    }

    public void setIsPendingPayments(int isPendingPayments) {
        this.isPendingPayments = isPendingPayments;
    }

    public void setIsTransfered(boolean isTransfered) {
        this.isTransfered = isTransfered;
    }

    public boolean isTransfered() {
        return isTransfered;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public double getTotalAfterPromoPayment() {
        return totalAfterPromoPayment;
    }

    public void setTotalAfterPromoPayment(double totalAfterPromoPayment) {
        this.totalAfterPromoPayment = totalAfterPromoPayment;
    }

    public double getTotalServiceFees() {
        return totalServiceFees;
    }

    public void setTotalServiceFees(double totalServiceFees) {
        this.totalServiceFees = totalServiceFees;
    }

    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(double totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public String getTripServiceCityTypeId() {
        return tripServiceCityTypeId;
    }

    public void setTripServiceCityTypeId(String tripServiceCityTypeId) {
        this.tripServiceCityTypeId = tripServiceCityTypeId;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getCardPayment() {
        return cardPayment;
    }

    public void setCardPayment(double cardPayment) {
        this.cardPayment = cardPayment;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getProviderServiceFees() {
        return providerServiceFees;
    }

    public void setProviderServiceFees(double providerServiceFees) {
        this.providerServiceFees = providerServiceFees;
    }

    public void setIsTip(boolean isTip) {
        this.isTip = isTip;
    }

    public boolean isTip() {
        return isTip;
    }

    public int getNoOfTimeSendRequest() {
        return noOfTimeSendRequest;
    }

    public void setNoOfTimeSendRequest(int noOfTimeSendRequest) {
        this.noOfTimeSendRequest = noOfTimeSendRequest;
    }

    public double getTripTypeAmount() {
        return tripTypeAmount;
    }

    public void setTripTypeAmount(int tripTypeAmount) {
        this.tripTypeAmount = tripTypeAmount;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getProviderArrivedTime() {
        return providerArrivedTime;
    }

    public void setProviderArrivedTime(String providerArrivedTime) {
        this.providerArrivedTime = providerArrivedTime;
    }

    public double getWaitingTimeCost() {
        return waitingTimeCost;
    }

    public void setWaitingTimeCost(double waitingTimeCost) {
        this.waitingTimeCost = waitingTimeCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getProviderServiceFeesInAdminCurrency() {
        return providerServiceFeesInAdminCurrency;
    }

    public void setProviderServiceFeesInAdminCurrency(double providerServiceFeesInAdminCurrency) {
        this.providerServiceFeesInAdminCurrency = providerServiceFeesInAdminCurrency;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(String acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public double getServiceTotalInAdminCurrency() {
        return serviceTotalInAdminCurrency;
    }

    public void setServiceTotalInAdminCurrency(double serviceTotalInAdminCurrency) {
        this.serviceTotalInAdminCurrency = serviceTotalInAdminCurrency;
    }

    public int getIsTripCancelledByUser() {
        return isTripCancelledByUser;
    }

    public void setIsTripCancelledByUser(int isTripCancelledByUser) {
        this.isTripCancelledByUser = isTripCancelledByUser;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<Double> getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(List<Double> destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public double getTotalAfterSurgeFees() {
        return totalAfterSurgeFees;
    }

    public void setTotalAfterSurgeFees(double totalAfterSurgeFees) {
        this.totalAfterSurgeFees = totalAfterSurgeFees;
    }

    public double getBaseDistanceCost() {
        return baseDistanceCost;
    }

    public void setBaseDistanceCost(double baseDistanceCost) {
        this.baseDistanceCost = baseDistanceCost;
    }

    public void setIsScheduleTrip(boolean isScheduleTrip) {
        this.isScheduleTrip = isScheduleTrip;
    }

    public boolean isScheduleTrip() {
        return isScheduleTrip;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        this.providerFirstName = providerFirstName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getProviderTripStartTime() {
        return providerTripStartTime;
    }

    public void setProviderTripStartTime(String providerTripStartTime) {
        this.providerTripStartTime = providerTripStartTime;
    }

    public double getProviderIncomeSetInWallet() {
        return providerIncomeSetInWallet;
    }

    public void setProviderIncomeSetInWallet(double providerIncomeSetInWallet) {
        this.providerIncomeSetInWallet = providerIncomeSetInWallet;
    }

    public int getIsTripCancelledByProvider() {
        return isTripCancelledByProvider;
    }

    public void setIsTripCancelledByProvider(int isTripCancelledByProvider) {
        this.isTripCancelledByProvider = isTripCancelledByProvider;
    }

    public int getIsSurgeHours() {
        return isSurgeHours;
    }

    public void setIsSurgeHours(int isSurgeHours) {
        this.isSurgeHours = isSurgeHours;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public double getProviderMiscellaneousFee() {
        return providerMiscellaneousFee;
    }

    public void setProviderMiscellaneousFee(double providerMiscellaneousFee) {
        this.providerMiscellaneousFee = providerMiscellaneousFee;
    }

    public double getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(double timeCost) {
        this.timeCost = timeCost;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getIsUserRated() {
        return isUserRated;
    }

    public void setIsUserRated(int isUserRated) {
        this.isUserRated = isUserRated;
    }

    public double getPromoPayment() {
        return promoPayment;
    }

    public void setPromoPayment(double promoPayment) {
        this.promoPayment = promoPayment;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getPaymentErrorMessage() {
        return paymentErrorMessage;
    }

    public void setPaymentErrorMessage(String paymentErrorMessage) {
        this.paymentErrorMessage = paymentErrorMessage;
    }

    public double getTotalInAdminCurrency() {
        return totalInAdminCurrency;
    }

    public void setTotalInAdminCurrency(double totalInAdminCurrency) {
        this.totalInAdminCurrency = totalInAdminCurrency;
    }

    public String getAdminCurrency() {
        return adminCurrency;
    }

    public void setAdminCurrency(String adminCurrency) {
        this.adminCurrency = adminCurrency;
    }

    public int getIsMinFareUsed() {
        return isMinFareUsed;
    }

    public void setIsMinFareUsed(int isMinFareUsed) {
        this.isMinFareUsed = isMinFareUsed;
    }

    public String getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }

    public double getProviderHaveCash() {
        return providerHaveCash;
    }

    public void setProviderHaveCash(double providerHaveCash) {
        this.providerHaveCash = providerHaveCash;
    }

    public double getPromoReferralAmount() {
        return promoReferralAmount;
    }

    public void setPromoReferralAmount(double promoReferralAmount) {
        this.promoReferralAmount = promoReferralAmount;
    }

    public double getUserTaxFee() {
        return userTaxFee;
    }

    public void setUserTaxFee(double userTaxFee) {
        this.userTaxFee = userTaxFee;
    }

    public int getIsUserInvoiceShow() {
        return isUserInvoiceShow;
    }

    public void setIsUserInvoiceShow(int isUserInvoiceShow) {
        this.isUserInvoiceShow = isUserInvoiceShow;
    }

    public void setIsProviderEarningSetInWallet(boolean isProviderEarningSetInWallet) {
        this.isProviderEarningSetInWallet = isProviderEarningSetInWallet;
    }

    public boolean isProviderEarningSetInWallet() {
        return isProviderEarningSetInWallet;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getAdminCurrencycode() {
        return adminCurrencycode;
    }

    public void setAdminCurrencycode(String adminCurrencycode) {
        this.adminCurrencycode = adminCurrencycode;
    }

    public double getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(double remainingPayment) {
        this.remainingPayment = remainingPayment;
    }

    public List<Double> getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(List<Double> sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public double getPayToProvider() {
        return payToProvider;
    }

    public void setPayToProvider(double payToProvider) {
        this.payToProvider = payToProvider;
    }

    @Override
    public String toString() {
        return "Trip{" + "provider_trip_end_time = '" + providerTripEndTime + '\'' + ",total_after_referral_payment = '" + totalAfterReferralPayment + '\'' + ",current_provider = '" + currentProvider + '\'' + ",is_provider_invoice_show = '" + isProviderInvoiceShow + '\'' + ",user_type = '" + userType + '\'' + ",wallet_payment = '" + walletPayment + '\'' + ",user_miscellaneous_fee = '" + userMiscellaneousFee + '\'' + ",total_time = '" + totalTime + '\'' + ",invoice_number = '" + invoiceNumber + '\'' + ",providerLocation = '" + providerLocation + '\'' + ",payment_transaction = '" + paymentTransaction + '\'' + ",user_create_time = '" + userCreateTime + '\'' + ",room_number = '" + roomNumber + '\'' + ",fixed_price = '" + fixedPrice + '\'' + ",cash_payment = '" + cashPayment + '\'' + ",is_toll = '" + isToll + '\'' + ",is_cancellation_fee = '" + isCancellationFee + '\'' + ",user_id = '" + userId + '\'' + ",total_after_tax_fees = '" + totalAfterTaxFees + '\'' + ",is_amount_refund = '" + isAmountRefund + '\'' + ",provider_id = '" + providerId + '\'' + ",country_id = '" + countryId + '\'' + ",schedule_start_time = '" + scheduleStartTime + '\'' + ",total_after_wallet_payment = '" + totalAfterWalletPayment + '\'' + ",confirmed_provider = '" + confirmedProvider + '\'' + ",user_last_name = '" + userLastName + '\'' + ",created_at = '" + createdAt + '\'' + ",provider_tax_fee = '" + providerTaxFee + '\'' + ",is_provider_status = '" + isProviderStatus + '\'' + ",trip_type = '" + tripType + '\'' + ",tax_fee = '" + taxFee + '\'' + ",is_provider_rated = '" + isProviderRated + '\'' + ",user_first_name = '" + userFirstName + '\'' + ",updated_at = '" + updatedAt + '\'' + ",is_provider_accepted = '" + isProviderAccepted + '\'' + ",surge_fee = '" + surgeFee + '\'' + ",refund_amount = '" + refundAmount + '\'' + ",currency = '" + currency + '\'' + ",distance_cost = '" + distanceCost + '\'' + ",toll_amount = '" + tollAmount + '\'' + ",is_fixed_fare = '" + isFixedFare + '\'' + ",payment_mode = '" + paymentMode + '\'' + ",is_trip_completed = '" + isTripCompleted + '\'' + ",is_trip_cancelled = '" + isTripCancelled + '\'' + ",complete_date_tag = '" + completeDateTag + '\'' + ",is_trip_end = '" + isTripEnd + '\'' + ",payment_error = '" + paymentError + '\'' + ",tip_amount = '" + tipAmount + '\'' + ",service_type_id = '" + serviceTypeId + '\'' + ",provider_type = '" + providerType + '\'' + ",provider_type_id = '" + providerTypeId + '\'' + ",referral_payment = '" + referralPayment + '\'' + ",server_start_time_for_schedule = '" + serverStartTimeForSchedule + '\'' + ",is_pending_payments = '" + isPendingPayments + '\'' + ",is_transfered = '" + isTransfered + '\'' + ",currencycode = '" + currencycode + '\'' + ",total_after_promo_payment = '" + totalAfterPromoPayment + '\'' + ",total_service_fees = '" + totalServiceFees + '\'' + ",total_waiting_time = '" + totalWaitingTime + '\'' + ",trip_service_city_type_id = '" + tripServiceCityTypeId + '\'' + ",total_distance = '" + totalDistance + '\'' + ",card_payment = '" + cardPayment + '\'' + ",unique_id = '" + uniqueId + '\'' + ",provider_service_fees = '" + providerServiceFees + '\'' + ",is_tip = '" + isTip + '\'' + ",no_of_time_send_request = '" + noOfTimeSendRequest + '\'' + ",trip_type_amount = '" + tripTypeAmount + '\'' + ",unit = '" + unit + '\'' + ",provider_arrived_time = '" + providerArrivedTime + '\'' + ",waiting_time_cost = '" + waitingTimeCost + '\'' + ",_id = '" + id + '\'' + ",provider_service_fees_in_admin_currency = '" + providerServiceFeesInAdminCurrency + '\'' + ",city_id = '" + cityId + '\'' + ",accepted_time = '" + acceptedTime + '\'' + ",service_total_in_admin_currency = '" + serviceTotalInAdminCurrency + '\'' + ",is_trip_cancelled_by_user = '" + isTripCancelledByUser + '\'' + ",timezone = '" + timezone + '\'' + ",destinationLocation = '" + destinationLocation + '\'' + ",total_after_surge_fees = '" + totalAfterSurgeFees + '\'' + ",base_distance_cost = '" + baseDistanceCost + '\'' + ",is_schedule_trip = '" + isScheduleTrip + '\'' + ",speed = '" + speed + '\'' + ",provider_first_name = '" + providerFirstName + '\'' + ",total = '" + total + '\'' + ",provider_trip_start_time = '" + providerTripStartTime + '\'' + ",provider_income_set_in_wallet = '" + providerIncomeSetInWallet + '\'' + ",is_trip_cancelled_by_provider = '" + isTripCancelledByProvider + '\'' + ",is_surge_hours = '" + isSurgeHours + '\'' + ",source_address = '" + sourceAddress + '\'' + ",provider_miscellaneous_fee = '" + providerMiscellaneousFee + '\'' + ",time_cost = '" + timeCost + '\'' + ",user_type_id = '" + userTypeId + '\'' + ",is_paid = '" + isPaid + '\'' + ",floor = '" + floor + '\'' + ",is_user_rated = '" + isUserRated + '\'' + ",promo_payment = '" + promoPayment + '\'' + ",destination_address = '" + destinationAddress + '\'' + ",payment_error_message = '" + paymentErrorMessage + '\'' + ",total_in_admin_currency = '" + totalInAdminCurrency + '\'' + ",admin_currency = '" + adminCurrency + '\'' + ",is_min_fare_used = '" + isMinFareUsed + '\'' + ",provider_last_name = '" + providerLastName + '\'' + ",provider_have_cash = '" + providerHaveCash + '\'' + ",promo_referral_amount = '" + promoReferralAmount + '\'' + ",user_tax_fee = '" + userTaxFee + '\'' + ",is_user_invoice_show = '" + isUserInvoiceShow + '\'' + ",is_provider_earning_set_in_wallet = '" + isProviderEarningSetInWallet + '\'' + ",cancel_reason = '" + cancelReason + '\'' + ",admin_currencycode = '" + adminCurrencycode + '\'' + ",remaining_payment = '" + remainingPayment + '\'' + ",sourceLocation = '" + sourceLocation + '\'' + ",pay_to_provider = '" + payToProvider + '\'' + "}";
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

}