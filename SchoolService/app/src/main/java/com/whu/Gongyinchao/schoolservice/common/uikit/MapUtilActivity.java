package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.BikingRouteOverlay;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.BusLineOverlay;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.PoiOverlay;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.TransitRouteOverlay;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.WalkingRouteOverlay;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.common.utils.DisplayUtils;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.BusLineSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnGetRouteSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnPoiSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.presenter.BusLineSearchPresenter;
import com.whu.Gongyinchao.schoolservice.outdoormodule.ui.MapUtilActivityRouteBar;
import com.whu.Gongyinchao.schoolservice.outdoormodule.ui.MapUtilActivitySearchBar;

import java.util.List;

/**
 * Created by panfei on 16/4/14.
 */
public class MapUtilActivity extends BaseActivity implements OnPoiSearchCallBack<SearchResult>, BusLineSearchCallBack, OnGetRouteSearchCallBack {

    private MapUtilActivitySearchBar mSearchBar;
    private MapUtilActivityRouteBar mRouteBar;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BusLineSearchPresenter mBusLinePresenter;
    private boolean isFirstLoc = true;
    private BusLineOverlay mBusLineOverlay;
    private PoiOverlay mPoiOverlay;
    private WalkingRouteOverlay mWalkRouteoverlay;
    private TransitRouteOverlay mTransitRouteOverlay;
    private BikingRouteOverlay mBikingRouteoverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maputil_activity);
        mSearchBar = (MapUtilActivitySearchBar) findViewById(R.id.mapactivity_searchbar);
        mRouteBar = (MapUtilActivityRouteBar) findViewById(R.id.mapactivity_routebar);

        if ("route".equals(getIntent().getStringExtra("use"))) {
            mSearchBar.setVisibility(View.GONE);
            mRouteBar.setVisibility(View.VISIBLE);
            mRouteBar.setType(MapUtilActivityRouteBar.TYPE_SEARCH);

            if (getIntent().getStringArrayExtra("startend") != null && getIntent().getStringArrayExtra("startend").length == 2) {
                String[] buf = getIntent().getStringArrayExtra("startend");
                mRouteBar.setRouteInfo(buf[0], buf[1]);
            }
        }else if ("guide".equals(getIntent().getStringExtra("use"))){
            mSearchBar.setVisibility(View.GONE);
            mRouteBar.setVisibility(View.VISIBLE);
            mRouteBar.setType(MapUtilActivityRouteBar.TYPE_GUIDE);
            if (getIntent().getStringArrayExtra("startend") != null && getIntent().getStringArrayExtra("startend").length == 2) {
                String[] buf = getIntent().getStringArrayExtra("startend");
                mRouteBar.setRouteInfo(buf[0], buf[1]);
            }
        }else {
            mSearchBar.setVisibility(View.VISIBLE);
            mRouteBar.setVisibility(View.GONE);
        }

        mMapView = (MapView) findViewById(R.id.mapactivity_mapview);
        mBaiduMap = mMapView.getMap();

        mSearchBar.setCallBack(this);
        mRouteBar.setCallBack(this);
        mBaiduMap.getUiSettings().setRotateGesturesEnabled(true);
        mMapView.setLogoPosition(LogoPosition.logoPostionleftTop);
        mMapView.showScaleControl(false);

        mBusLineOverlay = new BusLineOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(mBusLineOverlay);
        mPoiOverlay = new PoiOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(mPoiOverlay);
        mWalkRouteoverlay = new WalkingRouteOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(mWalkRouteoverlay);
        mTransitRouteOverlay = new TransitRouteOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(mTransitRouteOverlay);
        mBikingRouteoverlay = new BikingRouteOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(mBikingRouteoverlay);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if (TextUtils.isEmpty(marker.getTitle())) {
                    return false;
                }

                TextView btn = new TextView(MapUtilActivity.this);
                btn.setBackgroundResource(R.drawable.marker_title);
                btn.setPadding(DisplayUtils.dip2px(MapUtilActivity.this, 6f), DisplayUtils.dip2px(MapUtilActivity.this, 3f), DisplayUtils.dip2px(MapUtilActivity.this, 6f), DisplayUtils.dip2px(MapUtilActivity.this, 8f));
                btn.setText(marker.getTitle());
                btn.setTextSize(DisplayUtils.sp2px(MapUtilActivity.this, 4f));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = (String) marker.getExtraInfo().get("uid");
                        if (TextUtils.isEmpty(uid)) {
                            return;
                        }

                        PoiParaOption para = new PoiParaOption().uid(uid);

                        try {
                            BaiduMapPoiSearch.openBaiduMapPoiDetialsPage(para, MapUtilActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showMapNativeDialog();
                        }
                    }
                });
                InfoWindow infoWindow = new InfoWindow(btn, marker.getPosition(), -DisplayUtils.dip2px(MapUtilActivity.this, 18f));
                mBaiduMap.showInfoWindow(infoWindow);
                return false;
            }
        });
        mBaiduMap.getUiSettings().setScrollGesturesEnabled(true);

        mBusLinePresenter = new BusLineSearchPresenter(this, this);
    }

    @Override
    public void locateSuccess(BDLocation location) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder
                    .target(ll)
                    .zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }

        super.locateSuccess(location);
    }

    @Override
    public void onPoiSearchSuccess(SearchResult searchResult) {
        mBaiduMap.clear();

        if (searchResult instanceof PoiResult) {

            PoiResult poiResult = (PoiResult) searchResult;
            List<PoiInfo> results = poiResult.getAllPoi();

            if (results.size() > 0 && (results.get(0).type == PoiInfo.POITYPE.BUS_LINE || results.get(0).type == PoiInfo.POITYPE.SUBWAY_LINE)) {
                mBusLinePresenter.searchBusLine(BaseApi.getInstance().getCurrentLocCity(), results.get(0).uid);
                return;
            }

            mBaiduMap.clear();
            mPoiOverlay.setData(poiResult);
            mPoiOverlay.addToMap();
            mPoiOverlay.zoomToSpan();
        }else if (searchResult instanceof PoiDetailResult) {
        }

    }

    @Override
    public void onPoiSearchFailed(SearchResult.ERRORNO error) {

    }

    @Override
    protected void onResume() {
        mMapView.setVisibility(View.VISIBLE);
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.setVisibility(View.GONE);
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mBusLinePresenter.onDestory();
        ((DestoryListener)mSearchBar).onDestory();
        ((DestoryListener)mRouteBar).onDestory();
        super.onDestroy();
    }

    @Override
    public void onBusLineSearchSuccess(BusLineResult busLineResult) {
        if (busLineResult == null || busLineResult.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("抱歉，未找到结果");
            return;
        }

        mBaiduMap.clear();
        mBusLineOverlay.removeFromMap();
        mBusLineOverlay.setData(busLineResult);
        mBusLineOverlay.addToMap();
        mBusLineOverlay.zoomToSpan();
    }

    @Override
    public void onBusLineSearchFailed(BusLineResult.ERRORNO error) {

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            if (result.getSuggestAddrInfo() != null) {
                if (result.getSuggestAddrInfo().getSuggestStartNode() != null && result.getSuggestAddrInfo().getSuggestStartNode().size() >  0) {
                    mRouteBar.setRouteInfo(result.getSuggestAddrInfo().getSuggestStartNode().get(0).name, null);
                }

                if (result.getSuggestAddrInfo().getSuggestEndNode() != null && result.getSuggestAddrInfo().getSuggestEndNode().size() >  0) {
                    mRouteBar.setRouteInfo(null, result.getSuggestAddrInfo().getSuggestEndNode().get(0).name);
                }
                showToast("已为您推荐详细的起始点信息");
            }else {
                showToast("请输入详细的起始点信息");
            }
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            mWalkRouteoverlay.setData(result.getRouteLines().get(0));
            mWalkRouteoverlay.addToMap();
            mWalkRouteoverlay.zoomToSpan();
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            if (result.getSuggestAddrInfo() != null) {
                if (result.getSuggestAddrInfo().getSuggestStartNode() != null && result.getSuggestAddrInfo().getSuggestStartNode().size() >  0) {
                    mRouteBar.setRouteInfo(result.getSuggestAddrInfo().getSuggestStartNode().get(0).name, null);
                }

                if (result.getSuggestAddrInfo().getSuggestEndNode() != null && result.getSuggestAddrInfo().getSuggestEndNode().size() >  0) {
                    mRouteBar.setRouteInfo(null, result.getSuggestAddrInfo().getSuggestEndNode().get(0).name);
                }
                showToast("已为您推荐详细的起始点信息");
            }else {
                showToast("请输入详细的起始点信息");
            }
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            mTransitRouteOverlay.setData(result.getRouteLines().get(0));
            mTransitRouteOverlay.addToMap();
            mTransitRouteOverlay.zoomToSpan();
        }
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            if (result.getSuggestAddrInfo() != null) {
                if (result.getSuggestAddrInfo().getSuggestStartNode() != null && result.getSuggestAddrInfo().getSuggestStartNode().size() >  0) {
                    mRouteBar.setRouteInfo(result.getSuggestAddrInfo().getSuggestStartNode().get(0).name, null);
                }

                if (result.getSuggestAddrInfo().getSuggestEndNode() != null && result.getSuggestAddrInfo().getSuggestEndNode().size() >  0) {
                    mRouteBar.setRouteInfo(null, result.getSuggestAddrInfo().getSuggestEndNode().get(0).name);
                }
                showLoading("已为您推荐详细的起始点信息");
            }else {
                showLoading("请输入详细的起始点信息");
            }
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            mBikingRouteoverlay.setData(result.getRouteLines().get(0));
            mBikingRouteoverlay.addToMap();
            mBikingRouteoverlay.zoomToSpan();
        }
    }

    public void mapClear() {
        mBaiduMap.clear();
    }
}
