package com.elluminati.eber.driver;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.elluminati.eber.driver.components.CustomDialogVerifyAccount;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderDataResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.models.validations.Validator;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.FieldValidation;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.elluminati.eber.driver.utils.ServerConfig;
import com.elluminati.eber.driver.utils.Utils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends BaseAppCompatActivity implements TextView.OnEditorActionListener, GoogleApiClient.OnConnectionFailedListener {
    public GoogleSignInClient googleSignInClient;
    private MyFontEdittextView etSignInEmail, etSignInPassword;
    private TextView tvForgotPassword;
    private MyFontButton btnSignIn;
    private MyAppTitleFontTextView tvGotoRegister;
    private CustomDialogVerifyAccount dialogForgotPassword;
    private CallbackManager callbackManager;
    private String socialId, socialEmail;
    private AccessTokenTracker accessTokenTracker;
    private TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        CurrentTrip.getInstance().clear();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        btnSignIn = findViewById(R.id.btnSignIn);
        tilPassword = findViewById(R.id.tilPassword);
        btnSignIn.setOnClickListener(this);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(this);

        etSignInEmail = findViewById(R.id.etSignInEmail);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        etSignInPassword.setOnEditorActionListener(this);

        tvGotoRegister = findViewById(R.id.tvGoRegister);
        tvGotoRegister.setOnClickListener(this);

        initFBLogin();
        initGoogleLogin();

        etSignInPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.MULTIPLY);
            }
        });
        findViewById(R.id.llSocialLogin).setVisibility(preferenceHelper.getIsProviderSocialLogin() ? View.VISIBLE : View.GONE);

        if (BuildConfig.APPLICATION_ID.equalsIgnoreCase("com.elluminatiinc.taxi.driver")) {
            findViewById(R.id.ivAppLogo).setOnTouchListener(new View.OnTouchListener() {

                final Handler handler = new Handler();
                int numberOfTaps = 0;
                long lastTapTimeMs = 0;
                long touchDownMs = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchDownMs = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.removeCallbacksAndMessages(null);
                            if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                                numberOfTaps = 0;
                                lastTapTimeMs = 0;
                            }
                            if (numberOfTaps > 0
                                    && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()
                            ) {
                                numberOfTaps += 1;
                            } else {
                                numberOfTaps = 1;
                            }
                            lastTapTimeMs = System.currentTimeMillis();

                            if (numberOfTaps == 3) {
                                showServerDialog();
                            }
                    }
                    return true;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
        setAdminApprovedListener(this);
    }

    private void getFacebookProfileDetail(AccessToken accessToken) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Utils.hideCustomProgressDialog();
                try {
                    if (object.has("email")) {
                        socialEmail = object.getString("email");
                    }
                    socialId = object.getString("id");
                    LoginManager.getInstance().logOut();
                    signIn(Const.SOCIAL_FACEBOOK);

                } catch (JSONException e) {
                    AppLog.handleException(TAG, e);
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }


    /**
     * Google signIn part...
     */
    private void googleSignIn() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, Const.google.RC_SIGN_IN);
        });
    }

    private void handleGoogleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        if (task.isSuccessful()) {
            try {
                GoogleSignInAccount account = task.getResult();
                socialId = account.getId();
                socialEmail = account.getEmail();
                signIn(Const.SOCIAL_GOOGLE);
            } catch (Exception e) {
                AppLog.handleException(TAG, e);
            }
        } else {
            Utils.showToast(getString(R.string.message_can_not_signin_google), SignInActivity.this);
        }
    }


    private void signIn(String loginType) {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_sing_in), false, null);
        JSONObject jsonObject = new JSONObject();


        try {
            if (loginType.equalsIgnoreCase(Const.MANUAL)) {
                jsonObject.put(Const.Params.PASSWORD, etSignInPassword.getText().toString());
                jsonObject.put(Const.Params.EMAIL, etSignInEmail.getText().toString());
                FirebaseAuth.getInstance().signOut();
            } else {
                jsonObject.put(Const.Params.PASSWORD, "");
                jsonObject.put(Const.Params.SOCIAL_UNIQUE_ID, socialId);
                jsonObject.put(Const.Params.EMAIL, socialEmail);
            }
            jsonObject.put(Const.Params.DEVICE_TYPE, Const.DEVICE_TYPE_ANDROID);

            jsonObject.put(Const.Params.DEVICE_TOKEN, preferenceHelper.getDeviceToken());
            jsonObject.put(Const.Params.LOGIN_BY, loginType);
            jsonObject.put(Const.Params.APP_VERSION, getAppVersion());

            Call<ProviderDataResponse> call = ApiClient.getClient().create(ApiInterface.class).login(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<ProviderDataResponse>() {
                @Override
                public void onResponse(Call<ProviderDataResponse> call, Response<ProviderDataResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (parseContent.saveProviderData(response.body(), true)) {
                            CurrentTrip.getInstance().clear();
                            Utils.hideCustomProgressDialog();
                            moveWithUserSpecificPreference();
                            generateFirebaseAccessToken();
                        } else {
                            Utils.hideCustomProgressDialog();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ProviderDataResponse> call, Throwable t) {
                    AppLog.handleThrowable(SignInActivity.class.getSimpleName(), t);
                }
            });

        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                if (isValidate()) {
                    signIn(Const.MANUAL);
                }
                break;
            case R.id.tvGoRegister:
                goToRegisterActivity();
                finish();
                break;
            case R.id.tvForgotPassword:
                openForgotPasswordDialog();
                break;
            default:
                // do wti default
                break;
        }

    }


    @Override
    protected boolean isValidate() {
        String msg = null;
        Validator emailValidation = FieldValidation.isEmailValid(SignInActivity.this, etSignInEmail.getText().toString().trim());
        Validator passwordValidation = FieldValidation.isPasswordValid(SignInActivity.this, etSignInPassword.getText().toString().trim());
        if (!emailValidation.isValid()) {
            msg = emailValidation.getErrorMsg();
            etSignInEmail.setError(msg);
            etSignInEmail.requestFocus();
        } else if (!passwordValidation.isValid()) {
            msg = passwordValidation.getErrorMsg();
            etSignInPassword.setError(msg);
            etSignInPassword.requestFocus();
            tilPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.CLEAR);
        }
        return TextUtils.isEmpty(msg);
    }

    @Override
    public void goWithBackArrow() {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void backToMainActivity() {
        Intent sigInIntent = new Intent(this, MainActivity.class);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sigInIntent);
        finishAffinity();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openForgotPasswordDialog() {
        dialogForgotPassword = new CustomDialogVerifyAccount(this, getResources().getString(R.string.text_forgot_password), getResources().getString(R.string.text_send), getResources().getString(R.string.text_cancel), getResources().getString(R.string.text_hint_enter_email), true) {
            @Override
            public void doWithEnable(EditText editText) {
                Validator emailValidation = FieldValidation.isEmailValid(SignInActivity.this, editText.getText().toString().trim());
                if (!emailValidation.isValid()) {
                    Utils.showToast(emailValidation.getErrorMsg(), SignInActivity.this);
                } else {
                    forgotPassword((editText.getText().toString()));
                }
            }

            @Override
            public void doWithDisable() {
                dismiss();
            }

            @Override
            public void clickOnText() {
            }
        };
        dialogForgotPassword.show();
    }

    private void forgotPassword(String email) {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_loading), false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.EMAIL, email);
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER);
            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).forgotPassword(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            if (dialogForgotPassword != null && dialogForgotPassword.isShowing()) {
                                dialogForgotPassword.dismiss();
                            }
                            Utils.showMessageToast(response.body().getMessage(), SignInActivity.this);
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), SignInActivity.this);
                        }
                    }
                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(SignInActivity.class.getSimpleName(), t);
                }
            });

        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.etSignInPassword:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isValidate()) {
                        signIn(Const.MANUAL);
                    }
                    return true;
                }
                break;
            default:

                break;

        }
        return false;
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

    @Override
    public void onAdminApproved() {
        goWithAdminApproved();
    }

    @Override
    public void onAdminDeclined() {
        goWithAdminDecline();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.google.RC_SIGN_IN) {
            handleGoogleSignInResult(data);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void initFBLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton faceBookLogin = findViewById(R.id.btnFbLogin);
        faceBookLogin.setPermissions(Arrays.asList("email", "public_profile"));
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, final AccessToken currentAccessToken) {
                if (currentAccessToken != null && !currentAccessToken.isExpired()) {
                    getFacebookProfileDetail(AccessToken.getCurrentAccessToken());
                }
            }
        };
        accessTokenTracker.startTracking();
    }

    private void initGoogleLogin() {
        SignInButton btnGoogleSingIn;
        btnGoogleSingIn = findViewById(R.id.btnGoogleLogin);
        btnGoogleSingIn.setSize(SignInButton.SIZE_WIDE);
        btnGoogleSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
    }

    private void showServerDialog() {
        final Dialog serverDialog = new Dialog(this);
        serverDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        serverDialog.setContentView(R.layout.dialog_server);

        RadioGroup dialogRadioGroup = serverDialog.findViewById(R.id.dialogRadioGroup);
        RadioButton rbServer1 = serverDialog.findViewById(R.id.rbServer1);
        RadioButton rbServer2 = serverDialog.findViewById(R.id.rbServer2);
        RadioButton rbServer3 = serverDialog.findViewById(R.id.rbServer3);

        switch (ServerConfig.BASE_URL) {
            case "https://eber.appemporio.net/":
                rbServer1.setChecked(true);
                break;
            case "https://staging.appemporio.net/":
                rbServer2.setChecked(true);
                break;
            case "https://eberdeveloper.appemporio.net/":
                rbServer3.setChecked(true);
                break;
        }

        serverDialog.findViewById(R.id.btnCancel).setOnClickListener(v -> serverDialog.dismiss());
        serverDialog.findViewById(R.id.btnOk).setOnClickListener(v -> {

            int id = dialogRadioGroup.getCheckedRadioButtonId();
            if (id == R.id.rbServer1) {
                ServerConfig.BASE_URL = "https://eber.appemporio.net/";
            } else if (id == R.id.rbServer2) {
                ServerConfig.BASE_URL = "https://staging.appemporio.net/";
            } else if (id == R.id.rbServer3) {
                ServerConfig.BASE_URL = "https://eberdeveloper.appemporio.net/";
            }

            serverDialog.dismiss();

            if (!ServerConfig.BASE_URL.equals(PreferenceHelper.getInstance(this).getBaseUrl())) {
                PreferenceHelper.getInstance(this).putBaseUrl(ServerConfig.BASE_URL);
                goToSplashActivity();
            }
        });
        WindowManager.LayoutParams params = serverDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        serverDialog.getWindow().setAttributes(params);
        serverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        serverDialog.setCancelable(false);
        serverDialog.show();
    }
}
