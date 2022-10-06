package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.TripsEarning;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.CurrencyHelper;
import com.elluminati.eber.driver.utils.PreferenceHelper;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by elluminati on 28-Jun-17.
 */

public class TripDayEarningAdaptor extends RecyclerView.Adapter<TripDayEarningAdaptor.OrderDayView> {

    private final List<TripsEarning> tripsEarnings;
    private final Context context;
    private final ParseContent parseContent;
    private final NumberFormat numberFormat;

    public TripDayEarningAdaptor(Context context, List<TripsEarning> tripsEarnings) {
        this.tripsEarnings = tripsEarnings;
        this.context = context;
        parseContent = ParseContent.getInstance();
        numberFormat = CurrencyHelper.getInstance(context).getCurrencyFormat(PreferenceHelper.getInstance(context).getCurrencyCode());
    }

    @Override
    public OrderDayView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_earning, parent, false);
        return new OrderDayView(view);
    }

    @Override
    public void onBindViewHolder(OrderDayView holder, int position) {
        TripsEarning tripsEarning = tripsEarnings.get(position);
        holder.tvTripNo.setText(String.valueOf(tripsEarning.getUniqueId()));
        holder.tvTotal.setText(numberFormat.format(tripsEarning.getTotal()));
        holder.tvProfit.setText(numberFormat.format(tripsEarning.getProviderServiceFees()));
        holder.tvCash.setText(numberFormat.format(tripsEarning.getProviderHaveCash()));
        holder.tvEarn.setText(numberFormat.format(tripsEarning.getPayToProvider()));
        holder.tvWallet.setText(numberFormat.format(tripsEarning.getProviderIncomeSetInWallet()));
        if (tripsEarning.getPaymentMode() == Const.CASH) {
            holder.tvPaymentMode.setText(context.getResources().getString(R.string.text_cash));
        } else {
            holder.tvPaymentMode.setText(context.getResources().getString(R.string.text_card));
        }


    }

    @Override
    public int getItemCount() {
        return tripsEarnings.size();
    }


    protected class OrderDayView extends RecyclerView.ViewHolder {

        MyFontTextView tvTripNo, tvPaymentMode, tvTotal, tvProfit, tvWallet, tvCash, tvEarn;

        public OrderDayView(View itemView) {
            super(itemView);
            tvTripNo = itemView.findViewById(R.id.tvTripNo);
            tvPaymentMode = itemView.findViewById(R.id.tvPaymentMode);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvProfit = itemView.findViewById(R.id.tvProfit);
            tvCash = itemView.findViewById(R.id.tvCash);
            tvWallet = itemView.findViewById(R.id.tvWallet);
            tvEarn = itemView.findViewById(R.id.tvEarn);

        }
    }
}
