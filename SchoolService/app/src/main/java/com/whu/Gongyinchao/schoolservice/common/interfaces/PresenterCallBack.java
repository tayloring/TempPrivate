package com.whu.Gongyinchao.schoolservice.common.interfaces;

import com.baidu.location.BDLocation;

/**
 * Created by panfei on 16/4/10.
 */
public interface PresenterCallBack {
    void locating();

    void locateFailed();

    void locateSuccess(BDLocation location);
    Object getRequestTag();

}
