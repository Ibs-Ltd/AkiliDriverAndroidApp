package com.elluminati.eber.driver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elluminati on 30-03-2016.
 * <p>
 * This Class is handle a Notification which send by Google FCM server.
 */
public class FcmMessagingService extends FirebaseMessagingService {

    public static final String ID = "id";
    public static final String PROVIDER_DECLINE = "208";
    public static final String PROVIDER_APPROVED = "207";
    public static final String PROVIDER_HAVE_NEW_TRIP = "201";
    public static final String USER_CANCEL_TRIP = "205";
    public static final String USER_DESTINATION_UPDATE = "210";
    public static final String PAYMENT_CASH = "211";
    public static final String PAYMENT_CARD = "212";
    public static final String LOG_OUT = "230";
    public static final String PROVIDER_TRIP_END = "241";
    public static final String PROVIDER_OFFLINE = "242";
    public static final String TRIP_ACCEPTED_BY_ANOTHER_PROVIDER = "232";
    public static final String TRIP_CANCEL_BY_ADMIN = "218";
    public static final String PUSH_CODE_FOR_TRIP_PAYMENT_FAILED_CASH = "233";
    public static final String PUSH_CODE_FOR_TRIP_PAYMENT_FAILED_WALLET = "234";
    private static final String CHANNEL_ID = "eberdriver2019";
    private static final String CHANNEL_ID_NEW_TRIP = "newTrip";
    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        AppLog.Log(Const.Tag.FCM_MESSAGING_SERVICE, "From:" + remoteMessage.getFrom());
        AppLog.Log(Const.Tag.FCM_MESSAGING_SERVICE, "Data:" + remoteMessage.getData());

