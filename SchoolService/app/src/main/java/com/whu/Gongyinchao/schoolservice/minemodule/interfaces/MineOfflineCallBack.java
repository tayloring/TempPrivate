package com.whu.Gongyinchao.schoolservice.minemodule.interfaces;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

import java.util.List;

/**
 * Created by panfei on 16/4/27.
 */
public interface MineOfflineCallBack extends PresenterCallBack {

    void checkOfflineSuccess(MKOLUpdateElement element);

    void checkOfflineFailed(int cityId);

    void getAllCitySuccess(List<MKOLSearchRecord> elementList);

    void getAllCityFailed();
}
