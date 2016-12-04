package com.whu.Gongyinchao.schoolservice.indoormodule.interfaces;

import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/25.
 */
public interface IndoorClassCallBack extends PresenterCallBack {

    void indoorClassSuccess(CourseData result);

    void indoorClassFailed(int errorCode, String message);
}
