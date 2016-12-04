package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.data.ShareData;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorShareCallBack;
import com.whu.Gongyinchao.schoolservice.indoormodule.presenter.IndoorSharePresenter;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.adpter.ShareAdapter;

/**
 * Created by panfei on 16/4/26.
 */
public class IndoorShareActivity extends BaseActivity implements IndoorShareCallBack {

    private ListView mList;
    private ShareAdapter mAdapter;
    private IndoorSharePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indoor_course);

        mList = (ListView) findViewById(R.id.indoor_course_list);
        mAdapter = new ShareAdapter();
        mList.setAdapter(mAdapter);

        mPresenter = new IndoorSharePresenter(this, this);
        mPresenter.request();
    }

    @Override
    public void indoorShareSuccess(ShareData result) {
        mAdapter.setDataSet(result.getContent());
    }

    @Override
    public void indoorShareFailed(int code, String msg) {

    }
}
