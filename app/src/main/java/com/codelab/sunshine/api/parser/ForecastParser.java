package com.codelab.sunshine.api.parser;


import com.codelab.sunshine.model.ForecastModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashiq on 11/18/17.
 */
public class ForecastParser {

    public ArrayList<ForecastModel> getForecastData(String response) {

        ArrayList<ForecastModel> arrayList = new ArrayList<>();
        try {


            JSONObject jsonObject = new JSONObject(response);

            JSONObject cityJson = jsonObject.getJSONObject("city");
            String city = cityJson.getString("name");

            JSONArray listJson = jsonObject.getJSONArray("list");

            for (int i = 0; i < listJson.length(); i++) {
                JSONObject listData = listJson.getJSONObject(i);
                String date = listData.getString("dt_txt");

                JSONArray weatherJsonArray = listData.getJSONArray("weather");
                JSONObject weatherJson = weatherJsonArray.getJSONObject(0);
                String condition = weatherJson.getString("main");
                String description = weatherJson.getString("description");
                String icon = weatherJson.getString("icon");

                JSONObject mainJson = listData.getJSONObject("main");
                String temp = mainJson.getString("temp");
                String tempMin = mainJson.getString("temp_min");
                String tempMax = mainJson.getString("temp_max");

                arrayList.add(new ForecastModel(date, condition, description, temp, tempMin, tempMax, icon));
            }

            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
