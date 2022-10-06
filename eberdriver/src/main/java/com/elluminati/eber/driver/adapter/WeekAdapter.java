package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.WeekData;
import com.elluminati.eber.driver.parse.ParseContent;

import java.util.ArrayList;

/**
 * Created by elluminati on 21-Aug-17.
 */

public abstract class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.MyViewHolder> {

    private final ArrayList<WeekData> listWeekData;
    private final ParseContent parseContent;
    private final Context context;

    public WeekAdapter(ArrayList<WeekData> listWeekData, Context context) {
        this.listWeekData = listWeekData;
        this.context = context;
        parseContent = ParseContent.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final WeekData weekData = listWeekData.get(position);
        holder.tvWeekStartDate.setText(parseContent.dailyEarningDateFormat.format(weekData.getListWeekDate().get(0)));
        holder.tvWeekEndDate.setText(parseContent.dailyEarningDateFormat.format(weekData.getListWeekDate().get(1)));

        if (weekData.isSelected) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.llWeekMain.setBackgroundColor(context.getResources().getColor(R.color.color_app_gray_light, context.getTheme()));
            } else {
                holder.llWeekMain.setBackgroundColor(ContextCompat.getColor(context, R.color.color_app_gray_light));
            }

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.llWeekMain.setBackgroundColor(context.getResources().getColor(R.color.color_app_theme_bg, context.getTheme()));
            } else {
                holder.llWeekMain.setBackgroundColor(ContextCompat.getColor(context, R.color.color_app_theme_bg));
            }
        }

        holder.llWeekMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWeekSelected(weekData);
                selectWeek(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listWeekData.size();
    }

    private void selectWeek(int position) {
        for (int i = 0; i < listWeekData.size(); i++) {
            listWeekData.get(i).isSelected = i == position;
        }
        notifyDataSetChanged();
    }

    public abstract void onWeekSelected(WeekData weekData);

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final MyFontTextView tvWeekStartDate;
        private final MyFontTextView tvWeekEndDate;
        private final LinearLayout llWeekMain;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvWeekStartDate = itemView.findViewById(R.id.tvWeekStartDate);
            tvWeekEndDate = itemView.findViewById(R.id.tvWeekEndDate);
            llWeekMain = itemView.findViewById(R.id.llWeekMain);
        }
    }
}
