package com.elluminati.eber.driver;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.ServerConfig;


public class PaystackPaymentActivity extends BaseAppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paystack_payment);
        initToolBar();
        String payUHtml = getIntent().getStringExtra(Const.Params.PAYU_HTML);

        WebView webViewTerms = findViewById(R.id.webview);
        webViewTerms.getSettings().setLoadsImagesAutomatically(true);
        webViewTerms.getSettings().setDisplayZoomControls(false);
        webViewTerms.getSettings().setBuiltInZoomControls(true);
        webViewTerms.getSettings().setJavaScriptEnabled(true);
        webViewTerms.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewTerms.getSettings().setDomStorageEnabled(true);
        if (TextUtils.isEmpty(payUHtml)) {
            setTitleOnToolbar(getResources().getString(R.string.text_cards));
            webViewTerms.loadUrl(getIntent().getStringExtra(Const.Params.AUTHORIZATION_URL));
        } else {
            setTitleOnToolbar(getResources().getString(R.string.text_payments));
            webViewTerms.loadDataWithBaseURL(null, payUHtml, "text/html", "utf-8", null);
        }
        webViewTerms.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AppLog.Log(TAG, url);
                if (url.contains("add_card_success")) {
                    setResult(RESULT_OK);
                    finish();
                } else if (url.contains(ServerConfig.BASE_URL + "provider_payments")) {
                    setResult(RESULT_OK);
                    finish();
                } else if (url.contains(ServerConfig.BASE_URL + "payment_fail")) {
                    setResult(RESULT_CANCELED);
                    finish();
                } else if (url.contains(ServerConfig.BASE_URL + "fail_stripe_intent_payment")) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
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
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAdminApproved() {

    }

    @Override
    public void onAdminDeclined() {

    }
}