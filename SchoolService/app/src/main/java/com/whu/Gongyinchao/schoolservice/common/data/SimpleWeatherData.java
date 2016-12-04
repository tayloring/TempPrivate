package com.whu.Gongyinchao.schoolservice.common.data;

/**
 * Created by panfei on 16/4/14.
 */
public final class SimpleWeatherData {
    private String mDay;
    private String mAir;
    private String mWeather;
    private String mHot;

    public String getDay() {
        return mDay;
    }

    public SimpleWeatherData setDay(String mDay) {
        this.mDay = mDay;
        return this;
    }

    public String getAir() {
        return mAir;
    }

    public SimpleWeatherData setAir(String mAir) {
        this.mAir = mAir;
        return this;
    }

    public String getWeather() {
        return mWeather;
    }

    public SimpleWeatherData setWeather(String mWeather) {
        this.mWeather = mWeather;
        return this;
    }

    public String getHot() {
        return mHot;
    }

    public SimpleWeatherData setHot(String mHot) {
        this.mHot = mHot;
        return this;
    }
}
