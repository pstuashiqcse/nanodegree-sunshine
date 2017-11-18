package com.codelab.sunshine.api.helper;

import android.content.Context;
import android.util.Log;

import com.codelab.sunshine.api.params.HttpParams;
import com.codelab.sunshine.api.parser.ForecastParser;
import com.codelab.sunshine.http.BaseHttp;
import com.codelab.sunshine.http.ResponseListener;

import java.util.HashMap;

/**
 * Created by Ashiq on 11/18/17.
 */
public class RequestForecast extends BaseHttp {

    private Object object;
    private ResponseListener responseListener;

    public RequestForecast(Context context) {
        super(context, HttpParams.getForecastUrl());
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String latitude, String longitude) {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.KEY_LAT, latitude);
        hashMap.put(HttpParams.KEY_LONG, longitude);
        hashMap.put(HttpParams.KEY_APP_ID, HttpParams.API_KEY);
        hashMap.put(HttpParams.KEY_UNIT, HttpParams.UNIT_METRIC);

        setParams(hashMap, GET);
    }


    @Override
    public void onBackgroundTask(String response) {
        object = new ForecastParser().getForecastData(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
