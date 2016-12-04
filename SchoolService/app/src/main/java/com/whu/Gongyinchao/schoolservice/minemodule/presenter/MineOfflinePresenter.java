package com.whu.Gongyinchao.schoolservice.minemodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.minemodule.interfaces.MineOfflineCallBack;

import java.util.List;

/**
 * Created by panfei on 16/4/27.
 */
public class MineOfflinePresenter extends BasePresenter<MineOfflineCallBack> {

    public MineOfflinePresenter(Context context, MineOfflineCallBack mCallBack) {
        super(context, mCallBack);
    }

    public void checkOffline(String city) {
        if (TextUtils.isEmpty(city)) {
            mCallBack.checkOfflineFailed(-1);
            return;
        }

        List<MKOLSearchRecord> records = BaseApi.getInstance().searchCity(city);
        int cityId = -1;
        for (MKOLSearchRecord record : records) {
            if (city.equals(record.cityName.replace("市", ""))) {
                cityId = record.cityID;
                break;
            }
        }

        if (cityId > 0) {
            MKOLUpdateElement element = BaseApi.getInstance().getUpdateInfo(cityId);
            if (element == null) {
                mCallBack.checkOfflineFailed(cityId);
                return;
            }else {
                mCallBack.checkOfflineSuccess(element);
            }
        }
    }

    public void	getAllCity() {
        List<MKOLSearchRecord> records = BaseApi.getInstance().getOfflineCityList();
        if (records == null) {
            mCallBack.getAllCityFailed();
            return;
        }

        mCallBack.getAllCitySuccess(records);
    }
}
