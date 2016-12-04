package com.whu.Gongyinchao.schoolservice.indoormodule.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorCourseCallBack;

/**
 * Created by panfei on 16/4/26.
 */
public class IndoorCoursePresenter extends BasePresenter<IndoorCourseCallBack> {

    public IndoorCoursePresenter(Context context, IndoorCourseCallBack mCallBack) {
        super(context, mCallBack);
    }

    public void request() {
        BaseApi.getInstance().getCourse(mCallBack.getRequestTag(), new NetApiProvider.UICallBack<CourseData>() {
            @Override
            public void onResponse(CourseData courseData) {
                mCallBack.indoorCourseSuccess(courseData);
            }

            @Override
            public void onErrorResponse(int errorCode, String message) {
                mCallBack.indoorCourseFailed(errorCode, message);
            }
        });
    }
}
