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
 * <p>
 * this custom dialog use for enable and disable internet,gps and other casual used
 * in this dialog only message is show with two button.
 */
public abstract class CustomDialogEnable extends Dialog implements View.OnClickListener {
    private final MyFontTextView tvDialogMessageEnable;
    private final MyFontButton btnEnable;
    private final MyFontButton btnDisable;

    public CustomDialogEnable(Context context, CharSequence message, String negativeBtnLabel, String positiveBtnLabel) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_enable);
        tvDialogMessageEnable = findViewById(R.id.tvDialogMessageEnable);
        btnDisable = findViewById(R.id.btnDisable);
        btnDisable.setText(negativeBtnLabel);
        btnEnable = findViewById(R.id.btnEnable);
        btnEnable.setText(positiveBtnLabel);
        tvDialogMessageEnable.setText(message);
        btnDisable.setOnClickListener(this);
        btnEnable.setOnClickListener(this);
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
            case R.id.btnEnable:
                dismiss();
                doWithEnable();
                break;
            case R.id.btnDisable:
                dismiss();
                doWithDisable();
                break;
            default:
                break;
        }

    }

    public abstract void doWithEnable();

    public abstract void doWithDisable();
}
