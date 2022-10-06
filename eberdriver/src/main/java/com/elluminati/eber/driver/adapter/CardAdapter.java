package com.elluminati.eber.driver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.PaymentActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.models.datamodels.Card;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.Const;

import java.util.ArrayList;

/**
 * Created by elluminati on 23-06-2016.
 */
public abstract class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public static final String TAG = "CardAdapter";
    private final PaymentActivity paymentActivity;
    private final ArrayList<Card> cardList;
    private final ParseContent parseContent;

    public CardAdapter(PaymentActivity paymentActivity, ArrayList<Card> cardList) {
        this.cardList = cardList;
        this.paymentActivity = paymentActivity;
        parseContent = ParseContent.getInstance();
        parseContent.getContext(paymentActivity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(paymentActivity).inflate(R.layout.item_payment_card_list, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Card card = cardList.get(position);
        String cardNo = card.getLastFour();
        String cardType = card.getCardType().toUpperCase();
        holder.iVCardNo.setText("****" + cardNo);
        holder.llCreditCard.setVisibility(View.VISIBLE);
        holder.ivSelected.setVisibility(card.getIsDefault() == Const.TRUE ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return cardList.size();

    }

    public abstract void onSelected(int position);

    public abstract void onClickRemove(int position);

    /**
     * Holder for item
     */

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView iVCardNo;
        LinearLayout llCreditCard;
        ImageView ivDelete, ivSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            iVCardNo = itemView.findViewById(R.id.tvCardNo);
            llCreditCard = itemView.findViewById(R.id.llCreditCard);
            llCreditCard.setOnClickListener(this);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivSelected = itemView.findViewById(R.id.ivSelected);
            ivDelete.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivDelete:
                    onClickRemove(getAdapterPosition());
                    break;
                case R.id.llCreditCard:
                    onSelected(getAdapterPosition());
                    break;
            }

        }

    }
}