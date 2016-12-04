package com.whu.Gongyinchao.schoolservice.indoormodule.interfaces;

import com.whu.Gongyinchao.schoolservice.common.data.ShareData;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/26.
 */
public interface IndoorShareCallBack extends PresenterCallBack{

    void indoorShareSuccess(ShareData result);

    void indoorShareFailed(int code, String msg);
}
