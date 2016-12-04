package com.whu.Gongyinchao.schoolservice.outdoormodule.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.mapapi.search.core.SearchResult;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.uikit.MapUtilActivity;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnPoiSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.presenter.PoiSearchPresenter;

/**
 * Created by panfei on 16/4/15.
 */
public class MapUtilActivitySearchBar extends SSRelativeLayout implements OnPoiSearchCallBack<SearchResult>, DestoryListener {

    private ImageView mBack;
    private EditText mSearch;
    private Button mSure;

    private OnPoiSearchCallBack mCallBack;
    private PoiSearchPresenter mPresenter;

    public MapUtilActivitySearchBar(Context context) {
        this(context, null);
    }

    public MapUtilActivitySearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.maputil_activity_titlebar, this);
        mBack = (ImageView) findViewById(R.id.titlebar_back);
        mSearch = (EditText) findViewById(R.id.titlebar_search);
        mSure = (Button) findViewById(R.id.titlebar_sure);

        mPresenter = new PoiSearchPresenter(getContext(), this);
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        mSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    ((MapUtilActivity)mCallBack).mapClear();
                }
                mPresenter.searchInCity(
                        BaseApi.getInstance().getCurrentLocCity(),
                        mSearch.getText().toString(), 0, 20);
            }
        });
    }

    public void setCallBack(OnPoiSearchCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void onPoiSearchSuccess(SearchResult searchResult) {

        if (searchResult == null || searchResult.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }

        if (mCallBack != null) {
            mCallBack.onPoiSearchSuccess(searchResult);
        }
    }

    @Override
    public void onPoiSearchFailed(SearchResult.ERRORNO error) {

        if (mCallBack != null) {
            mCallBack.onPoiSearchFailed(error);
        }
    }

    @Override
    public void onDestory() {
        mPresenter.onDestory();
    }
}
