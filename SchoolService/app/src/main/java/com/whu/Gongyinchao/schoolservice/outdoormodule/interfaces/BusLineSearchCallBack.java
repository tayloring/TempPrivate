package com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/17.
 */
public interface BusLineSearchCallBack extends PresenterCallBack{

    void onBusLineSearchSuccess(BusLineResult busLineResult);

    void onBusLineSearchFailed(BusLineResult.ERRORNO error);
}
