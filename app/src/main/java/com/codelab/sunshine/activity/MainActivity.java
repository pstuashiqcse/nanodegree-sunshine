package com.codelab.sunshine.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelab.sunshine.R;
import com.codelab.sunshine.adapter.ForecastAdapter;
import com.codelab.sunshine.api.helper.RequestForecast;
import com.codelab.sunshine.http.ResponseListener;
import com.codelab.sunshine.model.ForecastModel;
import com.codelab.sunshine.utility.LocationProviderTools;
import com.codelab.sunshine.utility.PermissionUtils;
import com.codelab.sunshine.utility.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private TextView tvErrorMessage;
    private ProgressBar pbLoadingIndicator;

    private RecyclerView recyclerView;
    private ArrayList<ForecastModel> arrayList;
    private ForecastAdapter mAdapter;

    private LocationProviderTools locationProviderTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initVars();
        initViews();
        initFunctionality();
        initListeners();

    }

    private void initVars() {
        mContext = MainActivity.this.getApplicationContext();
        arrayList = new ArrayList<>();
    }

    private void initViews() {
        tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pbLoadingIndicator = (ProgressBar) findViewById(R.id.pd_loading_indicator);
    }

    private void initFunctionality() {

        locationProviderTools = new LocationProviderTools(mContext);

        if (!Utils.isNetworkAvailable(mContext)) {
            Utils.showToast(mContext, getString(R.string.no_internet));
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ForecastAdapter(mContext, arrayList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListeners() {
        mAdapter.setItemClickListener(new ForecastAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
    }

    private void loadWeatherData(String latitude, String longitude) {
        pbLoadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.GONE);

        RequestForecast requestForecast = new RequestForecast(mContext);
        requestForecast.buildParams(latitude, longitude);
        requestForecast.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if (data != null) {
                    ArrayList<ForecastModel> allData = (ArrayList<ForecastModel>) data;
                    if (!allData.isEmpty()) {
                        arrayList.addAll(allData);
                    } else {
                        tvErrorMessage.setVisibility(View.VISIBLE);
                        tvErrorMessage.setText(getString(R.string.no_data));
                    }
                } else {
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    tvErrorMessage.setText(getString(R.string.no_data));
                }
                pbLoadingIndicator.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        requestForecast.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_LOCATION: {
                if (PermissionUtils.isPermissionResultGranted(grantResults)) {
                    loadLocation();
                }
            }
        }
    }


    private void loadLocation() {
        if (locationProviderTools.isGpsEnabled()) {

            pbLoadingIndicator.setVisibility(View.VISIBLE);
            tvErrorMessage.setVisibility(View.GONE);

            locationProviderTools.setLocationChangeListener(new LocationProviderTools.LocationChangeListener() {
                @Override
                public void onLocationUpdate(Location location) {

                    if (location != null && location.getLongitude() != 0) {
                        locationProviderTools.stopUsingGPS();
                        loadWeatherData(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                    }
                }
            });
            if (PermissionUtils.isPermissionGranted(MainActivity.this, PermissionUtils.LOCATION_PERMISSIONS, PermissionUtils.REQUEST_LOCATION)) {

                Location location = locationProviderTools.getLocation();
                if (location != null && location.getLongitude() != 0) {
                    locationProviderTools.stopUsingGPS();
                    loadWeatherData(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                }
            } else {
                pbLoadingIndicator.setVisibility(View.GONE);
                tvErrorMessage.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(getString(R.string.no_data));
            }
        } else {
            pbLoadingIndicator.setVisibility(View.GONE);
            tvErrorMessage.setVisibility(View.VISIBLE);
            tvErrorMessage.setText(getString(R.string.location_disabled));

            Snackbar.make(tvErrorMessage, getString(R.string.turn_on_gps), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).show();
        }
    }

}
