package com.elluminati.eber.driver;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.adapter.ChatAdapter;
import com.elluminati.eber.driver.models.datamodels.Message;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChatActivity extends BaseAppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ChatAdapter chatAdapter;
    private RecyclerView rcvChat;
    private DatabaseReference firebaseDatabaseReference;
    private Button btnSend;
    private EditText messageEditText;
    private String MESSAGES_CHILD = "Demo";
    private SimpleDateFormat webFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_chat_to_user) + " " + CurrentTrip.getInstance().getUserFirstName() + " " + CurrentTrip.getInstance().getUserLastName());
        initFirebaseChat();
        rcvChat = findViewById(R.id.rcvChat);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnSend.setEnabled(false);
        btnSend.setAlpha(0.5f);
        messageEditText = findViewById(R.id.messageEditText);
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    btnSend.setEnabled(true);
                    btnSend.setAlpha(1f);
                } else {
                    btnSend.setEnabled(false);
                    btnSend.setAlpha(0.5f);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        initChatRcv();

    }


    @Override
    public void onPause() {
        if (chatAdapter != null) {
            chatAdapter.stopListening();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (chatAdapter != null) {
            chatAdapter.startListening();
        }
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                sendMessage();
                break;
            default:
                break;
        }

    }

    @Override
    public void onAdminApproved() {

    }

    @Override
    public void onAdminDeclined() {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {

    }


    private void initChatRcv() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        SnapshotParser<Message> parser = new SnapshotParser<Message>() {
            @Override
            public Message parseSnapshot(DataSnapshot dataSnapshot) {
                Message chatMessage = dataSnapshot.getValue(Message.class);
                return chatMessage;
            }
        };
        DatabaseReference messagesRef = firebaseDatabaseReference.child(MESSAGES_CHILD);
        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>().setQuery(messagesRef, parser).build();

        chatAdapter = new ChatAdapter(this, options);
        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = chatAdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    rcvChat.scrollToPosition(positionStart);
                } else {
                    rcvChat.scrollToPosition(friendlyMessageCount - 1);
                }
            }
        });
        rcvChat.setLayoutManager(linearLayoutManager);
        rcvChat.setAdapter(chatAdapter);


    }

    private void initFirebaseChat() {
        webFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.getDefault());
        webFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        MESSAGES_CHILD = preferenceHelper.getTripId();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Utils.showToast("Google Connection Failed", this);
    }

    public void setAsReadMessage(String id) {
        DatabaseReference chatMessage = firebaseDatabaseReference.child(MESSAGES_CHILD).child(id).child("is_read");
        chatMessage.setValue(true);
    }


    private void sendMessage() {
        if (firebaseDatabaseReference != null) {
            String key = firebaseDatabaseReference.child(MESSAGES_CHILD).push().getKey();
            if (!TextUtils.isEmpty(key)) {
                Message chatMessage = new Message(key, messageEditText.getText().toString().trim(), webFormat.format(new Date()), Const.PROVIDER_UNIQUE_NUMBER, false);
                firebaseDatabaseReference.child(MESSAGES_CHILD).child(key).setValue(chatMessage);
                messageEditText.setText("");
            }
        }
    }
}
