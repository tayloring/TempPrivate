package com.whu.Gongyinchao.schoolservice.common.interfaces;

import com.baidu.location.BDLocation;

/**
 * Created by panfei on 16/4/16.
 */
public interface LocationCallBack {

    void locating();

    void locateFailed();

    void locateSuccess(BDLocation location);
}
