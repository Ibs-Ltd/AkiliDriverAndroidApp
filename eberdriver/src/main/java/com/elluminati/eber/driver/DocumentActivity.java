package com.elluminati.eber.driver;

import static com.elluminati.eber.driver.utils.Const.IMAGE_BASE_URL;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.adapter.DocumentAdaptor;
import com.elluminati.eber.driver.components.CustomDialogBigLabel;
import com.elluminati.eber.driver.components.CustomDialogEnable;
import com.elluminati.eber.driver.components.CustomPhotoDialog;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.interfaces.ClickListener;
import com.elluminati.eber.driver.interfaces.RecyclerTouchListener;
import com.elluminati.eber.driver.models.datamodels.Document;
import com.elluminati.eber.driver.models.responsemodels.DocumentResponse;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentActivity extends BaseAppCompatActivity {

    public ImageHelper imageHelper;
    private String expireDate;
    private Uri picUri;
    private CustomPhotoDialog customPhotoDialog;
    private RecyclerView rcvDocumentList;
    private ArrayList<Document> docList;
    private DocumentAdaptor documentAdaptor;
    private MyFontButton btnSubmitDocument;
    private CustomDialogBigLabel customDialogBigLabel;
    private int docListPosition = 0;
    private boolean isClickedOnDrawer;
    private CustomDialogEnable customDialogEnable;
    private Dialog documentDialog;
    private ImageView ivDocumentImage;
    private MyFontEdittextView etDocumentNumber, etExpireDate;
    private MyAppTitleFontTextView tvDocumentTitle;
    private TextInputLayout tilDocumentNumber;
    private String uploadImageFilePath = "";
    private LinearLayout llDocumentList;
    private FrameLayout flNoDocumentMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_Document));
        btnSubmitDocument = findViewById(R.id.btnSubmitDocument);
        llDocumentList = findViewById(R.id.llDocumentList);
        flNoDocumentMsg = findViewById(R.id.flNoDocumentMsg);
        btnSubmitDocument.setOnClickListener(this);
        imageHelper = new ImageHelper(this);
        initDocumentRcv();
        getProviderDocument();
        if (getIntent() != null) {
            isClickedOnDrawer = getIntent().getExtras().getBoolean(Const.IS_CLICK_INSIDE_DRAWER);
        }
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

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }

    protected void openPhotoDialog() {

        if (ContextCompat.checkSelfPermission(DocumentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(DocumentActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DocumentActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
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

    private void choosePhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Const.ServiceCode.CHOOSE_PHOTO);
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

    /**
     * This method is used for handel result after captured image from camera .
     */
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
                    GlideApp.with(DocumentActivity.this).load(imageUri).fallback(R.drawable.ellipse).override(200, 200).into(ivDocumentImage);
                }
            }
        }).execute(uploadImageFilePath);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitDocument:
                if (preferenceHelper.getAllDocUpload() == Const.ProviderStatus.IS_UPLOADED) {
                    goToMainDrawerActivity();
                } else {
                    Utils.showToast(getResources().getString(R.string.msg_upload_all_document), this);
                }
                break;
            default:

                break;

        }

    }

    private void getProviderDocument() {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_get_documents), false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            Call<DocumentResponse> call = ApiClient.getClient().create(ApiInterface.class).getDocuments(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<DocumentResponse>() {
                @Override
                public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {

                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            docList.addAll(response.body().getDocuments());
                            if (docList.size() == 0) {
                                preferenceHelper.putAllDocUpload(Const.ProviderStatus.IS_UPLOADED);
                                goToMainDrawerActivity();
                            } else {
                                documentAdaptor.notifyDataSetChanged();
                            }
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), DocumentActivity.this);
                            Utils.hideCustomProgressDialog();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DocumentResponse> call, Throwable t) {
                    AppLog.handleThrowable(DocumentActivity.class.getSimpleName(), t);
                }
            });


        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }

    }

    private void uploadDocument(String expireDate, String uniqueCode, String documentId, final int position) {


        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_upload_document), false, null);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(Const.Params.DOCUMENT_ID, ApiClient.makeTextRequestBody(documentId));
        map.put(Const.Params.TOKEN, ApiClient.makeTextRequestBody(preferenceHelper.getSessionToken()));
        map.put(Const.Params.PROVIDER_ID, ApiClient.makeTextRequestBody(preferenceHelper.getProviderId()));
        map.put(Const.Params.UNIQUE_CODE, ApiClient.makeTextRequestBody(uniqueCode));
        if (!TextUtils.isEmpty(expireDate)) {
            map.put(Const.Params.EXPIRED_DATE, ApiClient.makeTextRequestBody(expireDate));
        }

        Call<Document> userDataResponseCall;
        if (!TextUtils.isEmpty(uploadImageFilePath) || picUri != null) {
            userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).uploadDocument(ApiClient.makeMultipartRequestBody(this, (TextUtils.isEmpty(uploadImageFilePath) ? ImageHelper.getFromMediaUriPfd(this, getContentResolver(), picUri).getPath() : uploadImageFilePath), Const.Params.PICTURE_DATA), map);
        } else {
            userDataResponseCall = ApiClient.getClient().create(ApiInterface.class).uploadDocument(null, map);
        }
        userDataResponseCall.enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                if (parseContent.isSuccessful(response)) {
                    uploadImageFilePath = "";
                    picUri = null;
                    if (response.body().isSuccess()) {
                        Document document = docList.get(position);
                        document.setDocumentPicture(response.body().getDocumentPicture());
                        document.setExpiredDate(response.body().getExpiredDate());
                        document.setUniqueCode(response.body().getUniqueCode());
                        document.setIsUploaded(response.body().getIsUploaded());
                        documentAdaptor.notifyDataSetChanged();
                        preferenceHelper.putAllDocUpload(response.body().getIsDocumentUploaded());
                        Utils.hideCustomProgressDialog();
                    } else {
                        Utils.hideCustomProgressDialog();
                        Utils.showErrorToast(response.body().getErrorCode(), DocumentActivity
                                .this);
                    }


                }

            }

            @Override
            public void onFailure(Call<Document> call, Throwable t) {
                AppLog.handleThrowable(DocumentActivity.class.getSimpleName(), t);

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (isClickedOnDrawer) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            openLogoutDialog();
        }

    }

    protected void openLogoutDialog() {

        customDialogBigLabel = new CustomDialogBigLabel(this, getString(R.string.text_logout), getString(R.string.msg_are_you_sure), getString(R.string.text_yes), getString(R.string.text_no)) {
            @Override
            public void positiveButton() {
                customDialogBigLabel.dismiss();
                logOut(false);
            }

            @Override
            public void negativeButton() {
                customDialogBigLabel.dismiss();
            }
        };
        customDialogBigLabel.show();
    }


    private void openCameraPermissionDialog() {
        if (customDialogEnable != null && customDialogEnable.isShowing()) {
            return;
        }
        customDialogEnable = new CustomDialogEnable(this, getResources().getString(R.string.msg_reason_for_camera_permission), getString(R.string.text_i_am_sure), getString(R.string.text_re_try)) {
            @Override
            public void doWithEnable() {
                ActivityCompat.requestPermissions(DocumentActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, Const.PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE);
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

    private void initDocumentRcv() {
        docList = new ArrayList<>();
        rcvDocumentList = findViewById(R.id.rcvDocumentList);
        rcvDocumentList.setLayoutManager(new LinearLayoutManager(this));
        documentAdaptor = new DocumentAdaptor(this, docList);
        rcvDocumentList.setAdapter(documentAdaptor);
        rcvDocumentList.addOnItemTouchListener(new RecyclerTouchListener(this, rcvDocumentList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                docListPosition = position;
                openDocumentUploadDialog(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
        tvDocumentTitle.setText(document.getName());
        int maxWidth = getResources().getDisplayMetrics().widthPixels - (2 * getResources().getDimensionPixelSize(R.dimen.dimen_bill_line)) - (2 * getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin)) - (2 * getResources().getDimensionPixelSize(R.dimen.dimen_bill_margin_three)) - (2 * getResources().getDimensionPixelSize(R.dimen.dimen_bill_margin_two)) - (2 * getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        tvDocumentTitle.setMaxWidth(maxWidth);
        MyFontTextView tvOption = documentDialog.findViewById(R.id.tvOption);
        if (document.getOption() == Const.TRUE) {
            tvOption.setVisibility(View.VISIBLE);
        } else {
            tvOption.setVisibility(View.INVISIBLE);
        }
        GlideApp.with(this).load(IMAGE_BASE_URL + document.getDocumentPicture()).dontAnimate().fallback(R.drawable.uploading).override(200, 200).placeholder(R.drawable.uploading).into(ivDocumentImage);

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

                if (TextUtils.isEmpty(etDocumentNumber.getText().toString().trim()) && document.isIsUniqueCode()) {
                    Utils.showToast(getResources().getString(R.string.msg_plz_enter_document_unique_code), DocumentActivity.this);

                } else if ((TextUtils.isEmpty(expireDate) && document.isIsExpiredDate()) || isExpiredDate(date)) {
                    Utils.showToast(getResources().getString(R.string.msg_plz_enter_document_expire_date), DocumentActivity.this);

                } else if (TextUtils.isEmpty(uploadImageFilePath) && TextUtils.isEmpty(document.getDocumentPicture())) {
                    Utils.showToast(getResources().getString(R.string.msg_plz_select_document_image), DocumentActivity.this);
                } else {
                    documentDialog.dismiss();
                    uploadDocument(expireDate, etDocumentNumber.getText().toString(), document.getId(), position);
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

    private boolean isExpiredDate(Date date) {
        return date != null && date.before(new Date());


    }
}
