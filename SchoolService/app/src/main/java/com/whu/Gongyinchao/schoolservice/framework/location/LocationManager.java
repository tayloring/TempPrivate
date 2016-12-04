package com.whu.Gongyinchao.schoolservice.framework.location;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.Observer;

/**
 * Created by panfei on 16/4/7.
 */
public abstract class LocationManager {

    private static LocationClient mLocationClient;
    private BDLocationListener mListener = createLocationListener();
    private static Object mLock = new Object();

    protected abstract LocationClientOption createLocationClientOption();

    protected BDLocationListener createLocationListener() {
        return new DefaultLocationListener();
    }

    public LocationManager(Context context){
        synchronized (mLock) {
            if (mLocationClient == null){
                mLocationClient = new LocationClient(context.getApplicationContext());
                mLocationClient.setLocOption(createLocationClientOption());
                mLocationClient.registerLocationListener(mListener);
            }
        }
    }

    public void registerLocationListener(Observer observer){
        LocationDataCenter.getInstance().addObserver(observer);
    }

    public void unRegisterLocationListener(Observer observer){
        LocationDataCenter.getInstance().deleteObserver(observer);
    }

    public void unRegisterAllLocationListener(){
        LocationDataCenter.getInstance().deleteObservers();
    }

    public static void start() {
        synchronized (mLock) {
            if(mLocationClient != null && !mLocationClient.isStarted()){
                LocationDataCenter.getInstance().setUpdateState();
                mLocationClient.start();
            }
        }
    }

    public void stop() {
        synchronized (mLock) {
            if(mLocationClient != null && mLocationClient.isStarted()){
                mLocationClient.stop();
            }
        }
    }

    public boolean setLocOption(LocationClientOption option){
        boolean isSuccess = false;
        if(option != null){
            if(mLocationClient.isStarted())
                mLocationClient.stop();
            mLocationClient.setLocOption(option);
            isSuccess = true;
        }

        return isSuccess;
    }

    public LocationClientOption getLocationClientOption(){
        return mLocationClient.getLocOption();
    }

    public BDLocationListener getLocationListener() {
        return mListener;
    }
}
