package com.whu.Gongyinchao.schoolservice.framework.location;

import android.text.TextUtils;

import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by panfei on 16/4/8.
 */
public final class LocationApiProvider {

    private LocationDataCenter dataCenter = LocationDataCenter.getInstance();
    private static volatile LocationApiProvider instance;

    private LocationApiProvider(){

    }

    public static LocationApiProvider getInstance(){
        if (instance == null){
            synchronized (LocationApiProvider.class){
                if (instance == null){
                    instance = new LocationApiProvider();
                }
            }
        }

        return instance;
    }

    public final String getCurrentLocAddrStrA() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocAddrStrA())){
            result = "";
        }

        return result;
    }

    public final String getCurrentLocDescribe() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocDescribe())){
            result = "";
        }

        return result;
    }

    public final String getCurrentLocCountry() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocCountry())){
            result = "";
        }

        return result;
    }

    public final String getCurrentLocProvince() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocProvince())){
            result = "";
        }

        return result;
    }

    public final String getCurrentLocCity() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocCity())){
            result = "";
        }

        return result.replace("市", "");
    }

    public final String getCurrentLocDirection() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocDirection())){
            result = "";
        }

        return result;
    }

    public final LatLng getCurrentLocLat() {
        double lat = 0, lon = 0;
        if ((lat = getCurrentLocLatitude()) == 0 || (lon = getCurrentLocLongitude()) == 0) {
            return null;
        }

        return new LatLng(lat, lon);
    }

    public final String getCurrentLocStreet() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocStreet())){
            result = "";
        }

        return result;
    }

    public final String getCurrentLocStreetNumber() {
        String result = null;
        if (TextUtils.isEmpty(result = dataCenter.getCurrentLocStreetNumber())){
            result = "";
        }

        return result;
    }

    public final double getCurrentLocLatitude() {
        return dataCenter.getCurrentLocLatitude();
    }

    public final double getCurrentLocLongitude() {
        return dataCenter.getCurrentLocLongitude();
    }

    // 0 - 在国外   1 - 在国内   2 － 未知
    public final int getCurrentLocationWhere(){
        return dataCenter.getCurrentLocationWhere();
    }

    public final boolean hasAddr() {
        return dataCenter.hasAddr();
    }

    public final float getCurrentSpeed() {
        return dataCenter.getCurrentSpeed();
    }

    public final List<Poi> getCurrentLocPoiList() {
        return dataCenter.getCurrentLocPoiList();
    }

}
