package com.codelab.sunshine.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.codelab.sunshine.utility.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private TextView tvErrorMessage;
    private ProgressBar pbLoadingIndicator;

    private RecyclerView recyclerView;
    private ArrayList<ForecastModel> arrayList;
    private ForecastAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initVars();
        initViews();
        initFunctionality();
        initListeners();
        loadWeatherData();

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
        if(!Utils.isNetworkAvailable(mContext)) {
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

    private void loadWeatherData() {
        pbLoadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.GONE);

        RequestForecast requestForecast = new RequestForecast(mContext);
        requestForecast.buildParams("23.810332", "90.412518");
        requestForecast.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if (data != null) {
                    ArrayList<ForecastModel> allData = (ArrayList<ForecastModel>) data;
                    if (!allData.isEmpty()) {
                        arrayList.addAll(allData);
                    } else {
                        tvErrorMessage.setText(getString(R.string.no_data));
                    }
                } else {
                    tvErrorMessage.setText(getString(R.string.no_data));
                }
                pbLoadingIndicator.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        requestForecast.execute();
    }

}
