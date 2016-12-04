package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.PoiOverlay;
import com.whu.Gongyinchao.schoolservice.common.utils.DisplayUtils;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by 龚银超 on 2016/9/15.
 */
public class GetPublischedExpress extends Activity implements CloudListener{

    private static final String LTAG = GetPublischedExpress.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private PoiOverlay mPoiOverlay;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private boolean isFirstLoc = true;
    private TextView te1,te2,te3;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.published_express);
        CloudManager.getInstance().init(GetPublischedExpress.this);
        mMapView=(MapView)findViewById(R.id.express_mapView);
        mBaiduMap=mMapView.getMap();
          te1=(TextView)findViewById(R.id.moment);
         te2=(TextView)findViewById(R.id.publish);
        te3=(TextView)findViewById(R.id.fresh);

        te1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(GetPublischedExpress.this, MainExpressActivity.class);
                startActivity(intent);



            }
        });

        te2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GetPublischedExpress.this, CreateExpress.class);
                startActivity(intent);
            }
        });


        NearbySearchInfo info = new NearbySearchInfo();
        info.ak = "eH0WkS7KG2CLpZckx4ZSRX4k4Y0i3QVp";
        info.geoTableId = 150427;
        info.radius = 60000;
        info.location = "114.30,30.60";
        info.pageSize=30;
        CloudManager.getInstance().nearbySearch(info);

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


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        CloudManager.getInstance().destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }



    @Override
    public void onGetSearchResult(CloudSearchResult result, int error) {


        if (result != null && result.poiList != null
                && result.poiList.size() > 0) {
            Log.d(LTAG, "onGetSearchResult, result length: " + result.poiList.size());
            mBaiduMap.clear();

           BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            LatLng ll;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (CloudPoiInfo info : result.poiList) {
                ll = new LatLng(info.latitude, info.longitude);
                OverlayOptions oo = new MarkerOptions().icon(bd).position(ll);
                mBaiduMap.addOverlay(oo);
                builder.include(ll);

            }
            LatLngBounds bounds = builder.build();
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);
        }
    }





    @Override
    public void onGetDetailSearchResult(DetailSearchResult result, int error) {
        if (result != null) {
            if (result.poiInfo != null) {
                Toast.makeText(GetPublischedExpress.this, result.poiInfo.title,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GetPublischedExpress.this,
                        "status:" + result.status, Toast.LENGTH_SHORT).show();
            }


        }

    }
}
