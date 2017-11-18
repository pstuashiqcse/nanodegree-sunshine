package com.codelab.sunshine.api.params;

/**
 * Created by Ashiq on 11/18/17.
 */
public class HttpParams {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public static final String API_KEY = "9b21c9dd039513c150e6bcea87c57b9b";

    private static final String API_FORECAST = "forecast";
    public static String getForecastUrl() {
        return BASE_URL + API_FORECAST;
    }

    public static final String KEY_LAT = "lat";
    public static final String KEY_LONG = "lon";
    public static final String KEY_APP_ID = "APPID";

}
