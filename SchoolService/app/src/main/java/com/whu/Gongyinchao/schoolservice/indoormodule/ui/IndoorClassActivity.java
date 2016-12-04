package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorClassCallBack;
import com.whu.Gongyinchao.schoolservice.indoormodule.presenter.IndoorClassPresenter;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.adpter.CourseAdapter;

import java.util.List;

/**
 * Created by panfei on 16/4/25.
 */
public class IndoorClassActivity extends BaseActivity implements IndoorClassCallBack {

    private IndoorClassPresenter mPresenter;

    private ImageView mBack;
    private TextView mNearTime;
    private ListView mCourselist;
    private CourseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indoor_class);

        mBack = (ImageView) findViewById(R.id.indoor_class_back);
        mNearTime = (TextView) findViewById(R.id.indoor_class_neartime);
        mCourselist = (ListView) findViewById(R.id.indoor_class_list);

        mAdapter = new  CourseAdapter();
        mCourselist.setAdapter(mAdapter);

        mPresenter = new IndoorClassPresenter(this, this);
        mPresenter.request();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void indoorClassSuccess(CourseData result) {
        mAdapter.setDataSet(result.getCourses());
        mNearTime.setText(timeFormat(result.getNearTime()));
    }

    private String timeFormat(List<Integer> nearTime) {
        return nearTime.get(0) + " : " + nearTime.get(1);
    }

    @Override
    public void indoorClassFailed(int errorCode, String message) {
        showToast("数据出错");
    }
}
