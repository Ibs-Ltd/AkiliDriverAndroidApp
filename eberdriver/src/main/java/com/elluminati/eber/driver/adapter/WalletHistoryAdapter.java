package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.WalletHistory;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by elluminati on 07-Mar-18.
 */

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.WalletHistoryHolder> {
    private final ArrayList<WalletHistory> walletHistories;
    private final Context context;

    public WalletHistoryAdapter(Context context, ArrayList<WalletHistory> walletHistories) {
        this.walletHistories = walletHistories;
        this.context = context;
    }

    @Override
    public WalletHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_history, parent, false);
        return new WalletHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(WalletHistoryHolder holder, int position) {

        WalletHistory walletHistory = walletHistories.get(position);
        try {
            ParseContent.getInstance().webFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = ParseContent.getInstance().webFormat.parse(walletHistory.getCreatedAt());
            holder.tvTransactionDate.setText(ParseContent.getInstance().dailyEarningDateFormat.format(date));
            holder.tvTransactionTime.setText(ParseContent.getInstance().timeFormat_am.format(date));
            holder.tvWithdrawalId.setText(context.getResources().getString(R.string.text_id) + " " + walletHistory.getUniqueId());

            holder.tvTransactionState.setText(walletComment(walletHistory.getWalletCommentId()));
            switch (walletHistory.getWalletStatus()) {
                case Const.Wallet.ADD_WALLET_AMOUNT:
                    holder.ivWalletStatus.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.color_app_wallet_added, null));
                    holder.tvTransactionAmount.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.color_app_wallet_added, null));
                    holder.tvTransactionAmount.setText("+" + ParseContent.getInstance().twoDigitDecimalFormat.format(walletHistory.getAddedWallet()) + " " + walletHistory.getToCurrencyCode());
                    break;
                case Const.Wallet.REMOVE_WALLET_AMOUNT:
                    holder.ivWalletStatus.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.color_app_wallet_deduct, null));
                    holder.tvTransactionAmount.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.color_app_wallet_deduct, null));
                    holder.tvTransactionAmount.setText("-" + ParseContent.getInstance().twoDigitDecimalFormat.format(walletHistory.getAddedWallet()) + " " + walletHistory.getFromCurrencyCode());

                    break;


            }
        } catch (ParseException e) {
            AppLog.handleException(WalletHistoryAdapter.class.getName(), e);
        }

    }

    @Override
    public int getItemCount() {
        return walletHistories.size();
    }

    private String walletComment(int id) {
        String comment;
        switch (id) {
            case Const.Wallet.ADDED_BY_ADMIN:
                comment = context.getResources().getString(R.string.text_wallet_status_added_by_admin);
                break;
            case Const.Wallet.ADDED_BY_CARD:
                comment = context.getResources().getString(R.string.text_wallet_status_added_by_card);
                break;
            case Const.Wallet.ADDED_BY_REFERRAL:
                comment = context.getResources().getString(R.string.text_wallet_status_added_by_referral);
                break;
            case Const.Wallet.ORDER_PROFIT:
                comment = context.getResources().getString(R.string.text_wallet_status_order_profit);
                break;
            default:
                comment = "NA";
                break;
        }
        return comment;
    }

    protected class WalletHistoryHolder extends RecyclerView.ViewHolder {

        MyAppTitleFontTextView tvTransactionState, tvTransactionAmount;
        MyFontTextView tvTransactionDate, tvWithdrawalId, tvTransactionTime;
        View ivWalletStatus;


        public WalletHistoryHolder(View itemView) {
            super(itemView);
            tvWithdrawalId = itemView.findViewById(R.id.tvWithdrawalID);
            tvTransactionAmount = itemView.findViewById(R.id.tvTransactionAmount);
            tvTransactionDate = itemView.findViewById(R.id.tvTransactionDate);
            tvTransactionState = itemView.findViewById(R.id.tvTransactionState);
            tvTransactionTime = itemView.findViewById(R.id.tvTransactionTime);
            ivWalletStatus = itemView.findViewById(R.id.ivWalletStatus);
        }
    }
}
