package com.whu.Gongyinchao.schoolservice.minemodule.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.minemodule.interfaces.MineOfflineCallBack;
import com.whu.Gongyinchao.schoolservice.minemodule.presenter.MineOfflinePresenter;

import java.util.List;

/**
 * Created by panfei on 16/4/27.
 */
public class MineOfflineActivity extends BaseActivity implements MineOfflineCallBack, MKOfflineMapListener{

    private MineOfflinePresenter mPresenter;
    private TextView mCurrentCityTitle;
    private TextView mCurrentCityStatus;
    private TextView mCurrentCityRadio;
    private Button mCurrentCityDelete;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_offline);

        mPresenter = new MineOfflinePresenter(this, this);
        mCurrentCityTitle = (TextView) findViewById(R.id.current_city_title);
        mCurrentCityStatus = (TextView) findViewById(R.id.current_city_status);
        mCurrentCityRadio = (TextView) findViewById(R.id.current_city_radio);
        mCurrentCityDelete = (Button) findViewById(R.id.current_city_delete);
    }

    @Override
    public void locateSuccess(BDLocation location) {
        mPresenter.checkOffline(BaseApi.getInstance().getCurrentLocCity());
        super.locateSuccess(location);
    }

    @Override
    public void checkOfflineSuccess(final MKOLUpdateElement element) {

        mCurrentCityTitle.setText(element.cityName);

        mCurrentCityStatus.setText(element.update? "可更新" : "最新");
        if (element.update || element.ratio < 100) {
            mCurrentCityStatus.setClickable(true);
            mCurrentCityStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseApi.getInstance().update(element.cityID, MineOfflineActivity.this);
                }
            });
        }else {
            mCurrentCityStatus.setClickable(false);
        }

        mCurrentCityRadio.setText(element.ratio + "%");
        if (element.ratio < 100) {
            mCurrentCityStatus.setText("未下载");
            mCurrentCityRadio.setClickable(true);
            mCurrentCityRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseApi.getInstance().start(element.cityID, MineOfflineActivity.this);
                }
            });
        }else {
            mCurrentCityRadio.setClickable(false);
        }

        mCurrentCityDelete.setClickable(true);
        mCurrentCityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApi.getInstance().remove(element.cityID);
                mCurrentCityDelete.setClickable(false);
                mPresenter.checkOffline(BaseApi.getInstance().getCurrentLocCity());
            }
        });
    }

    @Override
    public void checkOfflineFailed(final int cityId) {
        if (cityId < 0) {
            showToast("离线地图获取失败");
            return;
        }

        mCurrentCityTitle.setText(BaseApi.getInstance().getCurrentLocCity() + "市");

        mCurrentCityStatus.setText("未下载");
        mCurrentCityStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApi.getInstance().start(cityId, MineOfflineActivity.this);
            }
        });

        mCurrentCityRadio.setText("0%");
        mCurrentCityDelete.setClickable(false);
    }

    @Override
    public void getAllCitySuccess(List<MKOLSearchRecord> elementList) {

    }

    @Override
    public void getAllCityFailed() {

    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:
                mPresenter.checkOffline(BaseApi.getInstance().getCurrentLocCity());
                break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                mPresenter.checkOffline(BaseApi.getInstance().getCurrentLocCity());
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        BaseApi.getInstance().destoryOffline();
        super.onDestroy();
    }
}
