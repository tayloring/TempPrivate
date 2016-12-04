package com.whu.Gongyinchao.schoolservice.indoormodule.interfaces;

import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/26.
 */
public interface IndoorCourseCallBack extends PresenterCallBack{

    void indoorCourseSuccess(CourseData result);

    void indoorCourseFailed(int code, String msg);
}
