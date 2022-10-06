package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;


/**
 * Created by elluminati on 20-Jun-17.
 */

public class LanguageAdaptor extends RecyclerView.Adapter<LanguageAdaptor.LanguageViewHolder> {
    private final TypedArray langName;


    public LanguageAdaptor(Context context) {
        langName = context.getResources().obtainTypedArray(R.array.language_name);
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_name, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        holder.tvCityName.setText(langName.getString(position));
    }

    @Override
    public int getItemCount() {
        return langName.length();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView tvCityName;

        LanguageViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvItemCityName);
        }

    }


}
