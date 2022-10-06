package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elluminati.eber.driver.R;


/**
 * Created by elluminati on 22-07-2016.
 */
public abstract class CustomDialogNotification extends Dialog implements View.OnClickListener {

    private final MyFontTextView tvNotifyMessage;
    private final MyFontButton btnClose;


    public CustomDialogNotification(Context context, String notifyMessage) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notification_window);
        tvNotifyMessage = findViewById(R.id.tvNotifyMessage);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);
        tvNotifyMessage.setText(notifyMessage);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        getWindow().setDimAmount(0);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        this.setCancelable(false);
    }

    public void notifyDataSetChange(String message) {
        tvNotifyMessage.setText(message);
    }

    @Override
    public void onClick(View v) {
        doWithClose();
    }

    public abstract void doWithClose();
}
