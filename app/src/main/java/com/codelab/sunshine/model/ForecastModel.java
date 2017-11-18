package com.codelab.sunshine.model;

/**
 * Created by Ashiq on 11/18/17.
 */
public class ForecastModel {

    private String day;
    private String condition;
    private String description;
    private String temp;
    private String tempMin;
    private String tempMax;
    private String icon;

    public ForecastModel(String day, String condition,
                         String description, String temp,
                         String tempMin, String tempMax,
                         String icon) {
        this.day = day;
        this.condition = condition;
        this.description = description;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.icon = icon;
    }

    public String getDay() {
        return day;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getIcon() {
        return icon;
    }
}
