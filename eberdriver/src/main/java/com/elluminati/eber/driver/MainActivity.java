package com.elluminati.eber.driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.elluminati.eber.driver.components.CustomLanguageDialog;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.utils.SocketHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends BaseAppCompatActivity {

    private MyFontTextView btnSignIn, btnRegister, tvVersion;
    private CustomLanguageDialog customLanguageDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SocketHelper.getInstance().socketDisconnect();
        stopService(new Intent(this, EberUpdateService.class));
        tvVersion = findViewById(R.id.tvVersion);
        btnSignIn = findViewById(R.id.tvSingIn);
        btnRegister = findViewById(R.id.tvRegister);
        btnRegister.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        findViewById(R.id.tvChangeLanguage).setOnClickListener(this);
        checkPlayServices();
        tvVersion.setText(getResources().getString(R.string.text_version) + " " + getAppVersion());
    }

    private void checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 12).show();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSingIn:
                goToSignInActivity();
                break;
            case R.id.tvRegister:
                goToRegisterActivity();
                break;
            case R.id.tvChangeLanguage:
                openLanguageDialog();
            default:

                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
        setAdminApprovedListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogInternet();
        } else {
            openInternetDialog();
        }
    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogGps();
        } else {
            openGpsDialog();

        }
    }

    private void openLanguageDialog() {
        if (customLanguageDialog != null && customLanguageDialog.isShowing()) {
            return;
        }
        customLanguageDialog = new CustomLanguageDialog(MainActivity.this) {
            @Override
            public void onSelect(String languageName, String languageCode) {
                //  tvLanguage.setText(languageName);
                if (!TextUtils.equals(preferenceHelper.getLanguageCode(), languageCode)) {
                    preferenceHelper.putLanguageCode(languageCode);
                    finishAffinity();
                    restartApp();
                }
                dismiss();
            }
        };
        customLanguageDialog.show();
    }

    @Override
    public void onAdminApproved() {
        goWithAdminApproved();
    }

    @Override
    public void onAdminDeclined() {
        goWithAdminDecline();
    }
}


