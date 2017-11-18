package com.codelab.sunshine.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelab.sunshine.R;
import com.codelab.sunshine.api.helper.RequestForecast;
import com.codelab.sunshine.http.ResponseListener;
import com.codelab.sunshine.model.ForecastModel;
import com.codelab.sunshine.utility.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private TextView tvWeatherData;
    private ProgressBar pbLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initVars();
        initViews();
        initFunctionality();
        loadWeatherData();

    }

    private void initVars() {
        mContext = MainActivity.this.getApplicationContext();
    }

    private void initViews() {
        tvWeatherData = (TextView)findViewById(R.id.tv_weather_data);
        pbLoadingIndicator = (ProgressBar) findViewById(R.id.pd_loading_indicator);
    }

    private void initFunctionality() {
        if(!Utils.isNetworkAvailable(mContext)) {
            Utils.showToast(mContext, getString(R.string.no_internet));
        }
    }

    private void loadWeatherData() {
        pbLoadingIndicator.setVisibility(View.VISIBLE);
        tvWeatherData.setVisibility(View.GONE);

        RequestForecast requestForecast = new RequestForecast(mContext);
        requestForecast.buildParams("23.810332", "90.412518");
        requestForecast.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if (data != null) {
                    ArrayList<ForecastModel> arrayList = (ArrayList<ForecastModel>) data;

                    if (!arrayList.isEmpty()) {
                        tvWeatherData.setText("");
                        for (ForecastModel forecastModel : arrayList) {
                            tvWeatherData.append(forecastModel.getDay() + "\n" +
                                    forecastModel.getCondition() + ", " + forecastModel.getDescription() + "\n"
                                    + forecastModel.getTemp() + "\n\n\n");
                        }
                    } else {
                        tvWeatherData.setText(getString(R.string.no_data));
                    }
                } else {
                    tvWeatherData.setText(getString(R.string.no_data));
                }
                pbLoadingIndicator.setVisibility(View.GONE);
                tvWeatherData.setVisibility(View.VISIBLE);
            }
        });
        requestForecast.execute();
    }

}
