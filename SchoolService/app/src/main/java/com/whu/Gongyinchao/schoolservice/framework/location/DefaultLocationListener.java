package com.whu.Gongyinchao.schoolservice.framework.location;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by panfei on 16/4/7.
 */
public class DefaultLocationListener implements BDLocationListener {

    private PresenterLocationCallBack callBack;

    public void setCallBack(PresenterLocationCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        LocationDataCenter.getInstance().updateLocationInfo(bdLocation);
        if (callBack != null) {
            callBack.onPresenterLocationCallBack();
            callBack = null;
        }
    }
}
