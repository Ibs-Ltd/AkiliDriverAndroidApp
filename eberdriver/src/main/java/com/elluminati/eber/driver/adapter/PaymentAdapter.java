package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.PaymentGateway;

import java.util.ArrayList;

/**
 * Created by elluminati on 03-Jan-17.
 */
public class PaymentAdapter extends BaseAdapter {

    private final ArrayList<PaymentGateway> selectedPaymentList;
    private final LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public PaymentAdapter(Context context, ArrayList<PaymentGateway> selectedPaymentList) {
        this.selectedPaymentList = selectedPaymentList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return selectedPaymentList.size();
    }

    @Override
    public String getItem(int i) {
        return selectedPaymentList.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_payment_list, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPaymentName = view.findViewById(R.id.tvPaymentName);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvPaymentName.setText(selectedPaymentList.get(i).getName());
        return view;
    }

    private class ViewHolder {
        MyFontTextView tvPaymentName;

    }
}
