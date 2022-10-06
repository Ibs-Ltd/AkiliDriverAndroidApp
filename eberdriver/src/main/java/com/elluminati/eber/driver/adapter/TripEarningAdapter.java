package com.elluminati.eber.driver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.EarningData;
import com.elluminati.eber.driver.utils.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by elluminati on 28-Jun-17.
 */

public class TripEarningAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private final ArrayList<ArrayList<EarningData>> arrayListForEarning;

    public TripEarningAdapter(ArrayList<ArrayList<EarningData>> arrayListForEarning) {
        this.arrayListForEarning = arrayListForEarning;
    }

    @Override
    public int getSectionCount() {
        return arrayListForEarning.size();
    }

    @Override
    public int getItemCount(int section) {
        return arrayListForEarning.get(section).size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {

        OrderEarningHeading heading = (OrderEarningHeading) holder;
        heading.tvEarningHeader.setText(arrayListForEarning.get(section).get(0).getTitleMain());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        OrderEarningItem item = (OrderEarningItem) holder;
        item.tvName.setText(arrayListForEarning.get(section).get(relativePosition).getTitle());
        item.tvPrice.setText(arrayListForEarning.get(section).get(relativePosition).getPrice());
        //item.tvName.setAllCaps(arrayListForEarning.size() - 1 == section);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return new OrderEarningHeading(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_earning_header, parent, false));
            case VIEW_TYPE_ITEM:
                return new OrderEarningItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_earning_item, parent, false));
            default:

                break;
        }
        return null;
    }


    protected class OrderEarningHeading extends RecyclerView.ViewHolder {
        MyAppTitleFontTextView tvEarningHeader;

        public OrderEarningHeading(View itemView) {
            super(itemView);
            tvEarningHeader = itemView.findViewById(R.id.tvEarningHeader);
        }
    }

    protected class OrderEarningItem extends RecyclerView.ViewHolder {
        MyFontTextView tvName, tvPrice;

        public OrderEarningItem(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
