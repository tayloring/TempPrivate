package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.data.DetailWeatherData;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;

/**
 * Created by panfei on 16/4/14.
 */
public class DetailWeather extends SSRelativeLayout {

    private TextView mAir;
    private TextView mWeather;
    private TextView mHot;
    private TextView mCloud;
    private TextView mWater;
    private TextView mTips;

    public DetailWeather(Context context) {
        this(context, null);
    }

    public DetailWeather(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.detail_weather, this);
        mAir = (TextView) findViewById(R.id.air);
        mWeather = (TextView) findViewById(R.id.weather);
        mHot = (TextView) findViewById(R.id.hot);
        mCloud = (TextView) findViewById(R.id.cloud);
        mWater = (TextView) findViewById(R.id.water);
        mTips = (TextView) findViewById(R.id.weather_tips);
    }

    public void setData(DetailWeatherData data) {
        mAir.setText(data.getAir());
        mWeather.setText(data.getWeather());
        mHot.setText(data.getHot());
        mCloud.setText(data.getCloud());
        mWater.setText(data.getWater());
        mTips.setText(data.getTips());
    }
}
