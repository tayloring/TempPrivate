package com.whu.Gongyinchao.schoolservice.indoormodule.interfaces;

import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;
import com.whu.Gongyinchao.schoolservice.common.data.DetailWeatherData;
import com.whu.Gongyinchao.schoolservice.common.data.SimpleWeatherData;

/**
 * Created by panfei on 16/4/14.
 */
public interface WeatherCallBack extends PresenterCallBack {

    void weatherSuccess(DetailWeatherData detailData, SimpleWeatherData simpleToday, SimpleWeatherData simpleTomorrow);

    void weatherFailed();
}
