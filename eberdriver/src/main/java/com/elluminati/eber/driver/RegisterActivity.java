package com.elluminati.eber.driver;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elluminati.eber.driver.adapter.CityAdapter;
import com.elluminati.eber.driver.components.CustomCountryDialog;
import com.elluminati.eber.driver.components.CustomDialogBigLabel;
import com.elluminati.eber.driver.components.CustomDialogEnable;
import com.elluminati.eber.driver.components.CustomDialogVerifyDetail;
import com.elluminati.eber.driver.components.CustomPhotoDialog;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.components.MyFontTextViewMedium;
import com.elluminati.eber.driver.components.PinView;
import com.elluminati.eber.driver.interfaces.ClickListener;
import com.elluminati.eber.driver.interfaces.RecyclerTouchListener;
import com.elluminati.eber.driver.models.datamodels.City;
import com.elluminati.eber.driver.models.datamodels.Country;
import com.elluminati.eber.driver.models.datamodels.CountryList;
import com.elluminati.eber.driver.models.responsemodels.CountriesResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderDataResponse;
import com.elluminati.eber.driver.models.responsemodels.VerificationResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
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
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseAppCompatActivity implements TextView.OnEditorActionListener {


    private Uri picUri;
    private ImageView ivProfilePicture;
    private String uploadImageFilePath = "";
    private MyFontEdittextView etFirstName, etLastName, etAddress, etZipCode, etContactNumber, etEmail, etPassword, etBio, tvRegisterCountryName, tvRegisterCityName;
    private MyFontTextViewMedium tvCountryCode;
    private MyFontButton btnRegisterDone;
    private String loginType = Const.MANUAL;
    private ArrayList<Country> countryList;
    private MyAppTitleFontTextView tvGoSignIn;
    private LinearLayout llPassword;
    private CustomCountryDialog customCountryDialog;
    private CustomPhotoDialog customPhotoDialog;
    private CustomDialogEnable customDialogEnable;
    private CustomDialogBigLabel customDialogBigLabel;
    private ArrayList<City> cityList;
    private CityAdapter cityAdapter;
    private boolean isEmailVerify, isSMSVerify, isBackPressedOnce;
    private String otpForSMS, otpForEmail;
    private CustomDialogVerifyDetail customDialogVerifyDetail;
    private ImageHelper imageHelper;
    private int phoneNumberLength, phoneNumberMinLength;
    private String selectedCountryPhoneCode = "";
    private MyFontTextView tvTerms;
    private CallbackManager callbackManager;
    private String socialId, socialEmail, socialPhotoUrl, socialFirstName, socialLastName;
    private String msg;
    private Spinner selectGender;
    private Country country;
    public GoogleSignInClient googleSignInClient;
    private City city;
    private TextInputLayout tilPassword;
    private AccessTokenTracker accessTokenTracker;
    private CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CurrentTrip.getInstance().clear();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        imageHelper = new ImageHelper(this);
        isEmailVerify = preferenceHelper.getIsProviderEmailVerification();
        isSMSVerify = preferenceHelper.getIsProviderSMSVerification();
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        ivProfilePicture.setOnClickListener(this);
        btnRegisterDone = findViewById(R.id.btnRegisterDone);
        btnRegisterDone.setOnClickListener(this);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etZipCode = findViewById(R.id.etZipCode);
        etPassword = findViewById(R.id.etRegisterPassword);
        etEmail = findViewById(R.id.etRegisterEmailId);
        etBio = findViewById(R.id.etBio);
        etContactNumber = findViewById(R.id.etContactNumber);
        etContactNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        tvCountryCode = findViewById(R.id.tvCountryCode);
        tvRegisterCountryName = findViewById(R.id.tvRegisterCountryName);
        tvRegisterCountryName.setOnClickListener(this);
        tvRegisterCityName = findViewById(R.id.tvRegisterCityName);
        tvRegisterCityName.setOnClickListener(this);
        tvCountryCode.setOnClickListener(this);
        llPassword = findViewById(R.id.llPassword);
        tvGoSignIn = findViewById(R.id.tvGoSignIn);
        tilPassword = findViewById(R.id.tilPassword);
        tvGoSignIn.setOnClickListener(this);
        etAddress.setOnEditorActionListener(this);
        etBio.setOnEditorActionListener(this);
        etZipCode.setOnEditorActionListener(this);
        tvTerms = findViewById(R.id.tvTerms);
        String link = getResources().getString(R.string.text_trems_and_condition_main, preferenceHelper.getTermsANdConditions(), preferenceHelper.getPolicy());
        tvTerms.setText(Utils.fromHtml(link));
        tvTerms.setMovementMethod(LinkMovementMethod.getInstance());
        initFBLogin();
        initGoogleLogin();
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        updateUi(false);
        getServicesCountry();
        initGenderSelection();
        etPassword.addTextChangedListener(new TextWatcher() {
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

        //copy past data not allowed
        etContactNumber.setLongClickable(false);
        etContactNumber.setInputType(InputType.TYPE_CLASS_NUMBER);


        cbTerms = findViewById(R.id.cbTerms);
        disableRegisterButton();
        cbTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableRegisterButton();
                } else {
                    disableRegisterButton();
                }
            }
        });
        findViewById(R.id.llSocialLogin).setVisibility(preferenceHelper.getIsProviderSocialLogin() ? View.VISIBLE : View.GONE);


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

    }

    private void getFacebookProfileDetail(AccessToken accessToken) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Utils.hideCustomProgressDialog();
                try {
                    socialId = object.getString("id");
                    socialPhotoUrl = new URL("https://graph.facebook.com/" + socialId + "/picture?width=250&height=250").toString();
                    storeSocialImageFile(socialPhotoUrl);
                    if (object.has("email")) {
                        socialEmail = object.getString("email");
                    }
                    socialFirstName = object.getString("first_name");
                    socialLastName = object.getString("last_name");
                    loginType = Const.SOCIAL_FACEBOOK;
                    LoginManager.getInstance().logOut();
                    setSocialData();


                } catch (Exception e) {
                    socialPhotoUrl = "";
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
                loginType = Const.SOCIAL_GOOGLE;
                socialId = account.getId();
                socialEmail = account.getEmail();
                String str = account.getDisplayName();
                if (str.trim().contains(" ")) {
                    String[] name = str.split("\\s+");
                    socialFirstName = name[0].trim();
                    socialLastName = name[1].trim();
                } else {
                    socialFirstName = str.trim();
                    socialLastName = "";
                }
                socialPhotoUrl = account.getPhotoUrl().toString();
                storeSocialImageFile(socialPhotoUrl);
                setSocialData();
            } catch (Exception e) {
                setSocialData();
                AppLog.handleException(TAG, e);
            }
        } else {
            Utils.showToast(getString(R.string.message_can_not_signin_google), RegisterActivity.this);
        }
    }


    /**
     * Use for download profile image from facebook and google profile picture url...
     *
     * @param url
     */
    private void storeSocialImageFile(String url) {
        GlideApp.with(getApplicationContext()).asBitmap().load(url)

                .diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        uploadImageFilePath = createProfilePhoto(resource).getPath();
                        ivProfilePicture.setImageBitmap(resource);
                        return true;
                    }
                }).into(ivProfilePicture);
    }


    private File createProfilePhoto(Bitmap bitmap) {
        File imgaeFile = new File(this.getFilesDir(), "image.jpg");
        FileOutputStream os;
        try {
            os = new FileOutputStream(imgaeFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgaeFile;
    }


    private void register(String loginType) {
        HashMap<String, RequestBody> map = new HashMap<>();
        if (etPassword.getVisibility() == View.VISIBLE) {
            map.put(Const.Params.PASSWORD, ApiClient.makeTextRequestBody(etPassword.getText().toString()));
            FirebaseAuth.getInstance().signOut();
        } else {
            map.put(Const.Params.PASSWORD, ApiClient.makeTextRequestBody(""));
            map.put(Const.Params.SOCIAL_UNIQUE_ID, ApiClient.makeTextRequestBody(socialId));
        }
        map.put(Const.Params.FIRST_NAME, ApiClient.makeTextRequestBody(etFirstName.getText().toString()));
        map.put(Const.Params.LAST_NAME, ApiClient.makeTextRequestBody(etLastName.getText().toString()));
        map.put(Const.Params.EMAIL, ApiClient.makeTextRequestBody(etEmail.getText().toString()));
        map.put(Const.Params.DEVICE_TYPE, ApiClient.makeTextRequestBody(Const.DEVICE_TYPE_ANDROID));
        map.put(Const.Params.DEVICE_TOKEN, ApiClient.makeTextRequestBody(preferenceHelper.getDeviceToken()));

        map.put(Const.Params.PHONE, ApiClient.makeTextRequestBody(etContactNumber.getText().toString()));
        map.put(Const.Params.BIO, ApiClient.makeTextRequestBody(etBio.getText().toString()));


        map.put(Const.Params.SERVICE_TYPE, ApiClient.makeTextRequestBody(""));
        map.put(Const.Params.DEVICE_TIMEZONE, ApiClient.makeTextRequestBody(Utils.getTimeZoneName()));
        map.put(Const.Params.ADDRESS, ApiClient.makeTextRequestBody(etAddress.getText().toString()));

        map.put(Const.Params.ZIPCODE, ApiClient.makeTextRequestBody(etZipCode.getText().toString()));
        map.put(Const.Params.LOGIN_BY, ApiClient.makeTextRequestBody(loginType));
        map.put(Const.Params.APP_VERSION, ApiClient.makeTextRequestBody(getAppVersion()));

        if (country != null) {
            map.put(Const.Params.COUNTRY, ApiClient.makeTextRequestBody(country.getCountryname()));
            map.put(Const.Params.COUNTRY_ID, ApiClient.makeTextRequestBody(country.getId()));
            map.put(Const.Params.COUNTRY_PHONE_CODE, ApiClient.makeTextRequestBody(country.getCountryphonecode()));
        }
        if (city != null) {
            map.put(Const.Params.CITY_ID, ApiClient.makeTextRequestBody(city.getId()));
            map.put(Const.Params.CITY, ApiClient.makeTextRequestBody(city.getFullCityName()));
        }


        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_registering), false, null);
        Call<ProviderDataResponse> userDataResponseCall;
        if (!TextUtils.isEmpty(uploadImageFilePath)) {
            userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).register(ApiClient.makeMultipartRequestBody(this, uploadImageFilePath, Const.Params.PICTURE_DATA), map);
        } else {
            userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).register(null, map);
        }
        userDataResponseCall.enqueue(new Callback<ProviderDataResponse>() {
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
                AppLog.handleThrowable(RegisterActivity.class.getSimpleName(), t);
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
        super.onDestroy();
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
            case Const.google.RC_SIGN_IN:
                handleGoogleSignInResult(data);
                break;
            default:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
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

    /**
     * This method is used for crop the image which selected or captured by currentTrip.
     */
    private void beginCrop(Uri sourceUri) {
        CropImage.activity(sourceUri).setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON).start(this);
    }

    private void setProfileImage(Uri imageUri) {
        GlideApp.with(this).load(imageUri).fallback(R.drawable.ellipse).into(ivProfilePicture);
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
            picUri = FileProvider.getUriForFile(RegisterActivity.this, this.getApplicationContext().getPackageName(), imageHelper.createImageFile());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            picUri = Uri.fromFile(imageHelper.createImageFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, Const.ServiceCode.TAKE_PHOTO);
    }

    protected void openPhotoDialog() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
        } else {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegisterDone:
                checkValidationForRegister();
                break;
            case R.id.ivProfilePicture:
                openPhotoDialog();
                break;
            case R.id.tvRegisterCountryName:
                if (countryList.isEmpty()) {
                    openRequestRegisterTypeDialog(getResources().getString(R.string.msg_type_not_available_current_city));
                } else {
                    openCountryCodeDialog();
                }
                break;
            case R.id.tvRegisterCityName:
                if (cityList.isEmpty()) {
                    openRequestRegisterTypeDialog(getResources().getString(R.string.msg_type_not_available_current_city));
                } else {
                    openCityNameDialog();
                }
                break;
            case R.id.tvGoSignIn:
                goToSignInActivity();
                finish();
                break;
            default:
                //Do something with default
                break;
        }

    }


    private void checkValidationForRegister() {
        if (isValidate()) {
            if (isEmailVerify || isSMSVerify) {
                OTPVerify();
            } else {
                register(loginType);
            }
        }
    }


    private void updateUi(boolean update) {
        if (update) {
            llPassword.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
        } else {
            llPassword.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.VISIBLE);
        }
    }

    private void setSocialData() {
        updateUi(true);
        if (!TextUtils.isEmpty(socialEmail)) etEmail.setEnabled(false);
        etEmail.setText(socialEmail);
        etFirstName.setText(socialFirstName);
        etLastName.setText(socialLastName);
    }


    @Override
    protected boolean isValidate() {
        msg = null;
        Validator emailValidation = FieldValidation.isEmailValid(RegisterActivity.this, etEmail.getText().toString().trim());
        Validator passwordValidation = FieldValidation.isPasswordValid(RegisterActivity.this, etPassword.getText().toString().trim());

        if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_name);
            etFirstName.requestFocus();
            etFirstName.setError(msg);
        } else if (TextUtils.isEmpty(etLastName.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_lname);
            etLastName.requestFocus();
            etLastName.setError(msg);
        } else if (!emailValidation.isValid()) {
            msg = emailValidation.getErrorMsg();
            etEmail.requestFocus();
            etEmail.setError(msg);
        } else if (etPassword.getVisibility() == View.VISIBLE && !passwordValidation.isValid()) {
            msg = passwordValidation.getErrorMsg();
            etPassword.requestFocus();
            etPassword.setError(msg);
            tilPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.CLEAR);
        } else if (TextUtils.equals(tvRegisterCountryName.getText().toString(), getResources().getString(R.string.text_hint_select_country)) || TextUtils.isEmpty(tvRegisterCountryName.getText().toString())) {
            msg = getString(R.string.msg_plz_select_country);
            tvRegisterCountryName.requestFocus();
            tvRegisterCountryName.setError(msg);
        } else if (TextUtils.equals(tvRegisterCityName.getText().toString(), getResources().getString(R.string.text_hint_select_city)) || TextUtils.isEmpty(tvRegisterCityName.getText().toString())) {
            msg = getString(R.string.msg_plz_select_city);
            tvRegisterCityName.requestFocus();
            tvRegisterCityName.setError(msg);
        } else if (!Utils.isValidPhoneNumber(etContactNumber.getText().toString(), preferenceHelper.getMinimumPhoneNumberLength(), preferenceHelper.getMaximumPhoneNumberLength())) {
            msg = getString(R.string.msg_enter_valid_number);
            etContactNumber.requestFocus();
            etContactNumber.setError(msg);
        }
        return TextUtils.isEmpty(msg);
    }


    private void validateOtherInformation() {


    }


    @Override
    public void goWithBackArrow() {
    }

    private void detectCountryCodeFromList(String countryPhoneCode) {
        int countryListSize = countryList.size();
        for (int i = 0; i < countryListSize; i++) {
            if (countryList.get(i).getCountryphonecode().toUpperCase().trim().equals(countryPhoneCode.toUpperCase().trim())) {
                setCountry(i);
                return;
            }
        }
        setCountry(0);
    }

    private void openCountryCodeDialog() {
        tvRegisterCountryName.setError(null);
        customCountryDialog = null;
        customCountryDialog = new CustomCountryDialog(this, countryList, null, false) {
            @Override
            public void onSelect(int position, ArrayList<Country> filterList) {
                if (!selectedCountryPhoneCode.equalsIgnoreCase(filterList.get(position).getCountryphonecode())) {
                    etContactNumber.getText().clear();
                    selectedCountryPhoneCode = filterList.get(position).getCountryphonecode();
                }
                phoneNumberLength = filterList.get(position).getPhoneNumberLength();
                phoneNumberMinLength = filterList.get(position).getPhoneNumberMinLength();
                tvCountryCode.setText(filterList.get(position).getCountryphonecode());
                country = filterList.get(position);
                //setContactNoLength(phoneNumberLength);
                tvRegisterCountryName.setText(filterList.get(position).getCountryname());
                tvRegisterCountryName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_app_text, null));
                getCityListSelectedCountry(filterList.get(position).getCities());
                InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                customCountryDialog.dismiss();
            }

            @Override
            public void onSelectItem(int position, ArrayList<CountryList> filterList) {

            }
        };
        customCountryDialog.show();

    }

    private void openCityNameDialog() {
        tvRegisterCityName.setError(null);
        RecyclerView rcvCountryCode;
        MyFontEdittextView etCountrySearch;
        TextView tvDialogTitle;
        MyFontButton btnAddCity;

        final Dialog cityDialog = new Dialog(this);

        cityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cityDialog.setContentView(R.layout.dialog_country_code);
        btnAddCity = cityDialog.findViewById(R.id.btnAddCity);
        btnAddCity.setVisibility(View.VISIBLE);
        tvDialogTitle = cityDialog.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(getResources().getString(R.string.text_city_name));
        rcvCountryCode = cityDialog.findViewById(R.id.rcvCountryCode);
        etCountrySearch = cityDialog.findViewById(R.id.etCountrySearch);
        etCountrySearch.setHint(getResources().getString(R.string.text_search_city_name));
        rcvCountryCode.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter = new CityAdapter(cityList);
        rcvCountryCode.setAdapter(cityAdapter);
        WindowManager.LayoutParams params = cityDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        cityDialog.getWindow().setAttributes(params);
        cityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityDialog.dismiss();
                openRequestRegisterTypeDialog(getResources().getString(R.string.msg_type_not_available_current_city));

            }
        });
        etCountrySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cityAdapter != null) {
                    cityAdapter.getFilter().filter(s);
                } else {
                    Log.d("filter", "no filter availible");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        rcvCountryCode.addOnItemTouchListener(new RecyclerTouchListener(RegisterActivity.this, rcvCountryCode, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                tvRegisterCityName.setText(cityAdapter.getFilterResult().get(position).getCityName());
                city = cityAdapter.getFilterResult().get(position);
                tvRegisterCityName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_app_text, null));
                InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                cityDialog.dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        cityDialog.show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.etAddress:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //checkValidationForRegister();
                    hideKeyBord();
                    return true;
                }
                break;
            default:
                //Do something
                break;
        }
        return false;
    }


    private void enableRegisterButton() {
        btnRegisterDone.setClickable(true);
        btnRegisterDone.setBackground(AppCompatResources.getDrawable(this, R.drawable.selector_round_rect_shape_blue));
    }

    private void disableRegisterButton() {
        btnRegisterDone.setClickable(false);
        btnRegisterDone.setBackground(AppCompatResources.getDrawable(this, R.drawable.selector_round_rect_shape_blue_disable));

    }

    @Override
    public void onBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            openDetailNotSaveDialog();
        }
    }

    private void openCameraPermissionDialog() {
        if (customDialogEnable != null && customDialogEnable.isShowing()) {
            return;
        }
        customDialogEnable = new CustomDialogEnable(this, getResources().getString(R.string.msg_reason_for_camera_permission), getString(R.string.text_i_am_sure), getString(R.string.text_re_try)) {
            @Override
            public void doWithEnable() {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
                closedPermissionDialog();
            }

            @Override
            public void doWithDisable() {
                closedPermissionDialog();
            }
        };
        customDialogEnable.show();
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

    private void closedPermissionDialog() {
        if (customDialogEnable != null && customDialogEnable.isShowing()) {
            customDialogEnable.dismiss();
            customDialogEnable = null;

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openPhotoDialog();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            openCameraPermissionDialog();
                        } else {
                            closedPermissionDialog();
                            openPermissionNotifyDialog(Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
                        }
                    } else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            openCameraPermissionDialog();
                        } else {
                            closedPermissionDialog();
                            openPermissionNotifyDialog(Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
                        }
                    }
                    break;
                }
            default:
                //Do something
                break;

        }
    }

    private void getCityListSelectedCountry(List<City> cities) {
        tvRegisterCityName.setText(getResources().getString(R.string.text_hint_select_city));
        tvRegisterCityName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_app_label, null));
        cityList.clear();
        if (cities != null && !cities.isEmpty()) {
            cityList.addAll(cities);
        }
        Utils.hideCustomProgressDialog();
        if (cityList.isEmpty()) {
            if (Utils.isGpsEnable(RegisterActivity.this)) {
                openRequestRegisterTypeDialog(getResources().getString(R.string.msg_type_not_available_current_country));
            }
        }

    }

    private void setCountry(int position) {
        if (!countryList.isEmpty()) {
            tvCountryCode.setText((countryList.get(position).getCountryphonecode()));
            phoneNumberLength = countryList.get(position).getPhoneNumberLength();
            phoneNumberMinLength = countryList.get(position).getPhoneNumberMinLength();
            if (!selectedCountryPhoneCode.equalsIgnoreCase(countryList.get(position).getCountryphonecode())) {
                etContactNumber.getText().clear();
                selectedCountryPhoneCode = countryList.get(position).getCountryphonecode();
            }
            //setContactNoLength(phoneNumberLength);
            country = countryList.get(position);
            tvRegisterCountryName.setText(countryList.get(position).getCountryname());
            tvRegisterCountryName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_app_text, null));
            getCityListSelectedCountry(countryList.get(position).getCities());
        } else {
            Utils.showToast(getString(R.string.msg_enter_country), RegisterActivity.this);
        }
    }

    private void setContactNoLength(int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        etContactNumber.setFilters(FilterArray);
    }

    private void openRequestRegisterTypeDialog(String message) {
        customDialogBigLabel = new CustomDialogBigLabel(this, getResources().getString(R.string.text_register_type), message, getResources().getString(R.string.text_email), getResources().getString(R.string.text_cancel)) {
            @Override
            public void positiveButton() {
                dismiss();
                contactUsWithEmail(preferenceHelper.getContactUsEmail());

            }

            @Override
            public void negativeButton() {
                dismiss();
            }
        };
        customDialogBigLabel.show();
    }

    private void OTPVerify() {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_loading), false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.EMAIL, etEmail.getText());
            jsonObject.put(Const.Params.PHONE, etContactNumber.getText());
            jsonObject.put(Const.Params.COUNTRY_PHONE_CODE, country.getCountryphonecode());
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER);
            Call<VerificationResponse> call = ApiClient.getClient().create(ApiInterface.class).verification(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<VerificationResponse>() {
                @Override
                public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            Utils.hideCustomProgressDialog();
                            otpForEmail = response.body().getOtpForEmail();
                            otpForSMS = response.body().getOtpForSMS();
                            openOTPVerifyDialog();

                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), RegisterActivity
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
        customDialogVerifyDetail = new CustomDialogVerifyDetail(this, isEmailVerify, isSMSVerify, tvCountryCode.getText().toString(), etContactNumber.getText().toString()) {
            @Override
            public void doWithSubmit(PinView etEmailVerify, PinView etSMSVerify) {

                if (isEmailVerify && isSMSVerify) {
                    if (otpForEmail.equals(etEmailVerify.getText().toString()) && otpForSMS.equals(etSMSVerify.getText().toString())) {
                        dismiss();
                        register(loginType);
                    } else {
                        Utils.showToast(getResources().getString(R.string.msg_otp_wrong), RegisterActivity.this);
                    }


                } else if (isEmailVerify) {
                    if (otpForEmail.equals(etEmailVerify.getText().toString())) {
                        dismiss();
                        register(loginType);
                    } else {
                        Utils.showToast(getResources().getString(R.string.msg_otp_wrong), RegisterActivity.this);
                    }


                } else {
                    if (otpForSMS.equals(etSMSVerify.getText().toString())) {
                        dismiss();
                        register(loginType);
                    } else {
                        Utils.showToast(getResources().getString(R.string.msg_otp_wrong), RegisterActivity.this);
                    }
                }


            }

            @Override
            public void reSendCode() {
                OTPVerify();
            }

            @Override
            public void doCancel() {
                customDialogVerifyDetail.dismiss();
            }
        };
        customDialogVerifyDetail.show();
    }

    private void openDetailNotSaveDialog() {
        CustomDialogBigLabel detailNotSaveDialog = new CustomDialogBigLabel(this, getResources().getString(R.string.msg_are_you_sure), getResources().getString(R.string.msg_not_save), getResources().getString(R.string.text_yes), getResources().getString(R.string.text_no)) {
            @Override
            public void positiveButton() {
                this.dismiss();
                isBackPressedOnce = true;
                RegisterActivity.this.onBackPressed();

            }

            @Override
            public void negativeButton() {
                this.dismiss();
            }
        };
        detailNotSaveDialog.show();
    }

    private void getServicesCountry() {
        Utils.showCustomProgressDialog(this, "", false, null);
        Call<CountriesResponse> call = ApiClient.getClient().create(ApiInterface.class).getCountries();
        call.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (parseContent.isSuccessful(response)) {
                    countryList.addAll(response.body().getCountry());
                    detectCountryCodeFromList(Utils.getDeviceCountryPhoneCode(Utils.getDeviceCountryCode(RegisterActivity.this)));
                    Utils.hideCustomProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                AppLog.handleThrowable(ProfileActivity.class.getSimpleName(), t);
            }
        });
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


    private void initGenderSelection() {
        selectGender = findViewById(R.id.selectGender);
        String[] typedArray = getResources().getStringArray(R.array.gender);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, typedArray);
        selectGender.setAdapter(arrayAdapter);
        selectGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] typedArray = getResources().getStringArray(R.array.gender_english);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initFBLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton faceBookLogin = findViewById(R.id.btnFbLogin);
        faceBookLogin.setReadPermissions(Arrays.asList("email", "public_profile"));
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


}