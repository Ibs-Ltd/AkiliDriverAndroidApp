package com.elluminati.eber.driver;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.elluminati.eber.driver.components.CustomDialogBigLabel;
import com.elluminati.eber.driver.components.CustomDialogVerifyAccount;
import com.elluminati.eber.driver.components.CustomPhotoDialog;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.Bankdetails;
import com.elluminati.eber.driver.models.responsemodels.BankDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.GlideApp;
import com.elluminati.eber.driver.utils.ImageHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailActivity extends BaseAppCompatActivity {
    private MyFontEdittextView etAccountNumber, etAccountHolderName, etRoutingNumber, etAccountHolderType, etPersonalIdNumber, etAddress, etStateCode, etPostalCode;
    private MyFontTextView tvDob;
    private String currentPassword;
    private CustomDialogVerifyAccount verifyDialog;
    private Uri picUri;
    private ImageHelper imageHelper;
    private LinearLayout llPersonalId, llDob, llAcHolder, llAddress, llStateCode, llPostalCode;
    private RadioButton rbMale;
    private TextView tvFrontDoc, tvFrontDocView, tvBackDoc, tvBackDocView, tvExtraDoc, tvExtraDocView;
    private Dialog bankDocumentDialog;
    private int paymentGatewayType = 0;
    private RadioGroup rgGender;
    private CardView cvUploadPhoto;
    private Bankdetails bankdetails;
    private TextInputLayout tilRoutingNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_bank_detail));

        imageHelper = new ImageHelper(this);
        llAcHolder = findViewById(R.id.llAcHolder);
        llAddress = findViewById(R.id.llAddress);
        llPostalCode = findViewById(R.id.llPostalCode);
        llStateCode = findViewById(R.id.llStateCode);
        rgGender = findViewById(R.id.rgGender);
        cvUploadPhoto = findViewById(R.id.cvUploadPhoto);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etAccountHolderName = findViewById(R.id.etAccountHolderName);
        etRoutingNumber = findViewById(R.id.etRoutingNumber);
        tilRoutingNumber = findViewById(R.id.tilRoutingNumber);
        etAccountHolderType = findViewById(R.id.etAccountHolderType);
        etPersonalIdNumber = findViewById(R.id.etPersonalIdNumber);
        etAddress = findViewById(R.id.etAddress);
        etStateCode = findViewById(R.id.etStateCode);
        etPostalCode = findViewById(R.id.etPostalCode);
        llPersonalId = findViewById(R.id.llPersonalId);
        tvFrontDoc = findViewById(R.id.tvFrontDoc);
        tvFrontDocView = findViewById(R.id.tvFrontDocView);
        tvBackDoc = findViewById(R.id.tvBackDoc);
        tvBackDocView = findViewById(R.id.tvBackDocView);
        tvExtraDoc = findViewById(R.id.tvExtraDoc);
        tvExtraDocView = findViewById(R.id.tvExtraDocView);
        tvFrontDoc.setOnClickListener(this);
        tvFrontDocView.setOnClickListener(this);
        tvBackDoc.setOnClickListener(this);
        tvBackDocView.setOnClickListener(this);
        tvExtraDoc.setOnClickListener(this);
        tvExtraDocView.setOnClickListener(this);
        rbMale = findViewById(R.id.rbMale);
        llDob = findViewById(R.id.llDob);
        tvDob = findViewById(R.id.tvDob);
        tvDob.setOnClickListener(this);
        updateUIPartnerProvider(preferenceHelper.getProviderType() == Const.ProviderStatus.PROVIDER_TYPE_PARTNER);
        getBankDetail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Const.ServiceCode.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    onCaptureImageResult();
                }
                break;
            case Const.ServiceCode.CHOOSE_PHOTO:
                onSelectFromGalleryResult(data);
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                handleCrop(resultCode, data);
                break;
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            picUri = data.getData();
            //beginCrop(picUri);
            setImage();
        }
    }

    /**
     * This method is used for handel result after captured image from camera .
     */
    private void onCaptureImageResult() {
        //beginCrop(picUri);
        setImage();
    }

    /**
     * This method is used for crop the image which selected or captured by currentTrip.
     */
    private void beginCrop(Uri sourceUri) {
        CropImage.activity(sourceUri).setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON).start(this);
    }

    /**
     * This method is used for  handel crop result after crop the image.
     */
    private void handleCrop(int resultCode, Intent result) {
        CropImage.ActivityResult activityResult = CropImage.getActivityResult(result);
        if (resultCode == RESULT_OK) {
            File file = ImageHelper.getFromMediaUriPfd(this, this.getContentResolver(), activityResult.getUri());
            if (bankDocumentDialog != null && bankDocumentDialog.isShowing() && file != null) {
                ImageView ivDocumentImage = bankDocumentDialog.findViewById(R.id.ivDocumentImage);
                GlideApp.with(this).load(activityResult.getUri()).fallback(R.drawable.ellipse).into(ivDocumentImage);
                ivDocumentImage.setTag(file.getPath());
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Toast.makeText(this, activityResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setImage() {
        File file = ImageHelper.getFromMediaUriPfd(this, this.getContentResolver(), picUri);
        if (bankDocumentDialog != null && bankDocumentDialog.isShowing() && file != null) {
            ImageView ivDocumentImage = bankDocumentDialog.findViewById(R.id.ivDocumentImage);
            GlideApp.with(this).load(picUri).fallback(R.drawable.ellipse).into(ivDocumentImage);
            ivDocumentImage.setTag(file.getPath());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Const.PIC_URI, picUri);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        picUri = savedInstanceState.getParcelable(Const.PIC_URI);
    }

    private void choosePhotoFromGallery() {
        if (Build.VERSION.SDK_INT <= 19) {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i, Const.ServiceCode.CHOOSE_PHOTO);
        } else if (Build.VERSION.SDK_INT > 19) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Const.ServiceCode.CHOOSE_PHOTO);
        }
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Utils.isNougat()) {
            picUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName(), imageHelper.createImageFile());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            picUri = Uri.fromFile(imageHelper.createImageFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, Const.ServiceCode.TAKE_PHOTO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
        setAdminApprovedListener(this);
    }

    @Override
    protected boolean isValidate() {
        String msg = null;
        if (paymentGatewayType == Const.PaymentMethod.PAY_STACK || paymentGatewayType == Const.PaymentMethod.PAYU) {
            if (etAccountNumber.getText().toString().trim().isEmpty()) {
                msg = getString(R.string.msg_plz_valid_account_number);
                etAccountNumber.requestFocus();
            } else if (etRoutingNumber.getText().toString().trim().isEmpty()) {
                msg = getString(R.string.msg_plz_valid_bank_code);
                etRoutingNumber.requestFocus();
            }
        } else {
            if (TextUtils.isEmpty(etAccountHolderName.getText().toString())) {
                msg = getString(R.string.msg_plz_account_name);
                etAccountHolderName.requestFocus();
            } else if (etAccountNumber.getText().toString().length() != 12) {
                msg = getString(R.string.msg_plz_valid_account_number);
                etAccountNumber.requestFocus();
            } else if (etRoutingNumber.getText().toString().length() != 9) {
                msg = getString(R.string.msg_plz_valid_routing_number);
                etRoutingNumber.requestFocus();
            } else if (etPersonalIdNumber.getText().toString().length() != 4) {
                msg = getString(R.string.msg_plz_valid_personal_id_number);
                etPersonalIdNumber.requestFocus();
            } else if (TextUtils.equals(tvDob.getText().toString(), getResources().getString(R.string.text_dob))) {
                msg = getString(R.string.msg_add_dob);
            } else if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
                msg = getString(R.string.msg_valid_address);
                etAddress.requestFocus();
            } else if (TextUtils.isEmpty(etStateCode.getText().toString().trim())) {
                msg = getString(R.string.msg_valid_state_code);
                etStateCode.requestFocus();
            } else if (TextUtils.isEmpty(etPostalCode.getText().toString().trim())) {
                msg = getString(R.string.msg_valid_postal_code);
                etPostalCode.requestFocus();
            } else if (TextUtils.isEmpty((CharSequence) tvFrontDocView.getTag())) {
                msg = getString(R.string.text_upload_photo_id_front);
            } else if (TextUtils.isEmpty((CharSequence) tvBackDocView.getTag())) {
                msg = getString(R.string.text_upload_photo_id_back);
            } else if (TextUtils.isEmpty((CharSequence) tvExtraDocView.getTag())) {
                msg = getString(R.string.text_upload_photo_additional);
            }
        }
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void openVerifyAccountDialog() {
        if (TextUtils.isEmpty(preferenceHelper.getSocialId())) {
            verifyDialog = new CustomDialogVerifyAccount(this, getResources().getString(R.string.text_verify_account), getResources().getString(R.string.text_yes), getResources().getString(R.string.text_no), getResources().getString(R.string.text_pass_current_hint), false) {
                @Override
                public void doWithEnable(EditText editText) {
                    currentPassword = editText.getText().toString();
                    if (!currentPassword.isEmpty()) {
                        verifyDialog.dismiss();
                        if (bankdetails == null) {
                            addBankDetail();
                        } else {
                            deleteBankDetail();
                        }
                    } else {
                        Utils.showToast(getString(R.string.msg_enter_password), BankDetailActivity.this);
                    }
                }

                @Override
                public void doWithDisable() {
                    dismiss();
                }

                @Override
                public void clickOnText() {
                    dismiss();
                }
            };
            verifyDialog.show();
        } else {
            if (llDob.getVisibility() == View.VISIBLE) {
                addBankDetail();
            } else {
                deleteBankDetail();
            }
        }
    }

    private void addBankDetail() {
        Utils.showCustomProgressDialog(this, "", false, null);

        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put(Const.Params.BANK_ACCOUNT_HOLDER_NAME, ApiClient.makeTextRequestBody(etAccountHolderName.getText().toString()));
        hashMap.put(Const.Params.BANK_ACCOUNT_NUMBER, ApiClient.makeTextRequestBody(etAccountNumber.getText().toString()));
        hashMap.put(Const.Params.BANK_PERSONAL_ID_NUMBER, ApiClient.makeTextRequestBody(etPersonalIdNumber.getText().toString()));
        hashMap.put(Const.Params.BANK_ACCOUNT_HOLDER_TYPE, ApiClient.makeTextRequestBody(Const.Bank.BANK_ACCOUNT_HOLDER_TYPE));
        if (paymentGatewayType == Const.PaymentMethod.PAY_STACK || paymentGatewayType == Const.PaymentMethod.PAYU) {
            hashMap.put(Const.Params.BANK_CODE, ApiClient.makeTextRequestBody(etRoutingNumber.getText().toString()));
        } else {
            hashMap.put(Const.Params.BANK_ROUTING_NUMBER, ApiClient.makeTextRequestBody(etRoutingNumber.getText().toString()));
        }
        hashMap.put(Const.Params.DOB, ApiClient.makeTextRequestBody(tvDob.getText().toString()));
        hashMap.put(Const.Params.ADDRESS, ApiClient.makeTextRequestBody(etAddress.getText().toString()));
        hashMap.put(Const.Params.STATE, ApiClient.makeTextRequestBody(etStateCode.getText().toString()));
        hashMap.put(Const.Params.GENDER, ApiClient.makeTextRequestBody(rbMale.isChecked() ? "male" : "female"));
        hashMap.put(Const.Params.POSTAL_CODE, ApiClient.makeTextRequestBody(etPostalCode.getText().toString()));
        hashMap.put(Const.Params.SOCIAL_UNIQUE_ID, ApiClient.makeTextRequestBody(preferenceHelper.getSocialId()));
        hashMap.put(Const.Params.PASSWORD, ApiClient.makeTextRequestBody(currentPassword));
        hashMap.put(Const.Params.TOKEN, ApiClient.makeTextRequestBody(preferenceHelper.getSessionToken()));
        hashMap.put(Const.Params.PROVIDER_ID, ApiClient.makeTextRequestBody(preferenceHelper.getProviderId()));
        hashMap.put(Const.Params.PAYMENT_GATEWAY_TYPE, ApiClient.makeTextRequestBody(paymentGatewayType));

        Call<IsSuccessResponse> call;
        if (paymentGatewayType == Const.PaymentMethod.PAY_STACK || paymentGatewayType == Const.PaymentMethod.PAYU) {
            call = ApiClient.getClient().create(ApiInterface.class).addBankDetail(hashMap, null, null, null);
        } else {
            call = ApiClient.getClient().create(ApiInterface.class).addBankDetail(hashMap, ApiClient.makeMultipartRequestBody(this, (String) tvFrontDocView.getTag(), "front"), ApiClient.makeMultipartRequestBody(this, (String) tvBackDocView.getTag(), "back"), ApiClient.makeMultipartRequestBody(this, (String) tvExtraDocView.getTag(), "additional"));
        }
        call.enqueue(new Callback<IsSuccessResponse>() {
            @Override
            public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                if (parseContent.isSuccessful(response)) {
                    if (response.body().isSuccess()) {
                        Utils.hideCustomProgressDialog();
                        Utils.showMessageToast(response.body().getMessage(), BankDetailActivity.this);
                        onBackPressed();
                    } else {
                        Utils.hideCustomProgressDialog();
                        if (TextUtils.isEmpty(response.body().getStripeError())) {
                            Utils.showErrorToast(response.body().getErrorCode(), BankDetailActivity.this);
                        } else {
                            openBankDetailErrorDialog(response.body().getStripeError());
                        }


                    }
                }

            }

            @Override
            public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                AppLog.handleThrowable(BankDetailActivity.class.getSimpleName(), t);

            }
        });

    }

    private void getBankDetail() {

        Utils.showCustomProgressDialog(this, "", false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());

            Call<BankDetailResponse> call = ApiClient.getClient().create(ApiInterface.class).getBankDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<BankDetailResponse>() {
                @Override
                public void onResponse(Call<BankDetailResponse> call, Response<BankDetailResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        paymentGatewayType = response.body().getPaymentGatewayType();
                        if (response.body().isSuccess()) {
                            bankdetails = response.body().getBankdetails();
                            etAccountHolderName.setText(bankdetails.getAccountHolderName());
                            etAccountNumber.setText("********" + bankdetails.getAccountNumber());
                            etRoutingNumber.setText(bankdetails.getRoutingNumber());
                            etAccountHolderType.setText(bankdetails.getAccountHolderType());
                            updateUiOfBankDetail(true);
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.hideCustomProgressDialog();
                        }
                        updateUIToPaymentgateway();
                    }
                }

                @Override
                public void onFailure(Call<BankDetailResponse> call, Throwable t) {
                    AppLog.handleThrowable(BankDetailActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void updateUIToPaymentgateway() {
        if (paymentGatewayType == Const.PaymentMethod.PAY_STACK || paymentGatewayType == Const.PaymentMethod.PAYU) {
            tilRoutingNumber.setHint(R.string.hint_bank_code);
            etRoutingNumber.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            llAcHolder.setVisibility(View.GONE);
            llPersonalId.setVisibility(View.GONE);
            llDob.setVisibility(View.GONE);
            rbMale.setVisibility(View.GONE);
            rgGender.setVisibility(View.GONE);
            llPostalCode.setVisibility(View.GONE);
            llStateCode.setVisibility(View.GONE);
            cvUploadPhoto.setVisibility(View.GONE);
        }
    }

    /**
     * method update UI or make interaction enable or disable according provider type
     *
     * @param isPartnerProvider
     */
    private void updateUIPartnerProvider(boolean isPartnerProvider) {
        etAccountNumber.setEnabled(!isPartnerProvider);
        etAccountHolderName.setEnabled(!isPartnerProvider);
        etAccountHolderType.setEnabled(!isPartnerProvider);
        etRoutingNumber.setEnabled(!isPartnerProvider);
        etPersonalIdNumber.setEnabled(!isPartnerProvider);
        tvDob.setEnabled(!isPartnerProvider);
        if (!isPartnerProvider) {
            setToolbarIcon(AppCompatResources.getDrawable(this, R.drawable.ic_done_black_24dp), this);
        }

    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }


    private void deleteBankDetail() {
        Utils.showCustomProgressDialog(this, "", false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.PASSWORD, currentPassword);
            jsonObject.put(Const.Params.SOCIAL_UNIQUE_ID, preferenceHelper.getSocialId());
            jsonObject.put(Const.Params.PAYMENT_GATEWAY_TYPE, paymentGatewayType);

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).deleteBankDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {

                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            updateUiOfBankDetail(false);
                            bankdetails = null;
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), BankDetailActivity.this);
                        }

                        Utils.hideCustomProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(BankDetailActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDob:
                openDatePickerDialog();
                break;
            case R.id.ivToolbarIcon:
                if (bankdetails == null) {
                    if (isValidate()) {
                        openVerifyAccountDialog();
                    }
                } else {
                    openVerifyAccountDialog();
                }
                break;
            case R.id.tvFrontDoc:
            case R.id.tvFrontDocView:
                openBankDocumentDialog(tvFrontDocView, getResources().getString(R.string.text_upload_photo_id_front));
                break;

            case R.id.tvBackDoc:
            case R.id.tvBackDocView:
                openBankDocumentDialog(tvBackDocView, getResources().getString(R.string.text_upload_photo_id_back));
                break;

            case R.id.tvExtraDoc:
            case R.id.tvExtraDocView:
                openBankDocumentDialog(tvExtraDocView, getResources().getString(R.string.text_upload_photo_additional));
                break;
            default:
                break;
        }

    }

    protected void openPhotoDialog() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
        } else {
            CustomPhotoDialog customPhotoDialog = new CustomPhotoDialog(this) {
                @Override
                public void clickedOnCamera() {
                    dismiss();
                    takePhotoFromCamera();
                }

                @Override
                public void clickedOnGallery() {
                    dismiss();
                    choosePhotoFromGallery();
                }
            };
            customPhotoDialog.show();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    private void openDatePickerDialog() {

        final Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDate = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvDob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, currentYear, currentMonth, currentDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, calendar1.get(Calendar.YEAR) - 13);
        datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
        datePickerDialog.show();

    }

    private void openBankDetailErrorDialog(String message) {

        final CustomDialogBigLabel customDialogBigLabel = new CustomDialogBigLabel(this, getResources().getString(R.string.text_error), message, getResources().getString(R.string.text_retry), getResources().getString(R.string.text_ok)) {
            @Override
            public void positiveButton() {
                dismiss();
            }

            @Override
            public void negativeButton() {
                dismiss();

            }
        };
        customDialogBigLabel.btnYes.setVisibility(View.GONE);
        customDialogBigLabel.show();
    }

    private void updateUiOfBankDetail(boolean isHaveDetail) {
        if (isHaveDetail) {
            llDob.setVisibility(View.GONE);
            llPersonalId.setVisibility(View.GONE);
            if (preferenceHelper.getProviderType() != Const.ProviderStatus.PROVIDER_TYPE_PARTNER) {
                setToolbarIcon(AppCompatResources.getDrawable(this, R.drawable.ic_close_black_24dp), this);
            }
            updateUIPartnerProvider(true);
        } else {
            llDob.setVisibility(View.VISIBLE);
            llPersonalId.setVisibility(View.VISIBLE);
            etPersonalIdNumber.getText().clear();
            etRoutingNumber.getText().clear();
            etAccountHolderType.getText().clear();
            etAccountHolderName.getText().clear();
            etAccountNumber.getText().clear();
            updateUIPartnerProvider(preferenceHelper.getProviderType() == Const.ProviderStatus.PROVIDER_TYPE_PARTNER);
        }
        updateUIToPaymentgateway();
    }


    private void openBankDocumentDialog(final TextView docType, String docName) {
        if (bankDocumentDialog != null && bankDocumentDialog.isShowing()) {
            return;
        }
        bankDocumentDialog = new Dialog(this);
        bankDocumentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bankDocumentDialog.setContentView(R.layout.dialog_bank_document_upload);
        TextView tvDocName = bankDocumentDialog.findViewById(R.id.tvDocumentTitle);
        tvDocName.setText(docName);
        final ImageView docImage = bankDocumentDialog.findViewById(R.id.ivDocumentImage);
        if (!TextUtils.isEmpty((CharSequence) docType.getTag())) {
            GlideApp.with(this).load(docType.getTag().toString()).fallback(R.drawable.ic_add_image).into(docImage);
            docImage.setTag(docType.getTag().toString());
        }
        docImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoDialog();

            }
        });
        bankDocumentDialog.findViewById(R.id.btnDialogDocumentSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty((CharSequence) docImage.getTag())) {
                    docType.setTag(docImage.getTag());
                    docType.setText(getResources().getString(R.string.text_view));
                    bankDocumentDialog.dismiss();

                } else {
                    Utils.showToast(getResources().getString(R.string.msg_add_document_image), BankDetailActivity.this);
                }
            }
        });
        bankDocumentDialog.findViewById(R.id.btnDialogDocumentCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankDocumentDialog.dismiss();
            }
        });
        bankDocumentDialog.setCancelable(false);
        WindowManager.LayoutParams params = bankDocumentDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        bankDocumentDialog.getWindow().setAttributes(params);
        bankDocumentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bankDocumentDialog.show();

    }
}
