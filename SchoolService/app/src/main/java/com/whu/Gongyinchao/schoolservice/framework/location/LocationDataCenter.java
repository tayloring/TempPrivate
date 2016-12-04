package com.whu.Gongyinchao.schoolservice.framework.location;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;

import java.util.List;
import java.util.Observable;

/**
 * Created by panfei on 16/4/7.
 */
final class LocationDataCenter extends Observable {

    private BDLocation mCurrentLocation;
    private int mCount;
    private static volatile LocationDataCenter mInstance;
    private final String[] type = new String[]{
            "TypeGpsLocation",
            "TypeCriteriaException",
            "TypeNetWorkException",
            "",
            "TypeCacheLocation",
            "TypeOffLineLocation",
            "TypeOffLineLocationFail",
            "TypeOffLineLocationNetworkFail",
            "TypeNetWorkLocation",
            "TypeServerError"
    };

    private LocationDataCenter(){

    }

    public static LocationDataCenter getInstance(){

        if (mInstance == null){
            synchronized (LocationDataCenter.class){
                if (mInstance == null){
                    mInstance = new LocationDataCenter();
                }
            }
        }

        return mInstance;
    }


    public BDLocation getCurrentLocation() {
        return mCurrentLocation;
    }

    public void setUpdateState(){
        setChanged();
        notifyObservers(new String("正在定位"));
    }

    public void updateLocationInfo(BDLocation location){

        // 百度sdk官方说明第一次定位可能不准确,所以认为连续两次以上定位在同一区为有效
        if (mCurrentLocation != null && location != null
                && mCurrentLocation.getDistrict() != null && mCurrentLocation.getDistrict().equals(location.getDistrict())){
            if (mCount < 0) {
                mCount = 1;
            }else {
                mCount ++;
            }
        }else {
            if (mCount > 0){
                mCount = 0;
            }else {
                mCount --;
            }
        }

        this.mCurrentLocation = location;

        if (mCount >= 2 || mCount < 0){
            setChanged();
            notifyObservers(location);
        }
    }

    public final String getCurrentLocType() {
        int typeCode = mCurrentLocation.getLocType();
        if (typeCode <= 68){
            return type[typeCode - 61];
        }else if (typeCode == 161){
            return type[8];
        }else {
            return type[9];
        }
    }

    public final String getCurrentLocAddrStrA() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getAddrStr();
    }

    public final String getCurrentLocDescribe() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getLocationDescribe();
    }

    public final String getCurrentLocCountry() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getCountry();
    }

    public final String getCurrentLocProvince() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getProvince();
    }

    public final String getCurrentLocCity() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getCity();
    }

    public final String getCurrentLocDirection() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getDistrict();
    }

    public final String getCurrentLocStreet() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getStreet();
    }

    public final String getCurrentLocStreetNumber() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getStreetNumber();
    }

    public final double getCurrentLocLatitude() {
        if (mCurrentLocation == null) {
            return 0;
        }

        return mCurrentLocation.getLatitude();
    }

    public final double getCurrentLocLongitude() {
        if (mCurrentLocation == null) {
            return 0;
        }

        return mCurrentLocation.getLongitude();
    }

    // 0 - 在国外   1 - 在国内   2 － 未知
    public final int getCurrentLocationWhere(){
        if (mCurrentLocation == null) {
            return 2;
        }

        return mCurrentLocation.getLocationWhere();
    }

    public final boolean hasAddr() {
        if (mCurrentLocation == null) {
            return false;
        }

        return mCurrentLocation.hasAddr();
    }

    public final float getCurrentSpeed() {
        if (mCurrentLocation == null) {
            return 0;
        }

        return mCurrentLocation.getSpeed();
    }

    public final List<Poi> getCurrentLocPoiList() {
        if (mCurrentLocation == null) {
            return null;
        }

        return mCurrentLocation.getPoiList();
    }

}
