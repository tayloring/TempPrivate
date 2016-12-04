package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.data.SimpleWeatherData;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;

/**
 * Created by panfei on 16/4/14.
 */
public class SimpleWeather extends SSRelativeLayout {

    private TextView mDay;
    private TextView mAir;
    private TextView mWeather;
    private TextView mHot;

    public SimpleWeather(Context context) {
        this(context, null);
    }

    public SimpleWeather(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.simple_weather, this);
        mDay = (TextView) findViewById(R.id.simple_day);
        mAir = (TextView) findViewById(R.id.simple_air);
        mWeather = (TextView) findViewById(R.id.simple_weather);
        mHot = (TextView) findViewById(R.id.simple_hot);
    }

    public void setData (SimpleWeatherData data) {
        mDay.setText(data.getDay());
        mAir.setText(data.getAir());
        mWeather.setText(data.getWeather());
        mHot.setText(data.getHot());
    }

}
