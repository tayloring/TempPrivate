package com.whu.Gongyinchao.schoolservice.indoormodule.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorClassCallBack;

/**
 * Created by panfei on 16/4/25.
 */
public class IndoorClassPresenter extends BasePresenter<IndoorClassCallBack> {

    public IndoorClassPresenter(Context context, IndoorClassCallBack mCallBack) {
        super(context, mCallBack);
    }

    public void request() {
        BaseApi.getInstance().getClass(mCallBack.getRequestTag(), new NetApiProvider.UICallBack<CourseData>() {
            @Override
            public void onResponse(CourseData courseData) {
                mCallBack.indoorClassSuccess(courseData);
            }

            @Override
            public void onErrorResponse(int errorCode, String message) {
                mCallBack.indoorClassFailed(errorCode, message);
            }
        });
    }
}
