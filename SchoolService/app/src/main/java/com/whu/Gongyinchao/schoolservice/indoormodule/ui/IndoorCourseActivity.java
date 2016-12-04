package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorCourseCallBack;
import com.whu.Gongyinchao.schoolservice.indoormodule.presenter.IndoorCoursePresenter;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.adpter.CourseAdapter;

/**
 * Created by panfei on 16/4/26.
 */
public class IndoorCourseActivity extends BaseActivity implements IndoorCourseCallBack{

    private ListView mList;
    private CourseAdapter mAdapter;
    private IndoorCoursePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indoor_course);
        mList = (ListView) findViewById(R.id.indoor_course_list);

        mAdapter = new CourseAdapter();
        mList.setAdapter(mAdapter);
        mPresenter = new IndoorCoursePresenter(this, this);
        mPresenter.request();
    }

    @Override
    public void indoorCourseSuccess(CourseData result) {
        mAdapter.setDataSet(result.getCourses());
    }

    @Override
    public void indoorCourseFailed(int code, String msg) {

    }
}
