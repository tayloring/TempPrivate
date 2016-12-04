package com.whu.Gongyinchao.schoolservice.indoormodule.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.data.ShareData;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorShareCallBack;

/**
 * Created by panfei on 16/4/26.
 */
public class IndoorSharePresenter extends BasePresenter<IndoorShareCallBack>{

    public IndoorSharePresenter(Context context, IndoorShareCallBack mCallBack) {
        super(context, mCallBack);
    }

    public void request() {
        BaseApi.getInstance().getShare(mCallBack.getRequestTag(), new NetApiProvider.UICallBack<ShareData>() {
            @Override
            public void onResponse(ShareData shareData) {
                mCallBack.indoorShareSuccess(shareData);
            }

            @Override
            public void onErrorResponse(int errorCode, String message) {
                mCallBack.indoorShareFailed(errorCode, message);
            }
        });
    }
}
