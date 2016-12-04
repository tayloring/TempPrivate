package com.whu.Gongyinchao.schoolservice.outdoormodule.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.constant.StringConstant;
import com.whu.Gongyinchao.schoolservice.common.uikit.MapUtilActivity;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.OutdoorView;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnPoiSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.presenter.PoiSearchPresenter;


/**
 * Created by panfei on 16/4/12.
 */
public class OutdoorSelecter extends SSRelativeLayout implements View.OnClickListener, OnPoiSearchCallBack<PoiResult>, DestoryListener {

    private TextView mSearch;
    private TextView mRoute;
    private TextView mGuide;
    private TextView mPaly;
    private TextView mBank;
    private TextView mStation;
    private PoiSearchPresenter<PoiResult> mPoiSearchPresenter;
    private OnPoiSearchCallBack callBack;

    public OutdoorSelecter(Context context) {
        this(context, null);
    }

    public OutdoorSelecter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setCallBack(OnPoiSearchCallBack callBack) {
        this.callBack = callBack;
    }

    private void init() {
        inflate(getContext(), R.layout.outdoor_selecter, this);
        mSearch = (TextView) findViewById(R.id.search);
        mRoute = (TextView) findViewById(R.id.route);
        mGuide = (TextView) findViewById(R.id.guide);
        mPaly = (TextView) findViewById(R.id.play);
        mBank = (TextView) findViewById(R.id.bank);
        mStation = (TextView) findViewById(R.id.station);

        mSearch.setOnClickListener(this);
        mRoute.setOnClickListener(this);
        mGuide.setOnClickListener(this);
        mPaly.setOnClickListener(this);
        mBank.setOnClickListener(this);
        mStation.setOnClickListener(this);

        mPoiSearchPresenter = new PoiSearchPresenter<>(getContext(), this);
    }


    @Override
    public void onClick(View v) {
        if (callBack != null) {
            ((OutdoorView)callBack).mapClear();
        }

        if (v == mStation) {
            mPoiSearchPresenter.searchNearby(StringConstant.BAIDUMAPCLOUDAK,StringConstant.BAIDUMAPCLOUDPOIID,BaseApi.getInstance().getCurrentLocLat(),
                    "公交站", 0, 2000, PoiSortType.distance_from_near_to_far, 50);
        }else if (v == mSearch) {
            getContext().startActivity(new Intent(getContext(), MapUtilActivity.class));
        }else if (v == mRoute) {
            Intent intent = new Intent(getContext(), MapUtilActivity.class);
            intent.putExtra("use", "route");
            getContext().startActivity(intent);
        }else if (v == mGuide) {
            Intent intent = new Intent(getContext(), MapUtilActivity.class);
            intent.putExtra("use", "guide");
            getContext().startActivity(intent);
        }else if (v == mPaly) {
            mPoiSearchPresenter.searchNearby(StringConstant.BAIDUMAPCLOUDAK,StringConstant.BAIDUMAPCLOUDPOIID,BaseApi.getInstance().getCurrentLocLat(),
                    "休闲娱乐", 0, 2000, PoiSortType.distance_from_near_to_far, 50);
        }else if (v == mBank) {
            mPoiSearchPresenter.searchNearby(StringConstant.BAIDUMAPCLOUDAK,StringConstant.BAIDUMAPCLOUDPOIID,BaseApi.getInstance().getCurrentLocLat(),
                    "ATM", 0, 2000, PoiSortType.distance_from_near_to_far, 50);
        }
    }

    @Override
    public void onPoiSearchSuccess(PoiResult poiResult) {
        if (callBack == null) {
            return;
        }

        callBack.onPoiSearchSuccess(poiResult);
    }

    @Override
    public void onPoiSearchFailed(SearchResult.ERRORNO error) {
        if (callBack == null) {
            return;
        }

        callBack.onPoiSearchFailed(error);
    }

    @Override
    public void onDestory() {
        mPoiSearchPresenter.onDestory();
    }
}
