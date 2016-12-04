package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.data.DetailWeatherData;
import com.whu.Gongyinchao.schoolservice.common.data.SimpleWeatherData;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.WeatherCallBack;
import com.whu.Gongyinchao.schoolservice.indoormodule.presenter.WeatherPresenter;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;

/**
 * Created by panfei on 16/4/14.
 */

public class WeatherView extends SSRelativeLayout implements WeatherCallBack, DestoryListener {

    private DetailWeather mDetail;
    private SimpleWeather mTodaySimple;
    private SimpleWeather mTomorrowSimple;

    private WeatherPresenter mPresenter;

    public WeatherView(Context context) {
        this(context, null);
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.indoor_weather, this);
        mPresenter = new WeatherPresenter(getContext(), this);
        mDetail = (DetailWeather) findViewById(R.id.detail_weather);
        //mTodaySimple = (SimpleWeather) findViewById(R.id.today_simple);
        mTomorrowSimple = (SimpleWeather) findViewById(R.id.torrow_simple);
    }

    public void setData(DetailWeatherData detailData, SimpleWeatherData simpleToday, SimpleWeatherData simpleTomorrow) {
        mDetail.setData(detailData);
//        mTodaySimple.setData(simpleToday);
        mTomorrowSimple.setData(simpleTomorrow);
    }

    public void request() {
        mPresenter.request();
    }

    @Override
    public void weatherSuccess(DetailWeatherData detailData, SimpleWeatherData simpleToday, SimpleWeatherData simpleTomorrow) {
        setData(detailData, simpleToday, simpleTomorrow);
    }

    @Override
    public void weatherFailed() {
        ((BaseActivity)getContext()).showToast("天气获取失败");
    }

    @Override
    public void onDestory() {
        mPresenter.onDestory();
    }
}
