package com.elluminati.eber.driver.adapter;

import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.MainDrawerActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.utils.PreferenceHelper;

import java.util.ArrayList;


/**
 * Created by elluminati on 06-06-2016.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;

    private final MainDrawerActivity activity;
    private final TypedArray drawerItemTitle;
    private final TypedArray drawerItemIcon;
    private final PreferenceHelper preferenceHelper;
    private final ArrayList<String> notifyDot = new ArrayList<>();

    public DrawerAdapter(MainDrawerActivity activity) {
        drawerItemTitle = activity.getResources().obtainTypedArray(R.array.drawer_menu_item);
        drawerItemIcon = activity.getResources().obtainTypedArray(R.array.drawer_menu_icons);
        this.activity = activity;
        preferenceHelper = PreferenceHelper.getInstance(activity);
    }

    public void clearDrawerNotification(String filedName) {
        this.notifyDot.remove(filedName);
    }

    public void setDrawerNotification(String filedName) {
        this.notifyDot.add(filedName);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvDrawerItemTitle.setText(drawerItemTitle.getString(position));
        viewHolder.ivDrawerItemIcon.setImageResource(drawerItemIcon.getResourceId(position, 0));
        viewHolder.ivDot.setVisibility(notifyDot.contains(viewHolder.tvDrawerItemTitle.getText().toString()) ? View.VISIBLE : View.GONE);

        if (TextUtils.equals(activity.getResources().getString(R.string.text_bank_detail), viewHolder.tvDrawerItemTitle.getText().toString()) && notifyDot.contains(viewHolder.tvDrawerItemTitle.getText().toString())) {
            ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.height = 0;
            viewHolder.itemView.setVisibility(View.GONE);
            viewHolder.itemView.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
            layoutParams.width = activity.getResources().getDimensionPixelOffset(R.dimen.menu_img_width);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            viewHolder.itemView.setVisibility(View.VISIBLE);
            viewHolder.itemView.setLayoutParams(layoutParams);
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return drawerItemTitle.length();
    }

    /**
     * Holder for item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        MyFontTextView tvDrawerItemTitle;
        ImageView ivDrawerItemIcon, ivDot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDrawerItemTitle = itemView.findViewById(R.id.tvDrawerItemTitle);
            ivDrawerItemIcon = itemView.findViewById(R.id.ivDrawerItemIcon);
            ivDot = itemView.findViewById(R.id.ivDot);
        }
    }
}