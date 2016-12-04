package com.whu.Gongyinchao.schoolservice.outdoormodule.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.BusLineSearchCallBack;

/**
 * Created by panfei on 16/4/17.
 */
public class BusLineSearchPresenter extends BasePresenter<BusLineSearchCallBack>{

    private BusLineSearch busLineSearch;
    private OnGetBusLineSearchResultListener listener;

    public BusLineSearchPresenter(Context context, BusLineSearchCallBack callBack) {
        super(context, callBack);
        busLineSearch = BusLineSearch.newInstance();
        listener = new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                if (busLineResult != null && busLineResult.error == SearchResult.ERRORNO.NO_ERROR && mCallBack != null) {
                    mCallBack.onBusLineSearchSuccess(busLineResult);
                }else if (mCallBack != null && busLineResult != null){
                    mCallBack.onBusLineSearchFailed(busLineResult.error);
                }
            }
        };

        busLineSearch.setOnGetBusLineSearchResultListener(listener);
    }

    public void searchBusLine(final String city, @NonNull final String uid) {

        final String[] aCity = new String[] { city };
        IPrensenterRequest request = new IPrensenterRequest() {
            @Override
            public void request() {
                if (TextUtils.isEmpty(aCity[0])) {
                    aCity[0] = BaseApi.getInstance().getCurrentLocCity();
                }

                BusLineSearchOption option = new BusLineSearchOption();
                option.city(aCity[0]).uid(uid);
                busLineSearch.searchBusLine(option);
            }
        };

        if (TextUtils.isEmpty(aCity[0])) {
            doAfterLocate(request);
        }else {
            doDirect(request);
        }

    }

    @Override
    public void onDestory() {
        busLineSearch.destroy();
        super.onDestory();
    }
}
