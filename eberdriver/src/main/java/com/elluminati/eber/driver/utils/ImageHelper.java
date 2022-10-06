package com.elluminati.eber.driver.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.parse.ParseContent;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by elluminati on 29-Nov-16.
 */
public class ImageHelper {
    private final Context context;
    private final ParseContent parseContent;
    private final String TAG = this.getClass().getSimpleName();

    public ImageHelper(Context context) {
        parseContent = ParseContent.getInstance();
        this.context = context;
    }

    @Nullable
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

    public File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);


            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("CameraSample", "failed to create directory");
                    return null;
                }
            }


        } else {
            Log.v(context.getString(R.string.app_name), "External storage is not mounted " + "READ/WRITE.");
        }

        return storageDir;
    }

    public File createImageFile() {
        // Create an image file name
        Date date = new Date();
        String timeStamp = parseContent.dateFormat.format(date);
        timeStamp = timeStamp + "_" + parseContent.timeFormat.format(date);
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        File albumF = getAlbumDir();
        return new File(albumF, imageFileName);
    }

    public Uri getPhotosUri(String ImageFilePath) {
        Bitmap photoBitmap;
        int rotationAngle = 0;
        if (ImageFilePath != null && ImageFilePath.length() > 0) {

            try {
                int mobile_width = 480;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(ImageFilePath, options);
                int outWidth = options.outWidth;
                int ratio = (int) ((((float) outWidth) / mobile_width) + 0.5f);

                if (ratio == 0) {
                    ratio = 1;
                }
                ExifInterface exif = new ExifInterface(ImageFilePath);

                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotationAngle = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotationAngle = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotationAngle = 270;
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                        rotationAngle = 0;
                        break;
                    default:

                        break;
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = ratio;

                photoBitmap = BitmapFactory.decodeFile(ImageFilePath, options);
                if (photoBitmap != null) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotationAngle, (float) photoBitmap.getWidth() / 2, (float) photoBitmap.getHeight() / 2);
                    photoBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, photoBitmap.getWidth(), photoBitmap.getHeight(), matrix, true);
                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photoBitmap, Calendar.getInstance().getTimeInMillis() + ".jpg", null);
                    return Uri.parse(path);
                }
            } catch (OutOfMemoryError e) {
                AppLog.handleThrowable(TAG, e);
            } catch (IOException e) {
                AppLog.handleException(TAG, e);
            }
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.error_capture_image), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public String getRealPathFromURI(Uri contentURI) {
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

                Toast.makeText(context, context.getResources().getString(R.string.error_get_image), Toast.LENGTH_SHORT).show();

                result = "";
            }
            cursor.close();
        }
        return result;
    }
}
