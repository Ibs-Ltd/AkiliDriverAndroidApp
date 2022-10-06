package com.elluminati.eber.driver;

import static com.elluminati.eber.driver.utils.Const.IMAGE_BASE_URL;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.adapter.DocumentAdaptor;
import com.elluminati.eber.driver.components.CustomDialogEnable;
import com.elluminati.eber.driver.components.CustomPhotoDialog;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.interfaces.ClickListener;
import com.elluminati.eber.driver.interfaces.RecyclerTouchListener;
import com.elluminati.eber.driver.models.datamodels.Document;
import com.elluminati.eber.driver.models.datamodels.VehicleDetail;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.VehicleDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.VehicleDocumentResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.GlideApp;
import com.elluminati.eber.driver.utils.ImageCompression;
import com.elluminati.eber.driver.utils.ImageHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleDetailActivity extends BaseAppCompatActivity {

    public ImageHelper imageHelper;
    private int currentYear;
    private int passingYear;
    private boolean isAddVehicle = true;
    private MyFontEdittextView edtVehicleName, edtVehicleModel, edtVehiclePlateNo, edtVehicleColor, edtPassingYear;
    private LinearLayout llVehicleDocument;
    private RecyclerView rcvVehicleDocument;
    private ArrayList<Document> docList;
    private DocumentAdaptor documentAdaptor;
    private int docListPosition = 0;
    private Dialog documentDialog;
    private ImageView ivDocumentImage;
    private MyFontEdittextView etDocumentNumber, etExpireDate;
    private MyAppTitleFontTextView tvDocumentTitle;
    private TextInputLayout tilDocumentNumber;
    private String uploadImageFilePath = "", expireDate;
    private CustomPhotoDialog customPhotoDialog;
    private CustomDialogEnable customDialogEnable;
    private Uri picUri;
    private String vehicleId = "";
    private ImageView ivAddVehicleTypeImage;
    private CheckBox cbHandicap, cbBabySeat, cbHotspot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_detail);
        initToolBar();
        setTitleOnToolbar(getString(R.string.text_manage_vehicles));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        edtVehicleName = findViewById(R.id.edtVehicleName);
        edtVehicleModel = findViewById(R.id.edtVehicleModel);
        edtVehiclePlateNo = findViewById(R.id.edtVehiclePlateNo);
        edtVehicleColor = findViewById(R.id.edtVehicleColor);
        llVehicleDocument = findViewById(R.id.llVehicleDocument);
        rcvVehicleDocument = findViewById(R.id.rcvVehicleDocument);
        ivAddVehicleTypeImage = findViewById(R.id.ivAddVehicleTypeImage);
        edtPassingYear = findViewById(R.id.edtPassingYear);

        cbBabySeat = findViewById(R.id.cbBabySeat);
        cbHandicap = findViewById(R.id.cbHandicap);
        cbHotspot = findViewById(R.id.cbHotspot);
        imageHelper = new ImageHelper(this);
        initVehicleDocumentList();
        initYearSpinner();
        if (getIntent() != null) {
            isAddVehicle = getIntent().getExtras().getBoolean(Const.IS_ADD_VEHICLE);
            vehicleId = getIntent().getExtras().getString(Const.VEHICLE_ID);
        }
        updateEditIcon(isAddVehicle);
        if (!TextUtils.isEmpty(vehicleId)) {
            getProviderVehicleDetail();
        }
    }


    private void initVehicleDocumentList() {
        docList = new ArrayList<>();
        rcvVehicleDocument.setLayoutManager(new LinearLayoutManager(this));
        documentAdaptor = new DocumentAdaptor(this, docList);
        rcvVehicleDocument.setAdapter(documentAdaptor);
        rcvVehicleDocument.addOnItemTouchListener(new RecyclerTouchListener(this, rcvVehicleDocument, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                docListPosition = position;
                if (preferenceHelper.getProviderType() != Const.ProviderStatus.PROVIDER_TYPE_PARTNER) {
                    openDocumentUploadDialog(position);
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    private void initYearSpinner() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        passingYear = currentYear - 20;
    }


    @Override
    protected boolean isValidate() {
        String msg = null;

        if (TextUtils.isEmpty(edtVehicleName.getText().toString().trim())) {
            msg = getString(R.string.text_message_provide_vehicle_name);
            edtVehicleName.requestFocus();
        } else if (TextUtils.isEmpty(edtVehicleModel.getText().toString().trim())) {
            msg = getString(R.string.text_message_provide_vehicle_model);
            edtVehicleModel.requestFocus();
        } else if (TextUtils.isEmpty(edtVehiclePlateNo.getText().toString().trim())) {
            msg = getString(R.string.text_message_provide_vehicle_plate_no);
            edtVehiclePlateNo.requestFocus();
        } else if (edtVehiclePlateNo.getText().toString().trim().length() > 15) {
            msg = getString(R.string.text_message_provide_vehicle_plate_no);
            edtVehiclePlateNo.requestFocus();
        }
        else if (TextUtils.isEmpty(edtVehicleColor.getText().toString().trim())) {
            msg = getString(R.string.text_message_provide_vehicle_color);
            edtVehicleColor.requestFocus();
        } else if (TextUtils.isEmpty(edtPassingYear.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_year);
            edtPassingYear.requestFocus();
        } else if (Integer.parseInt(edtPassingYear.getText().toString()) > currentYear || Integer.parseInt(edtPassingYear.getText().toString()) < passingYear) {
            msg = getString(R.string.msg_enter_year);
            edtPassingYear.requestFocus();
        }
        else if (TextUtils.isEmpty(edtPassingYear.getText().toString().trim())) {
            msg = getString(R.string.msg_enter_year);
            edtPassingYear.requestFocus();
        }
        else if (Integer.parseInt(edtPassingYear.getText().toString()) > currentYear
            || Integer.parseInt(edtPassingYear.getText().toString()) < passingYear){
            msg = getString(R.string.msg_enter_year);
            edtPassingYear.requestFocus();
        }

        if (msg != null) {
            Utils.showToast(msg, this);
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdminApprovedListener(this);
        setConnectivityListener(this);
    }

    private void updateUiVehicleDocument(boolean isShow) {
        if (isShow) llVehicleDocument.setVisibility(View.VISIBLE);
        else llVehicleDocument.setVisibility(View.GONE);
    }


    private void setEditable(boolean isEditable) {
        edtVehicleName.setEnabled(isEditable);
        edtVehicleColor.setEnabled(isEditable);
        edtPassingYear.setEnabled(isEditable);
        edtVehiclePlateNo.setEnabled(isEditable);
        edtVehicleModel.setEnabled(isEditable);
        cbBabySeat.setEnabled(isEditable);
        cbHotspot.setEnabled(isEditable);
        cbHandicap.setEnabled(isEditable);
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnToolBar:
                if (edtVehicleName.isEnabled()) {
                    if (isValidate()) {
                        if (isAddVehicle && TextUtils.isEmpty(vehicleId)) {
                            addVehicle();
                        } else {
                            updateVehicleDetail();
                        }
                    }
                } else {
                    updateEditIcon(true);
                }
                break;
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_OK);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void addVehicle() {
        JSONObject jsonObject = new JSONObject();
        Utils.showCustomProgressDialog(this, "", false, null);
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.VEHICLE_NAME, edtVehicleName.getText().toString().trim());
            jsonObject.put(Const.Params.PLATE_NO, edtVehiclePlateNo.getText().toString().trim());
            jsonObject.put(Const.Params.MODEL, edtVehicleModel.getText().toString().trim());
            jsonObject.put(Const.Params.COLOR, edtVehicleColor.getText().toString().trim());
            jsonObject.put(Const.Params.PASSING_YEAR, edtPassingYear.getText().toString().trim());
            jsonObject.put(Const.Params.ACCESSIBILITY, getAccessibility());


            Call<VehicleDetailResponse> call = ApiClient.getClient().create(ApiInterface.class).addVehicleDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<VehicleDetailResponse>() {
                @Override
                public void onResponse(Call<VehicleDetailResponse> call, Response<VehicleDetailResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        setVehicleDetail(response.body());
                    }

                }

                @Override
                public void onFailure(Call<VehicleDetailResponse> call, Throwable t) {
                    AppLog.handleThrowable(AddVehicleDetailActivity.class.getSimpleName(), t);

                }
            });
        } catch (JSONException e) {
            AppLog.handleException("PROVIDER_ADD_VEHICLE", e);
        }
    }


    private void setVehicleDetail(VehicleDetailResponse response) {
        if (response.isSuccess()) {
            VehicleDetail vehicleDetail = response.getVehicleDetail();
            setAccessibility(vehicleDetail.getAccessibility());
            edtVehicleName.setText(vehicleDetail.getName());
            edtVehicleModel.setText(vehicleDetail.getModel());
            edtVehiclePlateNo.setText(vehicleDetail.getPlateNo());
            edtVehicleColor.setText(vehicleDetail.getColor());
            vehicleId = vehicleDetail.getId();
            edtPassingYear.setText(vehicleDetail.getPassingYear());

            if (TextUtils.isEmpty(vehicleDetail.getServiceType())) {
                ivAddVehicleTypeImage.setVisibility(View.GONE);
            } else {
                ivAddVehicleTypeImage.setVisibility(View.VISIBLE);
                GlideApp.with(AddVehicleDetailActivity.this).load(IMAGE_BASE_URL + vehicleDetail.getTypeImageUrl()).placeholder(R.drawable.car_placeholder).into(ivAddVehicleTypeImage);
            }
            docList.addAll(response.getDocumentList());
            if (docList.size() > 0) {
                updateUiVehicleDocument(true);
                documentAdaptor.notifyDataSetChanged();
            } else {
                updateUiVehicleDocument(false);
            }

            updateEditIcon(false);
        } else {
            updateUiVehicleDocument(false);
        }
        Utils.hideCustomProgressDialog();
    }

    private void updateVehicleDetail() {
        Utils.showCustomProgressDialog(this, "", false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.VEHICLE_NAME, edtVehicleName.getText().toString().trim());
            jsonObject.put(Const.Params.VEHICLE_ID, vehicleId);
            jsonObject.put(Const.Params.PLATE_NO, edtVehiclePlateNo.getText().toString().trim());
            jsonObject.put(Const.Params.MODEL, edtVehicleModel.getText().toString().trim());
            jsonObject.put(Const.Params.COLOR, edtVehicleColor.getText().toString().trim());
            jsonObject.put(Const.Params.PASSING_YEAR, edtPassingYear.getText().toString().trim());
            jsonObject.put(Const.Params.ACCESSIBILITY, getAccessibility());


            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).updateVehicleDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            Utils.showToast(getString(R.string.message_detail_vehicle_successfully_added), AddVehicleDetailActivity.this);
                            onBackPressed();
                        }
                        Utils.hideCustomProgressDialog();
                    }


                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(AddVehicleDetailActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException("PROVIDER_ADD_VEHICLE", e);
        }
    }


    private void getProviderVehicleDetail() {
        Utils.showCustomProgressDialog(this, "", false, null);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.VEHICLE_ID, vehicleId);
            Call<VehicleDetailResponse> call = ApiClient.getClient().create(ApiInterface.class).getVehicleDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<VehicleDetailResponse>() {
                @Override
                public void onResponse(Call<VehicleDetailResponse> call, Response<VehicleDetailResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        setVehicleDetail(response.body());
                    }

                }

                @Override
                public void onFailure(Call<VehicleDetailResponse> call, Throwable t) {
                    AppLog.handleThrowable(AddVehicleDetailActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException("GET_PROVIDER_VEHICLE_DETAIL", e);
        }

    }


    private void openDocumentUploadDialog(final int position) {

        if (documentDialog != null && documentDialog.isShowing()) {
            return;
        }

        final Document document = docList.get(position);
        expireDate = "";
        documentDialog = new Dialog(this);
        documentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        documentDialog.setContentView(R.layout.dialog_document_upload);
        ivDocumentImage = documentDialog.findViewById(R.id.ivDocumentImage);
        etDocumentNumber = documentDialog.findViewById(R.id.etDocumentNumber);
        etExpireDate = documentDialog.findViewById(R.id.etExpireDate);
        tilDocumentNumber = documentDialog.findViewById(R.id.tilDocumentNumber);
        tvDocumentTitle = documentDialog.findViewById(R.id.tvDocumentTitle);
        int maxWidth = getResources().getDisplayMetrics().widthPixels - (2 * getResources().getDimensionPixelSize(R.dimen.dimen_bill_line)) - (2 * getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin)) - (2 * getResources().getDimensionPixelSize(R.dimen.dimen_bill_margin_three)) - (2 * getResources().getDimensionPixelSize(R.dimen.dimen_bill_margin_two)) - (2 * getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        tvDocumentTitle.setMaxWidth(maxWidth);
        MyFontTextView tvOption = documentDialog.findViewById(R.id.tvOption);
        if (document.getOption() == Const.TRUE) {
            tvOption.setVisibility(View.VISIBLE);
        } else {
            tvOption.setVisibility(View.INVISIBLE);
        }
        tvDocumentTitle.setText(document.getName());
        GlideApp.with(this).load(IMAGE_BASE_URL + document.getDocumentPicture()).dontAnimate().fallback(R.drawable.ellipse).override(200, 200).placeholder(R.drawable.ellipse).into(ivDocumentImage);

        if (document.isIsExpiredDate()) {
            etExpireDate.setVisibility(View.VISIBLE);


            try {
                etExpireDate.setText(ParseContent.getInstance().dateFormat.format(ParseContent.getInstance().webFormatWithLocalTimeZone.parse(document.getExpiredDate())));
                Date date = parseContent.dateFormat.parse(etExpireDate.getText().toString());
                date.setTime(date.getTime() + 86399000);// 86399000 add 24 hour
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.US);
                expireDate = simpleDateFormat.format(date);
            } catch (ParseException e) {
                AppLog.handleException(TAG, e);
            }
        }
        if (document.isIsUniqueCode()) {
            tilDocumentNumber.setVisibility(View.VISIBLE);
            etDocumentNumber.setText(document.getUniqueCode());
        }

        documentDialog.findViewById(R.id.btnDialogDocumentSubmit).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Date date = null;
                if (!TextUtils.isEmpty(expireDate)) {
                    try {
                        date = parseContent.dateFormat.parse(expireDate);
                    } catch (ParseException e) {
                        AppLog.handleException(TAG, e);
                    }
                }
                if ((TextUtils.isEmpty(expireDate) && document.isIsExpiredDate()) || isExpiredDate(date)) {
                    Utils.showToast(getResources().getString(R.string.msg_plz_enter_document_expire_date), AddVehicleDetailActivity.this);

                } else if (TextUtils.isEmpty(etDocumentNumber.getText().toString().trim()) && document.isIsUniqueCode()) {
                    Utils.showToast(getResources().getString(R.string.msg_plz_enter_document_unique_code), AddVehicleDetailActivity.this);

                } else if (TextUtils.isEmpty(uploadImageFilePath) && TextUtils.isEmpty(document.getDocumentPicture())) {
                    Utils.showToast(getResources().getString(R.string.msg_plz_select_document_image), AddVehicleDetailActivity.this);
                } else {
                    documentDialog.dismiss();
                    uploadVehicleDocument(expireDate, etDocumentNumber.getText().toString(), document.getId(), position);
                    expireDate = "";
                }
            }
        });

        documentDialog.findViewById(R.id.btnDialogDocumentCancel).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                documentDialog.dismiss();
            }
        });
        ivDocumentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhotoDialog();
            }
        });
        etExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog();
            }
        });
        WindowManager.LayoutParams params = documentDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        documentDialog.getWindow().setAttributes(params);
        documentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        documentDialog.setCancelable(false);
        documentDialog.show();

    }

    protected void openPhotoDialog() {

        if (ContextCompat.checkSelfPermission(AddVehicleDetailActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AddVehicleDetailActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddVehicleDetailActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE:
                    goWithCameraAndStoragePermission(grantResults);
                    break;
                default:
                    break;
            }
        }

    }

    private void goWithCameraAndStoragePermission(int[] grantResults) {
        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //Do the stuff that requires permission...
            openPhotoDialog();
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                openCameraPermissionDialog();
            } else {
                closedPermissionDialog();
                openPermissionNotifyDialog(Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
            }
        } else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
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
                ActivityCompat.requestPermissions(AddVehicleDetailActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
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
            picUri = FileProvider.getUriForFile(this, getPackageName(), imageHelper.createImageFile());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            picUri = Uri.fromFile(imageHelper.createImageFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent, Const.ServiceCode.TAKE_PHOTO);
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
            case Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE:
                openPhotoDialog();
                break;
            default:
                break;

        }
    }

    private void onCaptureImageResult() {
        uploadImageFilePath = ImageHelper.getFromMediaUriPfd(this, getContentResolver(), picUri).getPath();
        setDocumentImage(picUri);
    }

    private void setDocumentImage(final Uri imageUri) {

        new ImageCompression(this).setImageCompressionListener(new ImageCompression.ImageCompressionListener() {

            @Override
            public void onImageCompression(String compressionImagePath) {
                if (documentDialog != null && documentDialog.isShowing()) {
                    uploadImageFilePath = compressionImagePath;
                    if (documentDialog != null && documentDialog.isShowing()) {
                        GlideApp.with(AddVehicleDetailActivity.this).load(imageUri).fallback(R.drawable.ellipse).override(200, 200).into(ivDocumentImage);
                    }
                }
            }
        }).execute(uploadImageFilePath);
    }

    /**
     * This method is used for handel result after select image from gallery .
     */

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            picUri = data.getData();
            uploadImageFilePath = ImageHelper.getFromMediaUriPfd(this, getContentResolver(), picUri).getPath();
            setDocumentImage(picUri);
        }
    }


    private void openDatePickerDialog() {


        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDate = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, currentYear, currentMonth, currentDate);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, this.getResources().getString(R.string.text_select), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (documentDialog != null && datePickerDialog.isShowing()) {

                    calendar.set(Calendar.YEAR, datePickerDialog.getDatePicker().getYear());
                    calendar.set(Calendar.MONTH, datePickerDialog.getDatePicker().getMonth());
                    calendar.set(Calendar.DAY_OF_MONTH, datePickerDialog.getDatePicker().getDayOfMonth());
                    etExpireDate.setText(parseContent.dateFormat.format(calendar.getTime()));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.US);
                    expireDate = simpleDateFormat.format(calendar.getTime());
                }
            }
        });
        long now = System.currentTimeMillis();
        datePickerDialog.getDatePicker().setMinDate(now + 86400000);
        datePickerDialog.show();
    }


    private void uploadVehicleDocument(String expireDate, String uniqueCode, String documentId, final int position) {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_upload_document), false, null);
        HashMap<String, RequestBody> map = new HashMap<>();

        try {
            map.put(Const.Params.DOCUMENT_ID, ApiClient.makeTextRequestBody(documentId));
            map.put(Const.Params.TOKEN, ApiClient.makeTextRequestBody(preferenceHelper.getSessionToken()));
            map.put(Const.Params.UNIQUE_CODE, ApiClient.makeTextRequestBody(uniqueCode));
            map.put(Const.Params.EXPIRED_DATE, ApiClient.makeTextRequestBody(TextUtils.isEmpty(expireDate) ? "" : expireDate));
            map.put(Const.Params.PROVIDER_ID, ApiClient.makeTextRequestBody(preferenceHelper.getProviderId()));
            map.put(Const.Params.VEHICLE_ID, ApiClient.makeTextRequestBody(vehicleId));

            Call<VehicleDocumentResponse> userDataResponseCall;
            if (!TextUtils.isEmpty(uploadImageFilePath) || picUri != null) {
                userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).uploadVehicleDocument(ApiClient.makeMultipartRequestBody(this, (TextUtils.isEmpty(uploadImageFilePath) ? ImageHelper.getFromMediaUriPfd(this, getContentResolver(), picUri).getPath() : uploadImageFilePath), Const.Params.PICTURE_DATA), map);
            } else {
                userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).uploadVehicleDocument(null, map);
            }
            userDataResponseCall.enqueue(new Callback<VehicleDocumentResponse>() {
                @Override
                public void onResponse(Call<VehicleDocumentResponse> call, Response<VehicleDocumentResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        uploadImageFilePath = "";
                        picUri = null;
                        if (response.body().isSuccess()) {
                            Document document = docList.get(position);
                            Document documentResponse = response.body().getDocuments();
                            document.setDocumentPicture(documentResponse.getDocumentPicture());
                            document.setExpiredDate(documentResponse.getExpiredDate());
                            document.setUniqueCode(documentResponse.getUniqueCode());
                            document.setIsUploaded(documentResponse.getIsUploaded());
                            documentAdaptor.notifyDataSetChanged();
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), AddVehicleDetailActivity
                                    .this);
                        }


                    }

                }

                @Override
                public void onFailure(Call<VehicleDocumentResponse> call, Throwable t) {
                    AppLog.handleThrowable(DocumentActivity.class.getSimpleName(), t);

                }
            });
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }

    }


    private void updateEditIcon(boolean isEditable) {
        setEditable(isEditable);
        if (preferenceHelper.getProviderType() != Const.ProviderStatus.PROVIDER_TYPE_PARTNER) {
            setToolbarRightSideButton(isEditable ? getResources().getString(R.string.text_done) : getResources().getString(R.string.text_edit), this);
        }


    }

    private JSONArray getAccessibility() {
        JSONArray jsonArray = new JSONArray();
        if (cbHandicap.isChecked()) {
            jsonArray.put(Const.Accessibility.HANDICAP);
        }
        if (cbHotspot.isChecked()) {
            jsonArray.put(Const.Accessibility.HOTSPOT);
        }
        if (cbBabySeat.isChecked()) {
            jsonArray.put(Const.Accessibility.BABY_SEAT);
        }
        return jsonArray;
    }

    private void setAccessibility(List<String> stringList) {
        if (stringList != null) {
            for (int i = 0; i < stringList.size(); i++) {
                if (TextUtils.equals(stringList.get(i), Const.Accessibility.HANDICAP)) {
                    cbHandicap.setChecked(true);
                }
                if (TextUtils.equals(stringList.get(i), Const.Accessibility.BABY_SEAT)) {
                    cbBabySeat.setChecked(true);
                }
                if (TextUtils.equals(stringList.get(i), Const.Accessibility.HOTSPOT)) {
                    cbHotspot.setChecked(true);
                }

            }
        }
    }

    private boolean isExpiredDate(Date date) {
        return date != null && date.before(new Date());


    }
}
