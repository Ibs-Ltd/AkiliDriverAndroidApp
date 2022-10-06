package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elluminati.eber.driver.R;


/**
 * Created by elluminati on 22-07-2016.
 * <p/>
 * this dialog is ued to confirmation of currentTrip ,and this dialog is show label and message
 * with two
 * button.
 */
public abstract class CustomDialogBigLabel extends Dialog implements View.OnClickListener {
    private final MyFontTextView tvDialogMessage;
    private final MyAppTitleFontTextView tvDialogLabel;
    public MyFontButton btnYes, btnNo;

    public CustomDialogBigLabel(Context context, String dialogLabel, String dialogMessage, String positiveBtnLabel, String negativeBtnLabel) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_big_label);
        tvDialogLabel = findViewById(R.id.tvDialogLabel);
        tvDialogMessage = findViewById(R.id.tvDialogMessage);
        btnYes = findViewById(R.id.btnYes);
        btnYes.setText(positiveBtnLabel);
        btnNo = findViewById(R.id.btnNo);
        btnNo.setText(negativeBtnLabel);
        btnNo.setOnClickListener(this);
        btnYes.setOnClickListener(this);
        tvDialogLabel.setText(dialogLabel);
        tvDialogMessage.setText(dialogMessage);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().setDimAmount(0);
        this.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                positiveButton();
                break;
            case R.id.btnNo:
                negativeButton();
                break;

            default:
                break;
        }
    }

    public abstract void positiveButton();

    public abstract void negativeButton();
}
