package com.whu.Gongyinchao.schoolservice.indoormodule.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.constant.StringConstant;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorCallBack;

/**
 * Created by panfei on 16/4/14.
 */
public class IndoorPresenter extends BasePresenter<IndoorCallBack>{

    public IndoorPresenter(Context context, IndoorCallBack callBack) {
        super(context, callBack);
    }

    public void request() {
        BaseApi.getInstance().getPoi(mCallBack.getRequestTag(),StringConstant.BAIDUEXPRESSAK, StringConstant.BAIDUMAPEXPRESSID,
                BaseApi.getInstance().getCurrentLocLongitude(), BaseApi.getInstance().getCurrentLocLatitude(),
                400, new NetApiProvider.UICallBack<CloudPoiResult>() {
                    @Override
                    public void onResponse(CloudPoiResult cloudPoiResult) {
                        mCallBack.indoorSuccess(cloudPoiResult);
                    }

                    @Override
                    public void onErrorResponse(int errorCode, String message) {
                        mCallBack.indoorFailed(errorCode, message);
                    }
                });
    }
}
