package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.models.datamodels.Invoice;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private final ArrayList<Invoice> invoices;
    private Context context;

    public InvoiceAdapter(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new InvoiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice invoice = invoices.get(position);
        holder.tvTitle.setText(invoice.getTitle());
        holder.tvPriceValue.setText(invoice.getPrice());
        holder.tvSubPriceValue.setText(invoice.getSubTitle());
        holder.tvDiscount.setVisibility(TextUtils.equals(invoice.getTitle(), context.getResources().getString(R.string.text_referral_bonus)) ? View.VISIBLE : View.GONE);


    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    protected class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubPriceValue, tvPriceValue, tvTitle, tvDiscount;

        public InvoiceViewHolder(View itemView) {
            super(itemView);
            tvSubPriceValue = itemView.findViewById(R.id.tvSubPriceValue);
            tvPriceValue = itemView.findViewById(R.id.tvPriceValue);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
        }
    }
}