        String message = remoteMessage.getData().get(ID);
        if (message != null && !message.isEmpty()) {
            tripStatus(message);
        }
    }

    private void sendNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Trip Status", NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            mChannel.enableLights(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyIntent = PendingIntent.getActivity(FcmMessagingService.this, 0, intent, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification.Builder notificationBuilder = new Notification.Builder(this).setColor(ResourcesCompat.getColor(getResources(), R.color.color_app_theme_dark, null)).setContentTitle(this.getResources().getString(R.string.app_name)).setContentText(message).setAutoCancel(true).setSmallIcon(getNotificationIcon()).setContentIntent(notifyIntent).setPriority(Notification.PRIORITY_DEFAULT).setVisibility(Notification.VISIBILITY_PUBLIC).setCategory(Notification.CATEGORY_MESSAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID); // Channel ID
        }

        if (PreferenceHelper.getInstance(this).getIsPushNotificationSoundOn() && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
        }
        notificationManager.notify(Const.PUSH_NOTIFICATION_ID, notificationBuilder.build());
    }

    private void sendNewTripNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.request_sound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_NEW_TRIP, "Trip Status", NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            mChannel.enableLights(true);
            mChannel.setSound(soundUri, attributes);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(this, MainDrawerActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        intent.putExtra(Const.Params.IS_FROM_NOTIFICATION, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        //PendingIntent notifyIntent = PendingIntent.getActivity(FcmMessagingService.this, 90, intent, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent notifyIntent = stackBuilder.getPendingIntent(90, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_UPDATE_CURRENT);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setColor(ResourcesCompat.getColor(getResources(), R.color.color_app_theme_dark, null))
                .setContentTitle(this.getResources().getString(R.string.app_name)).setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(getNotificationIcon())
                .setContentIntent(notifyIntent)
                .setFullScreenIntent(notifyIntent, true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(soundUri, AudioManager.STREAM_NOTIFICATION)
                .setCategory(NotificationCompat.CATEGORY_EVENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID_NEW_TRIP); // Channel ID
        }

        if (PreferenceHelper.getInstance(this).getIsPushNotificationSoundOn() && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
        }
        notificationManager.notify(Const.PUSH_NEW_TRIP_NOTIFICATION_ID, notificationBuilder.build());

        if (!PreferenceHelper.getInstance(FcmMessagingService.this).getIsMainScreenVisible()) {
            Intent mapIntent = new Intent(FcmMessagingService.this, MainDrawerActivity.class);
            mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mapIntent.putExtra(Const.Params.IS_FROM_NOTIFICATION, true);
            startActivity(mapIntent);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.request_sound);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_NEW_TRIP, "Trip Status", NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            mChannel.enableLights(true);
            mChannel.setSound(soundUri, attributes);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_stat_eber : R.mipmap.ic_launcher;
    }

    private void tripStatus(String status) {
        switch (status) {
            case PROVIDER_APPROVED:
                sendNotification(getMessage(status));
                sendGlobalBroadcast(Const.ACTION_APPROVED_PROVIDER);
                break;
            case PROVIDER_DECLINE:
                sendNotification(getMessage(status));
                sendGlobalBroadcast(Const.ACTION_DECLINE_PROVIDER);
                break;
            case USER_CANCEL_TRIP:
                sendNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_CANCEL_TRIP);
                break;
            case PROVIDER_HAVE_NEW_TRIP:
                sendNewTripNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_NEW_TRIP);
                break;
            case USER_DESTINATION_UPDATE:
                sendNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_DESTINATION_UPDATE);
                break;
            case PAYMENT_CARD:
                sendNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_PAYMENT_CARD);
                break;
            case PAYMENT_CASH:
                sendNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_PAYMENT_CASH);
                break;
            case LOG_OUT:
                sendNotification(getMessage(status));
                goToMainActivity();
                break;
            case PROVIDER_TRIP_END:
                sendLocalBroadcast(Const.ACTION_PROVIDER_TRIP_END);
                sendNotification(getMessage(status));
                break;
            case PROVIDER_OFFLINE:
                sendNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_PROVIDER_OFFLINE);
                break;
            case TRIP_ACCEPTED_BY_ANOTHER_PROVIDER:
                sendLocalBroadcast(Const.ACTION_TRIP_ACCEPTED_BY_ANOTHER_PROVIDER);
                sendNotification(getMessage(status));
                break;
            case TRIP_CANCEL_BY_ADMIN:
                sendNotification(getMessage(status));
                sendLocalBroadcast(Const.ACTION_TRIP_CANCEL_BY_ADMIN);
                break;
            case PUSH_CODE_FOR_TRIP_PAYMENT_FAILED_CASH:
            case PUSH_CODE_FOR_TRIP_PAYMENT_FAILED_WALLET:
                sendNotification(getMessage(status));
                break;
            default:
                sendNotification(status);
                break;
        }
    }

    private String getMessage(String code) {
        String msg = "";
        String messageCode = Const.PUSH_MESSAGE_PREFIX + code;
        try {
            Configuration conf = new Configuration(this.getApplicationContext().getResources().getConfiguration());
            Locale locale = new Locale(PreferenceHelper.getInstance(this).getLanguageCode());
            conf.setLocale(locale);
            msg = this.getApplicationContext().createConfigurationContext(conf).getResources().getString(this.getResources().getIdentifier(messageCode, Const.STRING, this.getPackageName()));

        } catch (Resources.NotFoundException e) {
            msg = code;
        }
        return msg;
    }

    private void sendLocalBroadcast(String action) {
        Intent intent = new Intent(action);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void sendGlobalBroadcast(String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    public void goToMainActivity() {
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(this);
        preferenceHelper.logout();
        Intent sigInIntent = new Intent(this, MainActivity.class);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sigInIntent);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        PreferenceHelper.getInstance(this).putDeviceToken(token);
        if (PreferenceHelper.getInstance(this).getSessionToken() != null) {
            updateDeviceTokenOnServer(token);
        }
        createNotificationChannel();
    }


    private void updateDeviceTokenOnServer(String deviceToken) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Const.Params.TOKEN, PreferenceHelper.getInstance(this).getSessionToken());
            jsonObject.accumulate(Const.Params.PROVIDER_ID, PreferenceHelper.getInstance(this).getProviderId());
            jsonObject.accumulate(Const.Params.DEVICE_TOKEN, deviceToken);

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).updateDeviceToken(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            Utils.showMessageToast(response.body().getMessage(), FcmMessagingService.this);
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), FcmMessagingService.this);
                        }
                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(FcmMessagingService.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException("FCM Token Refresh", e);
        }
    }

}