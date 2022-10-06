package com.elluminati.eber.driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.elluminati.eber.driver.components.CustomDialogNotification;
import com.elluminati.eber.driver.models.responsemodels.CardsResponse;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentAuthConfig;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.SetupIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmSetupIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.SetupIntent;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends BaseAppCompatActivity {
    private EditText etCardHolderName;
    private CardMultilineWidget stripeCard;
    private Stripe stripe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_cards));
        etCardHolderName = findViewById(R.id.etCardHolderName);
        stripeCard = findViewById(R.id.stripeCard);
        setToolbarIcon(AppCompatResources.getDrawable(this, R.drawable.ic_done_black_24dp), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    if (stripe != null) {
                        saveCreditCard();
                    } else {
                        CustomDialogNotification customDialogNotification = new CustomDialogNotification(AddCardActivity.this, getString(R.string.msg_error_stripe)) {
                            @Override
                            public void doWithClose() {
                                finishAffinity();
                            }
                        };
                        customDialogNotification.show();
                    }
                }


            }
        });
        if (!TextUtils.isEmpty(preferenceHelper.getStripePublicKey())) {
            initStripePayment();
        }
    }

    private void initStripePayment() {
        final PaymentAuthConfig.Stripe3ds2UiCustomization uiCustomization = new PaymentAuthConfig.Stripe3ds2UiCustomization.Builder().build();
        PaymentAuthConfig.init(new PaymentAuthConfig.Builder().set3ds2Config(new PaymentAuthConfig.Stripe3ds2Config.Builder()
                // set a 5 minute timeout for challenge flow
                .setTimeout(5)
                // customize the UI of the challenge flow
                .setUiCustomization(uiCustomization).build()).build());

        PaymentConfiguration.init(this, preferenceHelper.getStripePublicKey());
        stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());
    }

    @Override
    protected boolean isValidate() {
        String msg = null;
        if (etCardHolderName.getText().toString().isEmpty()) {
            msg = getString(R.string.msg_please_enter_valid_name);
            etCardHolderName.requestFocus();
        } else if (!stripeCard.validateAllFields()) {
            msg = getString(R.string.msg_card_invalid);
        }

        if (msg != null) {
            Utils.showToast(msg, this);
            return false;

        }

        return true;

    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdminApprovedListener(this);
        setConnectivityListener(this);
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


    private void saveCreditCard() {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_add_card), false, null);
        Call<CardsResponse> call = ApiClient.getClient().create(ApiInterface.class).getStripSetupIntent(new HashMap<>());
        call.enqueue(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {
                    PaymentMethodCreateParams createPaymentParams = PaymentMethodCreateParams.create(stripeCard.getPaymentMethodCard(), new PaymentMethod.BillingDetails.Builder().setName(preferenceHelper.getFirstName() + " " + preferenceHelper.getLastName()).setEmail(preferenceHelper.getEmail()).setPhone(preferenceHelper.getCountryPhoneCode() + preferenceHelper.getContact()).build());
                    stripe.confirmSetupIntent(AddCardActivity.this, ConfirmSetupIntentParams.create(createPaymentParams, response.body().getClientSecret()));
                } else {
                    Utils.hideCustomProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<CardsResponse> call, Throwable t) {
                Utils.hideCustomProgressDialog();
                AppLog.handleThrowable(PaymentActivity.class.getSimpleName(), t);
            }
        });

    }

    private void addCard(String paymentMethod) {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_add_card), false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER_UNIQUE_NUMBER);
            jsonObject.put(Const.Params.PAYMENT_METHOD, paymentMethod);
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.USER_ID, preferenceHelper.getProviderId());

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).addCard(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (response.body().isSuccess()) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), AddCardActivity
                                    .this);
                        }
                    }


                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(PaymentActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (stripe != null) {
            stripe.onSetupResult(requestCode, data, new ApiResultCallback<SetupIntentResult>() {
                @Override
                public void onSuccess(@NonNull SetupIntentResult result) {
                    final SetupIntent setupIntent = result.getIntent();
                    final SetupIntent.Status status = setupIntent.getStatus();
                    if (status == SetupIntent.Status.Succeeded) {
                        addCard(setupIntent.getPaymentMethodId());
                    } else if (status == SetupIntent.Status.Canceled) {
                        Utils.hideCustomProgressDialog();
                        Utils.showToast(getString(R.string.error_payment_cancel), AddCardActivity.this);
                    } else if (status == SetupIntent.Status.Processing) {
                        Utils.hideCustomProgressDialog();
                        Utils.showToast(getString(R.string.error_payment_processing), AddCardActivity.this);
                    } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                        Utils.hideCustomProgressDialog();
                        Utils.showToast(getString(R.string.error_payment_auth), AddCardActivity.this);
                    }
                }

                @Override
                public void onError(@NonNull Exception e) {
                    Utils.hideCustomProgressDialog();
                    Utils.showToast(e.getMessage(), AddCardActivity.this);
                }
            });
        }

    }

}
