package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.VehicleDetail;
import com.elluminati.eber.driver.utils.GlideApp;

import java.util.ArrayList;

import static com.elluminati.eber.driver.utils.Const.IMAGE_BASE_URL;

/**
 * Created by elluminati on 15-Sep-17.
 */

public abstract class VehicleSelectionAdapter extends RecyclerView.Adapter<VehicleSelectionAdapter.MyViewHolder> {

    private final ArrayList<VehicleDetail> listVehicle;
    private final Context context;
    private boolean isEnable = true;

    public VehicleSelectionAdapter(Context context, ArrayList<VehicleDetail> listVehicle) {

        this.context = context;
        this.listVehicle = listVehicle;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final VehicleDetail vehicleDetail = listVehicle.get(position);

        holder.tvVehicleListName.setText(vehicleDetail.getName() + " " + vehicleDetail.getModel());
        holder.tvVehicleListPlateNo.setText(vehicleDetail.getPlateNo());


        holder.rbSelectVehicle.setChecked(vehicleDetail.isIsSelected());

        if (!vehicleDetail.getIsDocumentUploaded()) {
            holder.tvExpireMsg.setText(context.getResources().getString(R.string.text_document_not_uploded));
            holder.tvExpireMsg.setVisibility(View.VISIBLE);
        } else if (vehicleDetail.isIsDocumentsExpired()) {
            holder.tvExpireMsg.setText(context.getResources().getString(R.string.text_document_expire));
            holder.tvExpireMsg.setVisibility(View.VISIBLE);
        } else {
            holder.tvExpireMsg.setVisibility(View.GONE);
        }
        GlideApp.with(context).load(IMAGE_BASE_URL + vehicleDetail.getTypeImageUrl()).fallback(R.drawable.car_placeholder).placeholder(R.drawable.car_placeholder).override(100, 100).into(holder.ivCarVehicleList);

        holder.rbSelectVehicle.setEnabled(isEnable);
        holder.rbSelectVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(vehicleDetail.getServiceType()) || vehicleDetail.isIsDocumentsExpired()) {
                    onVehicleSelect(position, vehicleDetail.getId(), false);
                    holder.rbSelectVehicle.setChecked(false);
                } else {
                    onVehicleSelect(position, vehicleDetail.getId(), true);
                }

            }
        });


        holder.llEditVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVehicleClick(vehicleDetail.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listVehicle.size();
    }

    public abstract void onVehicleSelect(int position, String vehicleId, boolean isHaveServiceTypeID);

    public abstract void onVehicleClick(String vehicleId);

    public void changeSelection(int position) {

        for (int i = 0; i < listVehicle.size(); i++) {
            listVehicle.get(i).setIsSelected(false);
        }
        listVehicle.get(position).setIsSelected(true);
        notifyDataSetChanged();
    }

    public void setVehicleChangeEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivCarVehicleList;
        private final MyFontTextView tvVehicleListName;
        private final MyFontTextView tvVehicleListPlateNo;
        private final MyFontTextView tvExpireMsg;
        private final RadioButton rbSelectVehicle;
        private final LinearLayout llEditVehicle;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivCarVehicleList = itemView.findViewById(R.id.ivCarVehicleList);
            tvVehicleListName = itemView.findViewById(R.id.tvVehicleListName);
            tvVehicleListPlateNo = itemView.findViewById(R.id.tvVehicleListPlateNo);
            rbSelectVehicle = itemView.findViewById(R.id.rbSelectVehicle);
            llEditVehicle = itemView.findViewById(R.id.llEditVehicles);
            tvExpireMsg = itemView.findViewById(R.id.tvExpireMsg);
        }
    }

}
