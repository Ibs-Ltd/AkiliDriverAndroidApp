package com.elluminati.eber.driver.components;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.SpannedString;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.elluminati.eber.driver.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;


/**
 * Created by elluminati on 07-10-2016.
 */
public abstract class CustomDialogVerifyDetail extends BottomSheetDialog implements View.OnClickListener {
    private PinView email_otp_view, phone_otp_view;
    private final FloatingActionButton btnVerify;
    private final ImageView btnVerifyCancel;
    private final MyFontTextView tvResendCodeTime, tvphonemsg, msgEmail, tvEditMobileNumber;
    private Context context;

    public CustomDialogVerifyDetail(Context context, boolean isEmailOTP, boolean isSmsOTP, String countryCode, String contactNumber) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_verify_detail);
        this.context = context;
        email_otp_view = findViewById(R.id.email_otp_view);
        phone_otp_view = findViewById(R.id.phone_otp_view);
        btnVerify = findViewById(R.id.btnVerify);
        btnVerifyCancel = findViewById(R.id.btnVerifyCancel);
        tvphonemsg = findViewById(R.id.tvphonemsg);
        msgEmail = findViewById(R.id.msgEmail);
        tvEditMobileNumber = findViewById(R.id.tvEditMobileNumber);
        tvEditMobileNumber.setOnClickListener(this);
        tvResendCodeTime = findViewById(R.id.tvResendCodeTime);
        tvResendCodeTime.setOnClickListener(this);


        btnVerify.setOnClickListener(this);
        btnVerifyCancel.setOnClickListener(this);
        if (isEmailOTP) {
            email_otp_view.setVisibility(View.VISIBLE);
            msgEmail.setVisibility(View.VISIBLE);
        }
        if (isSmsOTP) {
            phone_otp_view.setVisibility(View.VISIBLE);
            tvphonemsg.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= 24) {
                tvphonemsg.setText(Html.fromHtml(String.format(Html.toHtml(new SpannedString(context.getResources().getText(R.string.msg_hint_otp_send_number)), Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE), countryCode, contactNumber, Html.FROM_HTML_MODE_LEGACY)));
            } else {
                tvphonemsg.setText(Html.fromHtml(String.format(Html.toHtml(new SpannedString(context.getResources().getText(R.string.msg_hint_otp_send_number))), countryCode, contactNumber)));
            }
        }

        verifiedUser();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        setCancelable(true);
    }

    private void verifiedUser() {
        phone_otp_view.setText("");
        email_otp_view.setText("");
        tvResendCodeTime.setEnabled(false);
        tvResendCodeTime.setTextColor(context.getResources().getColor(R.color.color_app_text));
        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvResendCodeTime.setText(context.getString(R.string.msg_resend_otp_time, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                tvResendCodeTime.setText(context.getString(R.string.msg_resend_code));
                tvResendCodeTime.setTextColor(context.getResources().getColor(R.color.color_app_button));
                tvResendCodeTime.setEnabled(true);
            }
        }.start();
    }


    public void notifyDataSetChange() {
        verifiedUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public abstract void doWithSubmit(PinView etEmailVerify, PinView etSMSVerify);

    public abstract void doCancel();

    public abstract void reSendCode();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerify:
                doWithSubmit(email_otp_view, phone_otp_view);
                break;
            case R.id.btnVerifyCancel:
                doCancel();
                break;
            case R.id.tvResendCodeTime:
                reSendCode();
                break;
            case R.id.tvEditMobileNumber:
                dismiss();
                break;

        }
    }

}
