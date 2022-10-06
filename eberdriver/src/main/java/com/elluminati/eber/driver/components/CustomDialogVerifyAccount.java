package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.elluminati.eber.driver.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_NONE;


/**
 * Created by elluminati on 16-09-2016.
 */
public abstract class CustomDialogVerifyAccount extends Dialog implements View.OnClickListener, TextView.OnEditorActionListener {
    private final MyFontButton btnDisablePassword;
    private final MyFontButton btnEnablePassword;
    private final MyAppTitleFontTextView tvDialogMessageEnable;
    private final MyFontTextView tvProfileForgotPassword;
    private final TextInputLayout tilPassword;
    private EditText etCurrentPassword;


    public CustomDialogVerifyAccount(Context context, String title, String positiveBtnLabel, String negativeBtnLabel, String hintText, boolean isTextVisible) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_verify_account);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        btnDisablePassword = findViewById(R.id.btnDisablePassword);
        btnEnablePassword = findViewById(R.id.btnEnablePassword);
        tvDialogMessageEnable = findViewById(R.id.tvDialogMessageEnable);
        tilPassword = findViewById(R.id.tilPassword);
        tvDialogMessageEnable.setText(title);
        btnDisablePassword.setOnClickListener(this);
        btnEnablePassword.setOnClickListener(this);
        btnDisablePassword.setText(negativeBtnLabel);
        btnEnablePassword.setText(positiveBtnLabel);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etCurrentPassword.setOnEditorActionListener(this);
        etCurrentPassword.setHint(hintText);
        tvProfileForgotPassword = findViewById(R.id.tvProfileForgotPassword);
        tvProfileForgotPassword.setOnClickListener(this);
        if (isTextVisible) {
            tilPassword.setEndIconMode(END_ICON_NONE);
            etCurrentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().setDimAmount(0);
        setCancelable(false);
    }


    public abstract void doWithEnable(EditText editText);

    public abstract void doWithDisable();

    public abstract void clickOnText();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDisablePassword:
                doWithDisable();
                break;

            case R.id.btnEnablePassword:
                doWithEnable(etCurrentPassword);
                break;
            case R.id.tvProfileForgotPassword:
                clickOnText();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.etCurrentPassword:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doWithEnable(etCurrentPassword);
                    return true;
                }
                break;
        }
        return false;
    }

    public void setInputTypeNumber() {
        etCurrentPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }
}
