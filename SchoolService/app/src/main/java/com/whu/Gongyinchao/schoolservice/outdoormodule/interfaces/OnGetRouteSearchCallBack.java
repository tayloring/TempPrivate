package com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces;

import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/18.
 */
public interface OnGetRouteSearchCallBack extends PresenterCallBack {

    void onGetWalkingRouteResult(WalkingRouteResult result);

    void onGetTransitRouteResult(TransitRouteResult result);

    void onGetBikingRouteResult(BikingRouteResult bikingRouteResult);

}
