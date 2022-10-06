package com.elluminati.eber.driver.parse;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elluminati.eber.driver.BuildConfig;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.ServerConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {

    public static final String TAG = ApiClient.class.getSimpleName();
    private static final int CONNECTION_TIMEOUT = 60; //seconds
    private static final int READ_TIMEOUT = 50; //seconds
    private static final int WRITE_TIMEOUT = 50; //seconds
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("multipart/form-data");
    public static MediaType MEDIA_TYPE_IMAGE = MediaType.parse("placeholder/*");
    private static Retrofit retrofit = null;
    private static Gson gson;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient;

            if (BuildConfig.DEBUG) {
                // development build
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient = new OkHttpClient().newBuilder().connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).addInterceptor(interceptor).build();
            } else {
                // production build
                okHttpClient = new OkHttpClient().newBuilder().connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build();
            }

            retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(ServerConfig.BASE_URL).build();
        }
        return retrofit;
    }

    @NonNull
    public static MultipartBody.Part makeMultipartRequestBody(Context context, String photoPath, String partName) {
        File file = null;
        try {
            file = new File(photoPath);
        } catch (NullPointerException e) {
            AppLog.handleException(TAG, e);
        }
        RequestBody requestFile = RequestBody.create(MEDIA_TYPE_IMAGE, file);
        return MultipartBody.Part.createFormData(partName, context.getResources().getString(R.string.app_name), requestFile);

    }

    @NonNull
    public static MultipartBody.Part makeMultipartRequestBodySocial(Context context, File file, String partName) {
        RequestBody requestFile = RequestBody.create(MEDIA_TYPE_IMAGE, file);
        return MultipartBody.Part.createFormData(partName, context.getResources().getString(R.string.app_name), requestFile);

    }

    @NonNull
    public static RequestBody makeGSONRequestBody(Object jsonObject) {
        if (gson == null) {
            gson = new Gson();
        }
        return RequestBody.create(MEDIA_TYPE_TEXT, gson.toJson(jsonObject));
    }

    @NonNull
    public static String JSONResponse(Object jsonObject) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(jsonObject);
    }

    public static JSONArray JSONArray(Object jsonObject) {
        if (gson == null) {
            gson = new Gson();
        }
        try {
            return new JSONArray(String.valueOf(gson.toJsonTree(jsonObject).getAsJsonArray()));
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
        return null;
    }

    public static JSONObject JSONObject(Object jsonObject) {
        if (gson == null) {
            gson = new Gson();
        }
        try {
            return new JSONObject(String.valueOf(gson.toJsonTree(jsonObject).getAsJsonObject()));
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
        return null;
    }

    @NonNull
    public static RequestBody makeJSONRequestBody(JSONObject jsonObject) {
        String params = jsonObject.toString();
        return RequestBody.create(MEDIA_TYPE_TEXT, params);
    }

    @NonNull
    public static RequestBody makeTextRequestBody(Object stringData) {
        return RequestBody.create(MEDIA_TYPE_TEXT, String.valueOf(stringData));
    }

    private static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try {
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {

                AppLog.handleException(TAG, e);

                result = "";
            }
            cursor.close();
        }
        return result;
    }

    public static File getFromMediaUriPfd(Context context, ContentResolver resolver, Uri uri) {
        if (uri == null) return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFilename = getTempFilename(context);
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return new File(tempFilename);
        } catch (IOException ignored) {
            // Nothing we can do
        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }

    private static void closeSilently(@Nullable Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public Retrofit changeApiBaseUrl(String newApiBaseUrl) {
        OkHttpClient okHttpClient;
        if (BuildConfig.DEBUG) {
            // development build
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient().newBuilder().connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        } else {
            // production build
            okHttpClient = new OkHttpClient().newBuilder().connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build();
        }
        return new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).baseUrl(newApiBaseUrl).build();
    }

    public void changeAllApiBaseUrl(String baseUrl) {
        retrofit = changeApiBaseUrl(baseUrl);
    }
}