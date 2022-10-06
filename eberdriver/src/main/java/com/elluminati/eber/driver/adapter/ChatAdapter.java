package com.elluminati.eber.driver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.ChatActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.models.datamodels.Message;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.Const;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChatAdapter extends FirebaseRecyclerAdapter<Message, ChatAdapter.MessageViewHolder> {


    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat webFormat;
    private final ParseContent parseContent;
    private final ChatActivity chatActivity;
    private android.content.Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatAdapter(ChatActivity chatActivity, @NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
        webFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.getDefault());
        webFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm", Locale.getDefault());
        parseContent = ParseContent.getInstance();
        this.chatActivity = chatActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {

        if (model.getType() == Const.PROVIDER_UNIQUE_NUMBER) {
            holder.llSent.setVisibility(View.VISIBLE);
            holder.llReceive.setVisibility(View.GONE);
            holder.tvSentMessage.setText(model.getMessage());
            try {
                Date date = webFormat.parse(model.getTime());
                holder.tvSentTime.setText(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (model.isIs_read()) {
                holder.tvRead.setText(R.string.text_read);
            } else {
                holder.tvRead.setText("");
            }

        } else {
            holder.llSent.setVisibility(View.GONE);
            holder.llReceive.setVisibility(View.VISIBLE);
            holder.tvReceiveMessage.setText(model.getMessage());
            try {
                Date date = parseContent.webFormat.parse(model.getTime());
                holder.tvReceiveTime.setText(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!model.isIs_read()) {
                chatActivity.setAsReadMessage(model.getId());
            }


        }


    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
    }

    protected class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView tvReceiveMessage, tvSentMessage, tvSentTime, tvReceiveTime, tvRead;
        LinearLayout llReceive, llSent;

        public MessageViewHolder(View itemView) {
            super(itemView);
            tvReceiveMessage = itemView.findViewById(R.id.tvReceiveMessage);
            tvSentMessage = itemView.findViewById(R.id.tvSentMessage);
            tvRead = itemView.findViewById(R.id.tvRead);
            tvReceiveTime = itemView.findViewById(R.id.tvReceiveTime);
            tvSentTime = itemView.findViewById(R.id.tvSentTime);
            llReceive = itemView.findViewById(R.id.llReceive);
            llSent = itemView.findViewById(R.id.llSent);
        }
    }
}
