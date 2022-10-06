package com.elluminati.eber.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.adapter.WalletHistoryAdapter;
import com.elluminati.eber.driver.interfaces.ClickListener;
import com.elluminati.eber.driver.interfaces.RecyclerTouchListener;
import com.elluminati.eber.driver.models.datamodels.WalletHistory;
import com.elluminati.eber.driver.models.responsemodels.WalletHistoryResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by elluminati on 01-Nov-17.
 */

public class WalletHistoryActivity extends BaseAppCompatActivity {

    public static final String TAG = WalletHistoryActivity.class.getName();
    private RecyclerView rcvWalletData;
    private ArrayList<WalletHistory> walletHistory;
    private WalletHistoryAdapter walletHistoryAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_wallet_history));
        rcvWalletData = findViewById(R.id.rcvWalletData);
        initRcvWalletHistory();
        getWalletHistory();
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }


    private void getWalletHistory() {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_loading), false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.USER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER_UNIQUE_NUMBER);


            Call<WalletHistoryResponse> call = ApiClient.getClient().create(ApiInterface.class).getWalletHistory(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<WalletHistoryResponse>() {
                @Override
                public void onResponse(Call<WalletHistoryResponse> call, Response<WalletHistoryResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (response.body().isSuccess()) {
                            walletHistory.addAll(response.body().getWalletHistory());
                            walletHistoryAdapter.notifyDataSetChanged();
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), WalletHistoryActivity.this);
                        }
                    }

                }

                @Override
                public void onFailure(Call<WalletHistoryResponse> call, Throwable t) {
                    AppLog.handleThrowable(WalletHistoryActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void initRcvWalletHistory() {
        walletHistory = new ArrayList<>();
        rcvWalletData.setLayoutManager(new LinearLayoutManager(this));
        walletHistoryAdapter = new WalletHistoryAdapter(this, walletHistory);
        rcvWalletData.setAdapter(walletHistoryAdapter);
        rcvWalletData.addOnItemTouchListener(new RecyclerTouchListener(this, rcvWalletData, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                goToWalletDetailActivity(walletHistory.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void goToWalletDetailActivity(WalletHistory walletHistory) {
        Intent intent = new Intent(this, WalletDetailActivity.class);
        intent.putExtra(Const.BUNDLE, walletHistory);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private int compareTwoDate(String firstStrDate, String secondStrDate) {
        try {
            SimpleDateFormat webFormat = parseContent.webFormat;
            SimpleDateFormat dateFormat = parseContent.dateTimeFormat;
            String date2 = dateFormat.format(webFormat.parse(secondStrDate));
            String date1 = dateFormat.format(webFormat.parse(firstStrDate));
            return date2.compareTo(date1);
        } catch (ParseException e) {
            AppLog.handleException(WalletHistoryActivity.class.getName(), e);
        }
        return 0;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

}
