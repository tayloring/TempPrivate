package com.whu.Gongyinchao.schoolservice.outdoormodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.L;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.PoiOverlay;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSPickerView;
import com.whu.Gongyinchao.schoolservice.common.utils.DisplayUtils;
import com.whu.Gongyinchao.schoolservice.framentpart.BaseFragment;
import com.whu.Gongyinchao.schoolservice.framework.location.LocationManager;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnPoiSearchCallBack;
import com.whu.Gongyinchao.schoolservice.outdoormodule.ui.OutdoorSelecter;
import com.whu.Gongyinchao.schoolservice.searchmodule.SearchMainActivity;

/**
 * Created by panfei on 16/4/12.
 */
public class OutdoorView extends BaseFragment implements OnPoiSearchCallBack<PoiResult>, DestoryListener {

    private SSPickerView mPicker;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Button mRequestLocate;
    private OutdoorSelecter mSelecter;
    private PoiOverlay mPoiOverlay;
    private SharedPreferences sp;
    private EditText search;

    private MyLocationConfiguration.LocationMode mCurrentMode;
    private boolean isFirstLoc = true;

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.outdoor_fragment, container,false);
        mPicker = (SSPickerView) view.findViewById(R.id.picker);
        mMapView = (MapView) view.findViewById(R.id.outdoor_mapView);
        mSelecter = (OutdoorSelecter) view.findViewById(R.id.outdoor_select);
        mRequestLocate = (Button) view.findViewById(R.id.location_requst);
         search=(EditText)view.findViewById(R.id.et_search_keyword);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SearchMainActivity.class);
                getContext().startActivity(intent);
            }
        });

                init();


        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }


    private void init(){

        mSelecter.setCallBack(this);
        mMapView.setLogoPosition(LogoPosition.logoPostionleftTop);
        mMapView.showScaleControl(false);
        mBaiduMap = mMapView.getMap();
        mPoiOverlay = new PoiOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(mPoiOverlay);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        mBaiduMap.setMyLocationEnabled(true);


        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLng northeast = new LatLng(30.60, 114.30);
                LatLng southwest = new LatLng(30.50, 114.45);
                mBaiduMap.setMapStatusLimits(new LatLngBounds.Builder().include(northeast).include(southwest).build());
            }
        });



        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                TextView btn = new TextView(getContext());
                btn.setBackgroundResource(R.drawable.marker_title);
                btn.setPadding(DisplayUtils.dip2px(getContext(), 6f), DisplayUtils.dip2px(getContext(), 3f), DisplayUtils.dip2px(getContext(), 6f), DisplayUtils.dip2px(getContext(), 8f));
                btn.setText(marker.getTitle());
                btn.setTextSize(DisplayUtils.sp2px(getContext(), 4f));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = (String) marker.getExtraInfo().get("uid");
                        if (TextUtils.isEmpty(uid)) {
                            return;
                        }

                        PoiParaOption para = new PoiParaOption().uid(uid);

                        try {
                            BaiduMapPoiSearch.openBaiduMapPoiDetialsPage(para, getContext());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ((BaseActivity)getContext()).showMapNativeDialog();
                        }
                    }
                });
                InfoWindow infoWindow = new InfoWindow(btn, marker.getPosition(), -DisplayUtils.dip2px(getContext(), 16f));
                mBaiduMap.showInfoWindow(infoWindow);
                return false;
            }
        });

        mRequestLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                isFirstLoc = true;
                LocationManager.start();
            }
        });


    }


    @Override
    public void locating() {
        mPicker.setText("正在定位...");
        mRequestLocate.setBackgroundDrawable(getResources().getDrawable(R.drawable.outdoor_locating));
        mRequestLocate.setClickable(false);

        mSelecter.locating();
    }

    @Override
    public void locateFailed() {
        mPicker.setText("定位失败");
        mRequestLocate.setBackgroundDrawable(getResources().getDrawable(R.drawable.outdoor_request));
        mRequestLocate.setClickable(true);


    }

    @Override
    public void locateSuccess(BDLocation location) {
        mRequestLocate.setBackgroundDrawable(getResources().getDrawable(R.drawable.outdoor_request));
        mRequestLocate.setClickable(true);
        if (mPicker == null) {
            return;
        }

        String locDecri = location.getLocationDescribe();

        if (locDecri.startsWith("在")) {
            locDecri = locDecri.substring(1, locDecri.length());
        }

        mPicker.setText(locDecri);

        L.e("定位信息看这里  "+locDecri);

        SharedPreferences sp=getContext().getSharedPreferences("location",0);//token存储文件名
        SharedPreferences.Editor se1=sp.edit();
        se1.putString("location", locDecri);

        se1.commit();


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


            sp= getContext().getSharedPreferences("place",0);//login存储文件名
            SharedPreferences.Editor se=sp.edit();
            se.putString("lat", String.valueOf(ll.latitude));
            se.putString("lag", String.valueOf(ll.longitude));
            se.commit();



            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }


    }

    @Override
    public Object getRequestTag() {
        return null;
    }


  /*  @Override
    public void onAttachedToWindow() {
        mMapView.onResume();
        mMapView.onAttachedToWindow();
        LocationManager.start();
    }

    @Override
    public void onDetachedFromWindow() {
        mMapView.onPause();
        mMapView.onDetachedFromWindow();
    }*/

    @Override
    public void onPoiSearchSuccess(PoiResult poiResult) {
        if (poiResult.getAllPoi() == null || poiResult.getAllPoi().size() == 0) {
            return;
        }

        mBaiduMap.clear();
        mPoiOverlay.removeFromMap();
        mPoiOverlay.setData(poiResult);
        mPoiOverlay.addToMap();
        mPoiOverlay.zoomToSpan();
    }

    @Override
    public void onPoiSearchFailed(SearchResult.ERRORNO error) {

    }

    public void mapClear() {
        mBaiduMap.clear();
    }

    @Override
    public void onDestory() {
        ((DestoryListener)mSelecter).onDestory();
        mMapView.onDestroy();
    }


}
