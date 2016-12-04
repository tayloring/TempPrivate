package com.whu.Gongyinchao.schoolservice.framework.offlinemap;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;

import java.util.List;

/**
 * Created by panfei on 16/4/8.
 */
public final class OfflineMapApiProvider {

    private volatile static OfflineMapApiProvider mapManager;
    private MKOfflineMap mOffline;

    private OfflineMapApiProvider(MKOfflineMapListener listener){
        mOffline = new MKOfflineMap();
        mOffline.init(listener);
    }

    public static OfflineMapApiProvider get(MKOfflineMapListener listener){
        synchronized (OfflineMapApiProvider.class){
            mapManager = new OfflineMapApiProvider(listener);
        }
        return mapManager;
    }

    public static OfflineMapApiProvider get(){
        if (mapManager == null){
            synchronized (OfflineMapApiProvider.class){
                if (mapManager == null){
                    mapManager = new OfflineMapApiProvider(null);
                }
            }
        }

        return mapManager;
    }

    public final void destory() {
        if (mOffline == null) {
            return;
        }

        mOffline.destroy();
        mapManager = null;
    }

    public final List<MKOLSearchRecord> getHotCityList() {
        if (mOffline == null) {
            return null;
        }

        return mOffline.getHotCityList();
    }

    public final List<MKOLUpdateElement> getAllUpdateInfo() {
        if (mOffline == null) {
            return null;
        }

        return mOffline.getAllUpdateInfo();
    }

    public final List<MKOLSearchRecord>	getOfflineCityList() {
        if (mOffline == null) {
            return null;
        }

        return mOffline.getOfflineCityList();
    }

    public final MKOLUpdateElement getUpdateInfo(int cityID) {
        if (mOffline == null) {
            return null;
        }

        return mOffline.getUpdateInfo(cityID);
    }

    public final void start(int cityID) {
        if (mOffline == null) {
            return;
        }

        mOffline.start(cityID);
    }

    public final void update(int cityID) {
        if (mOffline == null) {
            return;
        }

        mOffline.update(cityID);
    }

    public final void pause(int cityID) {
        if (mOffline == null) {
            throw new RuntimeException("MKOfflineMap is destoryed");
        }

        mOffline.pause(cityID);
    }

    public final void remove(int cityID) {
        if (mOffline == null) {
            throw new RuntimeException("MKOfflineMap is destoryed");
        }

        mOffline.remove(cityID);
    }

    public final List<MKOLSearchRecord>	searchCity(String cityName) {
        if (mOffline == null) {
            return null;
        }

        return mOffline.searchCity(cityName);
    }
}
