package com.whu.Gongyinchao.schoolservice.outdoormodule.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnGetRouteSearchCallBack;

/**
 * Created by panfei on 16/4/18.
 */
public class RouteSearchPresenter extends BasePresenter<OnGetRouteSearchCallBack> {

    private static final int WALK = 0;
    private static final int BIKE = 1;
    private static final int BUS = 2;

    RoutePlanSearch mRouteSearch;
    OnGetRoutePlanResultListener listener;

    public RouteSearchPresenter(Context context, OnGetRouteSearchCallBack callBack) {
        super(context, callBack);

        mRouteSearch = RoutePlanSearch.newInstance();
        listener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if (mCallBack != null && walkingRouteResult != null) {
                    mCallBack.onGetWalkingRouteResult(walkingRouteResult);
                }else {
                    showToast();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                if (mCallBack != null && transitRouteResult != null) {
                    mCallBack.onGetTransitRouteResult(transitRouteResult);
                }else {
                    showToast();
                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                showToast();
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                if (mCallBack != null && bikingRouteResult != null) {
                    mCallBack.onGetBikingRouteResult(bikingRouteResult);
                }else {
                    showToast();
                }
            }
        };

        mRouteSearch.setOnGetRoutePlanResultListener(listener);
    }

    public  void searchOnWalk (@NonNull final String city, @NonNull final String start, @NonNull final String end) {
        searchRoute(city, start, end, WALK);
    }

    public void searchOnBus (@NonNull final String city, @NonNull final String start, @NonNull final String end) {
        searchRoute(city, start, end, BUS);
    }

    public void searchOnBike (@NonNull final String city, @NonNull final String start, @NonNull final String end) {
        searchRoute(city, start, end, BIKE);
    }

    private void searchRoute(@NonNull final String city, @NonNull final String start, @NonNull final String end, final int type) {

        if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
            showToast();
            return;
        }

        final String[] aCity = new String[] { city };
        IPrensenterRequest request = new IPrensenterRequest() {
            @Override
            public void request() {
                if (TextUtils.isEmpty(aCity[0])) {
                    aCity[0] = BaseApi.getInstance().getCurrentLocCity();
                }

                PlanNode node1 = null;
                if ("当前位置".equals(start)){
                    node1 = PlanNode.withLocation(BaseApi.getInstance().getCurrentLocLat());
                }else {
                    node1 = PlanNode.withCityNameAndPlaceName(aCity[0], start);
                }

                PlanNode node2 = null;

                if ("当前位置".equals(end)) {
                    node2 = PlanNode.withLocation(BaseApi.getInstance().getCurrentLocLat());
                }else {
                    node2 = PlanNode.withCityNameAndPlaceName(aCity[0], end);
                }

                switch (type) {
                    case WALK :
                        WalkingRoutePlanOption option1 = new WalkingRoutePlanOption().from(node1).to(node2);
                        mRouteSearch.walkingSearch(option1);
                        break;
                    case BIKE :
                        BikingRoutePlanOption option2 = new BikingRoutePlanOption().from(node1).to(node2);
                        mRouteSearch.bikingSearch(option2);
                        break;
                    case BUS :
                        TransitRoutePlanOption option3 = new TransitRoutePlanOption().from(node1).to(node2).city(aCity[0]);
                        mRouteSearch.transitSearch(option3);
                        break;
                }
            }
        };

        if (TextUtils.isEmpty(aCity[0])) {
            doAfterLocate(request);
        }else {
            doDirect(request);
        }
    }

    private void showToast() {
        ((BaseActivity)mContext.get()).showToast("没有找到结果");
    }

    @Override
    public void onDestory() {
        super.onDestory();
        mRouteSearch.destroy();
    }
}
