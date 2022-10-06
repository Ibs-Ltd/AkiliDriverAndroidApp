package com.elluminati.eber.driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.elluminati.eber.driver.components.CustomDialogEnable;
import com.elluminati.eber.driver.components.CustomDialogVerifyAccount;
import com.elluminati.eber.driver.components.CustomDialogVerifyDetail;
import com.elluminati.eber.driver.components.CustomPhotoDialog;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextViewMedium;
import com.elluminati.eber.driver.components.PinView;
import com.elluminati.eber.driver.models.datamodels.Country;
import com.elluminati.eber.driver.models.responsemodels.CountriesResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderDataResponse;
import com.elluminati.eber.driver.models.responsemodels.VerificationResponse;
import com.elluminati.eber.driver.models.validations.Validator;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.FieldValidation;
import com.elluminati.eber.driver.utils.GlideApp;
import com.elluminati.eber.driver.utils.ImageCompression;
import com.elluminati.eber.driver.utils.ImageHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseAppCompatActivity {

    private MyFontEdittextView etProfileFirstName, etProfileLastName, etProfileAddress, etProfileZipCode, etProfileContectNumber, etProfileEmail, etProfileBio, etNewPassword, etGender;
    private LinearLayout llNewPassword;
    private ImageView ivProfileImage;
    private String currentPassword, otpForSMS, tempContactNumber;
    private Uri picUri;
    private boolean isUpdate;
    private ArrayList<Country> countryList;
    private MyFontTextViewMedium tvProfileCountryCode;
    private CustomPhotoDialog customPhotoDialog;
    private CustomDialogEnable customDialogEnable;
    private CustomDialogVerifyAccount verifyDialog;
    private ImageHelper imageHelper;
    private int phoneNumberLength, phoneNumberMinLength;
    private CustomDialogVerifyDetail customDialogVerifyDetail;
    private String uploadImageFilePath = "";
    private LinearLayout llSelectGender, llGender;
    private Spinner selectGender;
    private Button btnChangePassword;
    private TextInputLayout tilPassword;
    private Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_profile));
        setToolbarIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mode_edit_black_24dp), this);
        imageHelper = new ImageHelper(this);
        isUpdate = false;
        etProfileEmail = findViewById(R.id.etProfileEmail);
        etProfileFirstName = findViewById(R.id.etProfileFirstName);
        etProfileLastName = findViewById(R.id.etProfileLastName);
        etProfileAddress = findViewById(R.id.etProfileAddress);
        etProfileZipCode = findViewById(R.id.etProfileZipCode);
        etProfileContectNumber = findViewById(R.id.etProfileContactNumber);
        etProfileBio = findViewById(R.id.etProfileBio);
        etNewPassword = findViewById(R.id.etNewPassword);
        tvProfileCountryCode = findViewById(R.id.tvProfileCountryCode);
        tvProfileCountryCode.setOnClickListener(this);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        llNewPassword = findViewById(R.id.llNewPassword);
        etGender = findViewById(R.id.etGender);
        llSelectGender = findViewById(R.id.llSelectGender);
        llGender = findViewById(R.id.llGender);
        tilPassword = findViewById(R.id.tilPassword);
        countryList = new ArrayList<>();
        ivProfileImage.setOnClickListener(this);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(this);
        setProfileData();
        setEditable(false);
        getServicesCountry();
        if (!TextUtils.equals(Const.MANUAL, preferenceHelper.getLoginBy())) {
            upDateUI();
        }
        llSelectGender.setVisibility(View.GONE);
        llGender.setVisibility(View.GONE);
        etNewPassword.addTextChangedListener(new TextWatcher() {
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
        etProfileContectNumber.setLongClickable(false);
        etProfileContectNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
        setAdminApprovedListener(this);
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
        if (picUri != null) {
            setEditable(true);
            isUpdate = true;
            setToolbarIcon(AppCompatResources.getDrawable(ProfileActivity.this, R.drawable.ic_done_black_24dp), this);
        }

    }

    @Override
    protected boolean isValidate() {
        String msg = null;
        Validator passwordValidation = FieldValidation.isPasswordValid(ProfileActivity.this, etNewPassword.getText().toString().trim());
        if (TextUtils.isEmpty(etProfileFirstName.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_name);
            etProfileFirstName.requestFocus();
            etProfileFirstName.setError(msg);
        } else if (TextUtils.isEmpty(etProfileLastName.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_lname);
            etProfileLastName.requestFocus();
            etProfileLastName.setError(msg);
        } else if (TextUtils.isEmpty(etProfileContectNumber.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_number);
            etProfileContectNumber.setError(msg);
            etProfileContectNumber.requestFocus();
        }else if (!Utils.isValidPhoneNumber(etProfileContectNumber.getText().toString(), preferenceHelper.getMinimumPhoneNumberLength(), preferenceHelper.getMaximumPhoneNumberLength())) {
            msg = getString(R.string.msg_enter_valid_number);
            etProfileContectNumber.requestFocus();
            etProfileContectNumber.setError(msg);
        } else if (!TextUtils.isEmpty(etNewPassword.getText().toString().trim())) {
            if (!passwordValidation.isValid()) {
                msg = passwordValidation.getErrorMsg();
                etNewPassword.requestFocus();
                etNewPassword.setError(msg);
                tilPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.CLEAR);
            }
        }

        return TextUtils.isEmpty(msg);
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivProfileImage:
                openPhotoDialog();
                break;
            case R.id.btnChangePassword:
                if (etNewPassword.isEnabled()) {
                    etNewPassword.setEnabled(false);
                    btnChangePassword.setText(getResources().getString(R.string.text_change));
                    etNewPassword.getText().clear();
                } else {
                    etNewPassword.setEnabled(true);
                    btnChangePassword.setText(getResources().getString(R.string.text_cancel));
                    etNewPassword.requestFocus();
                }
                break;
            case R.id.ivToolbarIcon:
                if (isUpdate) {
                    if (isValidate()) {
                        if (preferenceHelper.getIsProviderSMSVerification()) {
                            if (TextUtils.equals(etProfileContectNumber.getText().toString(), preferenceHelper.getContact()) && TextUtils.equals(tvProfileCountryCode.getText().toString(), preferenceHelper.getCountryPhoneCode())) {
                                openVerifyAccountDialog();
                            } else {
                                if (TextUtils.equals(tempContactNumber, tvProfileCountryCode.getText().toString() + etProfileContectNumber.getText().toString())) {
                                    openVerifyAccountDialog();
                                } else {
                                    OTPVerify();
                                }

                            }
                        } else {
                            openVerifyAccountDialog();
                        }
                    }
                } else {
                    setEditable(true);
                    isUpdate = true;
                    setToolbarIcon(AppCompatResources.getDrawable(ProfileActivity.this, R.drawable.ic_done_black_24dp), this);
                }
                break;
            default:

                break;
        }

    }


    private void setProfileData() {
        etProfileFirstName.setText(preferenceHelper.getFirstName());
        etProfileLastName.setText(preferenceHelper.getLastName());
        etProfileAddress.setText(preferenceHelper.getAddress());
        etProfileBio.setText(preferenceHelper.getBio());
        etProfileEmail.setText(preferenceHelper.getEmail());
        etProfileContectNumber.setText(preferenceHelper.getContact());
        etProfileBio.setText(preferenceHelper.getBio());
        tvProfileCountryCode.setText(preferenceHelper.getCountryPhoneCode());
        etProfileZipCode.setText(preferenceHelper.getZipCode());


        GlideApp.with(this).load(preferenceHelper.getProfilePic()).dontAnimate().placeholder(R.drawable.ellipse).override(200, 200).into(ivProfileImage);


    }

    private void upDateUI() {
        etProfileEmail.setEnabled(false);
        llNewPassword.setVisibility(View.GONE);
    }

    /**
     * This method is used for crop the image which selected or captured
     */
    private void beginCrop(Uri sourceUri) {

        CropImage.activity(sourceUri).setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON).start(this);
    }

    private void setProfileImage(Uri imageUri) {
        GlideApp.with(this).load(imageUri).fallback(R.drawable.ellipse).into(ivProfileImage);
    }

    /**
     * This method is used for  handel crop result after crop the image.
     */
    private void handleCrop(int resultCode, Intent result) {
        final CropImage.ActivityResult activityResult = CropImage.getActivityResult(result);
        if (resultCode == RESULT_OK) {
            uploadImageFilePath = imageHelper.getRealPathFromURI(activityResult.getUri());
            new ImageCompression(this).setImageCompressionListener(new ImageCompression.ImageCompressionListener() {
                @Override
                public void onImageCompression(String compressionImagePath) {
                    setProfileImage(activityResult.getUri());
                    uploadImageFilePath = compressionImagePath;

                }
            }).execute(uploadImageFilePath);
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Utils.showToast(activityResult.getError().getMessage(), this);

        }
    }

    /**
     * This method is used for handel result after select image from gallery .
     */

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            picUri = data.getData();
            beginCrop(picUri);
        }
    }

    /**
     * This method is used for handel result after captured image from camera .
     */
    private void onCaptureImageResult() {
        beginCrop(picUri);
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
            case Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE:
                openPhotoDialog();
                break;
            default:

                break;

        }
    }

    private void upDateProfile() {

        HashMap<String, RequestBody> map = new HashMap<>();
        hideKeyPad();
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_update_profile), false, null);

        if (TextUtils.equals(Const.MANUAL, preferenceHelper.getLoginBy())) {
            map.put(Const.Params.OLD_PASSWORD, ApiClient.makeTextRequestBody(currentPassword));
        } else {
            map.put(Const.Params.OLD_PASSWORD, ApiClient.makeTextRequestBody(""));
        }
        map.put(Const.Params.FIRST_NAME, ApiClient.makeTextRequestBody(etProfileFirstName.getText().toString()));
        map.put(Const.Params.LAST_NAME, ApiClient.makeTextRequestBody(etProfileLastName.getText().toString()));
        map.put(Const.Params.NEW_PASSWORD, ApiClient.makeTextRequestBody(etNewPassword.getText().toString()));
        map.put(Const.Params.PHONE, ApiClient.makeTextRequestBody(etProfileContectNumber.getText().toString()));
        map.put(Const.Params.BIO, ApiClient.makeTextRequestBody(etProfileBio.getText().toString()));
        map.put(Const.Params.PROVIDER_ID, ApiClient.makeTextRequestBody(preferenceHelper.getProviderId()));
        map.put(Const.Params.ADDRESS, ApiClient.makeTextRequestBody(etProfileAddress.getText().toString()));
        map.put(Const.Params.ZIPCODE, ApiClient.makeTextRequestBody(etProfileZipCode.getText().toString()));
        map.put(Const.Params.COUNTRY_PHONE_CODE, ApiClient.makeTextRequestBody(tvProfileCountryCode.getText().toString()));
        map.put(Const.Params.TOKEN, ApiClient.makeTextRequestBody(preferenceHelper.getSessionToken()));


        Call<ProviderDataResponse> userDataResponseCall;
        if (!TextUtils.isEmpty(uploadImageFilePath)) {
            userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).updateProfile(ApiClient.makeMultipartRequestBody(this, uploadImageFilePath, Const.Params.PICTURE_DATA), map);
        } else {
            userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).updateProfile(null, map);
        }
        userDataResponseCall.enqueue(new Callback<ProviderDataResponse>() {
            @Override
            public void onResponse(Call<ProviderDataResponse> call, Response<ProviderDataResponse> response) {
                if (parseContent.isSuccessful(response)) {
                    if (parseContent.saveProviderData(response.body(), false)) {
                        Utils.hideCustomProgressDialog();
                        onBackPressed();
                    } else {
                        Utils.hideCustomProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProviderDataResponse> call, Throwable t) {
                AppLog.handleThrowable(ProfileActivity.class.getSimpleName(), t);
            }
        });

    }

    protected void openPhotoDialog() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
        } else {
            //Do the stuff that requires permission...
            customPhotoDialog = new CustomPhotoDialog(this) {
                @Override
                public void clickedOnCamera() {
                    customPhotoDialog.dismiss();
                    takePhotoFromCamera();
                }

                @Override
                public void clickedOnGallery() {
                    customPhotoDialog.dismiss();
                    choosePhotoFromGallery();
                }
            };
            customPhotoDialog.show();
        }


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
            picUri = FileProvider.getUriForFile(ProfileActivity.this, this.getApplicationContext().getPackageName(), imageHelper.createImageFile());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            picUri = Uri.fromFile(imageHelper.createImageFile().getAbsoluteFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, Const.ServiceCode.TAKE_PHOTO);
    }


    private void setEditable(boolean isEditable) {
        etProfileFirstName.setEnabled(isEditable);
        etProfileLastName.setEnabled(isEditable);
        etProfileBio.setEnabled(isEditable);
        etProfileAddress.setEnabled(isEditable);
        etProfileContectNumber.setEnabled(isEditable);
        tvProfileCountryCode.setEnabled(isEditable);
        etProfileZipCode.setEnabled(isEditable);
        ivProfileImage.setClickable(isEditable);
        etProfileFirstName.setFocusableInTouchMode(isEditable);
        etProfileLastName.setFocusableInTouchMode(isEditable);
        etProfileBio.setFocusableInTouchMode(isEditable);
        etProfileAddress.setFocusableInTouchMode(isEditable);
        etProfileContectNumber.setFocusableInTouchMode(isEditable);
        etProfileZipCode.setFocusableInTouchMode(isEditable);
        etProfileEmail.setEnabled(false);
        etProfileEmail.setFocusableInTouchMode(false);
        btnChangePassword.setEnabled(isEditable);
        tilPassword.setEnabled(isEditable);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void hideKeyPad() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE:
                    getCameraAndStoragePermission(grantResults);
                    break;
                default:

                    break;
            }
        }
    }

    private void getCameraAndStoragePermission(int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Do the stuff that requires permission...
            openPhotoDialog();
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                openCameraPermissionDialog();
            } else {
                closedPermissionDialog();
                openPermissionNotifyDialog(Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
            }
        } else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                openCameraPermissionDialog();
            } else {
                closedPermissionDialog();
                openPermissionNotifyDialog(Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
            }
        }
    }

    private void openCameraPermissionDialog() {
        if (customDialogEnable != null && customDialogEnable.isShowing()) {
            return;
        }
        customDialogEnable = new CustomDialogEnable(this, getResources().getString(R.string.msg_reason_for_camera_permission), getString(R.string.text_i_am_sure), getString(R.string.text_re_try)) {
            @Override
            public void doWithEnable() {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
                closedPermissionDialog();
            }

            @Override
            public void doWithDisable() {
                closedPermissionDialog();
            }
        };
        customDialogEnable.show();
    }

    private void closedPermissionDialog() {
        if (customDialogEnable != null && customDialogEnable.isShowing()) {
            customDialogEnable.dismiss();
            customDialogEnable = null;

        }
    }

    private void openVerifyAccountDialog() {
        if (TextUtils.equals(Const.MANUAL, preferenceHelper.getLoginBy())) {
            verifyDialog = new CustomDialogVerifyAccount(this, getResources().getString(R.string.text_verify_account), getResources().getString(R.string.text_yes), getResources().getString(R.string.text_no), getResources().getString(R.string.text_pass_current_hint), false) {
                @Override
                public void doWithEnable(EditText editText) {
                    currentPassword = editText.getText().toString();
                    if (!currentPassword.isEmpty()) {
                        verifyDialog.dismiss();
                        upDateProfile();

                    } else {
                        Utils.showToast(getString(R.string.msg_enter_password), ProfileActivity
                                .this);
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
            upDateProfile();
        }


    }

    private void OTPVerify() {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_loading), false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Const.Params.PHONE, etProfileContectNumber.getText());
            jsonObject.accumulate(Const.Params.COUNTRY_PHONE_CODE, tvProfileCountryCode.getText().toString());
            jsonObject.accumulate(Const.Params.TYPE, Const.PROVIDER);

            Call<VerificationResponse> call = ApiClient.getClient().create(ApiInterface.class).verification(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<VerificationResponse>() {
                @Override
                public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            Utils.hideCustomProgressDialog();
                            otpForSMS = response.body().getOtpForSMS();
                            openOTPVerifyDialog();

                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), ProfileActivity
                                    .this);
                        }
                    }


                }

                @Override
                public void onFailure(Call<VerificationResponse> call, Throwable t) {
                    AppLog.handleThrowable(ProfileActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void openOTPVerifyDialog() {
        if (customDialogVerifyDetail != null && customDialogVerifyDetail.isShowing()) {
            customDialogVerifyDetail.notifyDataSetChange();
            return;
        }
        customDialogVerifyDetail = new CustomDialogVerifyDetail(this, false, true, tvProfileCountryCode.getText().toString(), etProfileContectNumber.getText().toString()) {
            @Override
            public void doWithSubmit(PinView etEmailVerify, PinView etSMSVerify) {

                if (otpForSMS.equals(etSMSVerify.getText().toString())) {
                    dismiss();
                    openVerifyAccountDialog();
                    tempContactNumber = tvProfileCountryCode.getText().toString() + etProfileContectNumber.getText().toString();
                } else {
                    Utils.showToast(getResources().getString(R.string.msg_otp_wrong), ProfileActivity.this);
                    tempContactNumber = "";
                }
            }

            @Override
            public void doCancel() {
                this.dismiss();
                hideKeyPad();
            }

            @Override
            public void reSendCode() {
                OTPVerify();
            }
        };
        customDialogVerifyDetail.show();
    }

    private void getServicesCountry() {
        Utils.showCustomProgressDialog(this, "", false, null);
        Call<CountriesResponse> call = ApiClient.getClient().create(ApiInterface.class).getCountries();
        call.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (parseContent.isSuccessful(response)) {
                    Utils.hideCustomProgressDialog();
                    countryList.addAll(response.body().getCountry());
                    for (Country countryCode : countryList) {
                        if (TextUtils.equals(countryCode.getCountryphonecode(), preferenceHelper.getCountryPhoneCode())) {
                            setCountry(countryCode);
//                            phoneNumberLength = countryCode.getPhoneNumberLength();
//                            phoneNumberMinLength = countryCode.getPhoneNumberMinLength();
//                            setContactNoLength(phoneNumberLength);
                            break;
                        }

                    }
                }
            }


            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                AppLog.handleThrowable(ProfileActivity.class.getSimpleName(), t);
            }
        });
    }

    private void setCountry(Country country) {
        this.country = country;
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

    private void openPermissionNotifyDialog(final int code) {
        if (customDialogEnable != null && customDialogEnable.isShowing()) {
            return;
        }
        customDialogEnable = new CustomDialogEnable(this, getResources().getString(R.string.msg_permission_notification), getResources().getString(R.string.text_exit_caps), getResources().getString(R.string.text_settings)) {
            @Override
            public void doWithEnable() {
                closedPermissionDialog();
                startActivityForResult(getIntentForPermission(), code);

            }

            @Override
            public void doWithDisable() {
                closedPermissionDialog();
                finishAffinity();
            }
        };
        customDialogEnable.show();

    }

    private void setContactNoLength(int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        etProfileContectNumber.setFilters(FilterArray);
    }
}

