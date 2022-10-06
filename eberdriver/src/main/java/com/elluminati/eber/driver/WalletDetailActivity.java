package com.elluminati.eber.driver;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.WalletHistory;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

public class WalletDetailActivity extends BaseAppCompatActivity {

    private MyFontTextView tvTransactionDate, tvDescription, tvTransactionTime, tvWithdrawalID, tvTagCurrentRate, tvAmountTag;
    private MyAppTitleFontTextView tvAmount, tvCurrentRate, tvTotalWalletAmount;
    private WalletHistory walletHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        initToolBar();
        tvTotalWalletAmount = findViewById(R.id.tvTotalWalletAmount);
        tvAmount = findViewById(R.id.tvAmount);
        tvWithdrawalID = findViewById(R.id.tvWithdrawalID);
        tvTransactionDate = findViewById(R.id.tvTransactionDate);
        tvTransactionTime = findViewById(R.id.tvTransactionTime);
        tvCurrentRate = findViewById(R.id.tvCurrentRate);
        tvDescription = findViewById(R.id.tvDescription);
        tvTagCurrentRate = findViewById(R.id.tvTagCurrentRate);
        tvAmountTag = findViewById(R.id.tvAmountTag);
        getExtraData();
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }


    @Override
    public void onClick(View v) {

    }

    private void getExtraData() {
        if (getIntent().getExtras() != null) {
            walletHistory = getIntent().getExtras().getParcelable(Const.BUNDLE);
            setTitleOnToolbar(walletComment(walletHistory.getWalletCommentId()));
            walletStatus(walletHistory.getWalletStatus());
            tvWithdrawalID.setText(getResources().getString(R.string.text_id) + " " + walletHistory.getUniqueId());
            try {
                ParseContent.getInstance().webFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = ParseContent.getInstance().webFormat.parse(walletHistory.getCreatedAt());
                tvTransactionDate.setText(ParseContent.getInstance().dailyEarningDateFormat.format(date));
                tvTransactionTime.setText(ParseContent.getInstance().timeFormat_am.format(date));
            } catch (ParseException e) {
                AppLog.handleException(WalletDetailActivity.class.getName(), e);
            }
            tvDescription.setText(walletHistory.getWalletDescription());

            if (walletHistory.getCurrentRate() == 1.0) {
                tvTagCurrentRate.setVisibility(View.GONE);
                tvCurrentRate.setVisibility(View.GONE);
            } else {
                DecimalFormat distanceDecimalFormat = new DecimalFormat("0.0000");
                tvTagCurrentRate.setVisibility(View.VISIBLE);
                tvCurrentRate.setVisibility(View.VISIBLE);
                tvCurrentRate.setText("1" + walletHistory.getFromCurrencyCode() + " (" + distanceDecimalFormat.format(walletHistory.getCurrentRate()) + walletHistory.getToCurrencyCode() + ")");
            }
        }

    }

    private void walletStatus(int id) {
        switch (id) {
            case Const.Wallet.ADD_WALLET_AMOUNT:
                tvAmount.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_app_wallet_added, null));
                tvAmountTag.setText(getResources().getString(R.string.text_added_amount));
                tvAmount.setText("+" + parseContent.twoDigitDecimalFormat.format(walletHistory.getAddedWallet()) + " " + walletHistory.getToCurrencyCode());
                tvTotalWalletAmount.setText(parseContent.twoDigitDecimalFormat.format(walletHistory.getTotalWalletAmount()) + " " + walletHistory.getToCurrencyCode());
                break;
            case Const.Wallet.REMOVE_WALLET_AMOUNT:
                tvAmount.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_app_wallet_deduct, null));
                tvAmountTag.setText(getResources().getString(R.string.text_deducted_amount));
                tvAmount.setText("-" + parseContent.twoDigitDecimalFormat.format(walletHistory.getAddedWallet()) + " " + walletHistory.getFromCurrencyCode());
                tvTotalWalletAmount.setText(parseContent.twoDigitDecimalFormat.format(walletHistory.getTotalWalletAmount()) + " " + walletHistory.getFromCurrencyCode());
                break;


        }
    }

    private String walletComment(int id) {
        String comment;
        switch (id) {
            case Const.Wallet.ADDED_BY_ADMIN:
                comment = getResources().getString(R.string.text_wallet_status_added_by_admin);
                break;
            case Const.Wallet.ADDED_BY_CARD:
                comment = getResources().getString(R.string.text_wallet_status_added_by_card);
                break;
            case Const.Wallet.ADDED_BY_REFERRAL:
                comment = getResources().getString(R.string.text_wallet_status_added_by_referral);
                break;
            case Const.Wallet.ORDER_PROFIT:
                comment = getResources().getString(R.string.text_wallet_status_order_profit);
                break;
            default:

                comment = "NA";
                break;
        }
        return comment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onAdminApproved() {
        goWithAdminApproved();
    }

    @Override
    public void onAdminDeclined() {
        goWithAdminDecline();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogInternet();
        } else {
            openInternetDialog();
        }
    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogGps();
        } else {
            openGpsDialog();

        }
    }
}
