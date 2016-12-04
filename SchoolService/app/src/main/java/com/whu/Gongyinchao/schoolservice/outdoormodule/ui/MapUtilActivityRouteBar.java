package com.whu.Gongyinchao.schoolservice.outdoormodule.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.uikit.MapUtilActivity;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSLinearLayout;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnGetRouteSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.presenter.RouteGuidePresenter;
import com.whu.Gongyinchao.schoolservice.outdoormodule.presenter.RouteSearchPresenter;

/**
 * Created by panfei on 16/4/18.
 */
public class MapUtilActivityRouteBar extends SSRelativeLayout implements OnGetRouteSearchCallBack, DestoryListener {

    public static final int TYPE_SEARCH = 0;
    public static final int TYPE_GUIDE = 1;

    private RouteSearchPresenter mRoutePresenter;
    private RouteGuidePresenter mGuidePresenter;
    private OnGetRouteSearchCallBack callBack;

    private ImageView mExchage;
    private EditText mStart;
    private EditText mEnd;
    private Button mRouteGuide;
    private SSLinearLayout mRouteSearch;
    private Button mRouteWalk;
    private Button mRouteBike;
    private Button mRouteBus;

    public MapUtilActivityRouteBar(Context context) {
        this(context, null);
    }

    public MapUtilActivityRouteBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.mapactivity_routebar, this);
        mRoutePresenter = new RouteSearchPresenter(getContext(), this);
        mGuidePresenter = new RouteGuidePresenter(getContext(), this);
        mExchage = (ImageView) findViewById(R.id.routebar_exchange);
        mStart = (EditText) findViewById(R.id.routebar_start);
        mEnd = (EditText) findViewById(R.id.routebar_end);
        mRouteGuide = (Button) findViewById(R.id.route_guide);
        mRouteSearch = (SSLinearLayout) findViewById(R.id.route_search);
        mRouteWalk = (Button) findViewById(R.id.route_walk);
        mRouteBike = (Button) findViewById(R.id.route_bike);
        mRouteBus = (Button) findViewById(R.id.route_bus);

        mExchage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable temp = mStart.getText();
                mStart.setText(mEnd.getText());
                mEnd.setText(temp);
            }
        });

        mRouteWalk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    ((MapUtilActivity)callBack).mapClear();
                }
                mRoutePresenter.searchOnWalk(BaseApi.getInstance().getCurrentLocCity(), mStart.getText().toString(), mEnd.getText().toString());
            }
        });

        mRouteBike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    ((MapUtilActivity)callBack).mapClear();
                }
                mRoutePresenter.searchOnBike(BaseApi.getInstance().getCurrentLocCity(), mStart.getText().toString(), mEnd.getText().toString());
            }
        });

        mRouteBus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    ((MapUtilActivity)callBack).mapClear();
                }
                mRoutePresenter.searchOnBus(BaseApi.getInstance().getCurrentLocCity(), mStart.getText().toString(), mEnd.getText().toString());
            }
        });

        mRouteGuide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    ((MapUtilActivity)callBack).mapClear();
                }
                mGuidePresenter.guide(mStart.getText().toString(), mEnd.getText().toString());
            }
        });
    }

    public void setCallBack(OnGetRouteSearchCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (callBack != null) {
            callBack.onGetWalkingRouteResult(result);
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (callBack != null) {
            callBack.onGetTransitRouteResult(result);
        }
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
        if (callBack != null) {
            callBack.onGetBikingRouteResult(bikingRouteResult);
        }
    }

    public void setType(int type) {
        switch (type) {
            case TYPE_SEARCH :
                mRouteSearch.setVisibility(VISIBLE);
                mRouteGuide.setVisibility(GONE);
                break;
            case TYPE_GUIDE :
                mRouteSearch.setVisibility(GONE);
                mRouteGuide.setVisibility(VISIBLE);
                break;
        }
    }

    public void setRouteInfo(String start, String end) {
        if (null != start) {
            if ("不在室内".equals(start)) {
                start = "当前位置";
            }
            mStart.setText(start);
        }

        if (null != end) {
            mEnd.setText(end);
        }
    }

    @Override
    public void locateSuccess(BDLocation location) {
        if (TextUtils.isEmpty(mStart.getText()) || "不在室内".equals(mStart.getText())) {
            mStart.setText("当前位置");
            mEnd.requestFocus();
        }
        super.locateSuccess(location);
    }

    @Override
    public void onDestory() {
        mGuidePresenter.onDestory();
        mRoutePresenter.onDestory();
    }
}
