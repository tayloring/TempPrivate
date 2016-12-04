package com.whu.Gongyinchao.schoolservice.indoormodule.interfaces;

import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult;

/**
 * Created by panfei on 16/4/14.
 */
public interface IndoorCallBack extends PresenterCallBack {

    void indoorSuccess(CloudPoiResult cloudPoiResult);

    void indoorFailed(int errorCode, String message);
}
