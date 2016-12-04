package com.whu.Gongyinchao.schoolservice.indoormodule.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.data.DetailWeatherData;
import com.whu.Gongyinchao.schoolservice.common.data.SimpleWeatherData;
import com.whu.Gongyinchao.schoolservice.common.data.WeatherData;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.WeatherCallBack;

/**
 * Created by panfei on 16/4/14.
 */
public class WeatherPresenter extends BasePresenter<WeatherCallBack>{

    public WeatherPresenter(Context context, WeatherCallBack callBack) {
        super(context, callBack);
    }

    public void request() {
        BaseApi.getInstance().getWeather(mCallBack.getRequestTag(), new NetApiProvider.UICallBack<WeatherData>() {
            @Override
            public void onResponse(WeatherData weatherData) {
                try{

                    WeatherData.HeWeatherDataBean dataBean = weatherData.getHeWeatherData().get(0);
                    DetailWeatherData detail = new DetailWeatherData().setAir("空气质量: " + dataBean.getAqi().getCity().getQlty())
                            .setWeather(dataBean.getNow().getCond().getTxt())
                            .setHot(dataBean.getNow().getTmp() + " ℃")
                            .setCloud(dataBean.getNow().getWind().getSc())
                            .setWater(dataBean.getNow().getHum())
                            .setTips(dataBean.getSuggestion().getComf().getTxt());

                    WeatherData.HeWeatherDataBean.DailyForecastBean today = dataBean.getDaily_forecast().get(0);
                    WeatherData.HeWeatherDataBean.DailyForecastBean tomorrow = dataBean.getDaily_forecast().get(1);

                    SimpleWeatherData simpleToday = new SimpleWeatherData().setAir(today.getWind().getSc())
                            .setDay("今天")
                            .setWeather(today.getCond().getTxt_d().equals(today.getCond().getTxt_n())? today.getCond().getTxt_d() :today.getCond().getTxt_d() + " 转 " + today.getCond().getTxt_n())
                            .setHot(today.getTmp().getMax() + " / " + today.getTmp().getMin() + " ℃");

                    SimpleWeatherData simpleTomorrow = new SimpleWeatherData().setAir(tomorrow.getWind().getSc())
                            .setDay("明天")
                            .setWeather(tomorrow.getCond().getTxt_d().equals(tomorrow.getCond().getTxt_n())? tomorrow.getCond().getTxt_d() :tomorrow.getCond().getTxt_d() + " 转 " + tomorrow.getCond().getTxt_n())
                            .setHot(tomorrow.getTmp().getMax() + " / " + tomorrow.getTmp().getMin() + " ℃");
                    mCallBack.weatherSuccess(detail, simpleToday, simpleTomorrow);

                }catch (NullPointerException e){
                    e.printStackTrace();
                    onErrorResponse(1001, "数据缺失");
                    request();
                }
            }

            @Override
            public void onErrorResponse(int errorCode, String message) {
                mCallBack.weatherFailed();
            }
        });
    }
}
