package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.PoiOverlay;
import com.whu.Gongyinchao.schoolservice.common.utils.DisplayUtils;

import java.util.HashMap;
import java.util.Map;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by 龚银超 on 2016/9/15.
 */
public class CreateExpress extends Activity {

     String lng;
     String lat;
   // final String expresstime ;
   String expresslocaton;
    private EditText editText;

    private Button button;
    /**
     * 百度地图
     */
    private MapView mapView;
    /**
     * 定义 BaiduMap 地图对象的操作方法与接口
     */
    BaiduMap baiduMap;

    private PoiSearch poiSearch;

    /**
     * 搜索关键词
     */
    private final String keyword = "快递";
    /**
     * 每页容量50
     */
    private final int pageCapacity = 50;
    /**
     * 第一页
     */
    private final int pageNum = 0;
    /**
     * 搜索半径10km
     */
    private final int radius = 60000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_express);
        editText = (EditText) findViewById(R.id.location);
        button=(Button)findViewById(R.id.sure);




        initView();
        initData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
                Toast.makeText(CreateExpress.this, "创建成功，请到地图查看", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(CreateExpress.this, GetPublischedExpress.class);
                startActivity(intent);
            }
        });


    }


    private void initView() {
        mapView = (MapView) findViewById(R.id.publish_mapView);
        // 是否显示自带缩放控件
        mapView.showZoomControls(false);
    }

    private void initData() {
        baiduMap = mapView.getMap();

        // 初始化搜索模块
        poiSearch = PoiSearch.newInstance();
        // 注册搜索事件监听
        poiSearch
                .setOnGetPoiSearchResultListener(new PoiResult());

        poiSearch.searchNearby(new PoiNearbySearchOption().keyword(keyword)
                .location(new LatLng(30.532641, 114.364646))
                .pageCapacity(pageCapacity).pageNum(pageNum).radius(radius));


        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                TextView btn = new TextView(getContext());
             Log.d("TAG", btn.getText().toString());
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
                baiduMap.showInfoWindow(infoWindow);
                return false;
            }
        });


    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 销毁搜索模块
        poiSearch.destroy();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();

    }
    public class PoiResult implements OnGetPoiSearchResultListener {

        /**
         * 定义 BaiduMap 地图对象的操作方法与接口
         */
        // private BaiduMap baiduMap;
        @Override
        public void onGetPoiResult(com.baidu.mapapi.search.poi.PoiResult poiResult) {

            if ((poiResult == null)
                    || (poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                baiduMap.clear();
           String[] r= (String[]) poiResult.getAllPoi().toArray();
              //  Log.e("TAG",r.toString());
                PoiOverlay overlay = new PoiOverlay(baiduMap);
                overlay.getPoiResult().getAllPoi();
                editText.append(overlay.getPoiResult().toString());
                baiduMap.setOnMarkerClickListener(overlay);

                overlay.setData(poiResult);
                overlay.addToMap();
                // 缩放地图，使所有Overlay都在合适的视野内
                overlay.zoomToSpan();
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            }


        }


        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {

                return;
            } else {
                public List<Map<String, Object>> convertJSON2List(String result,
                        String name, String[] fields) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    try {
                        JSONArray array = new JSONObject(result).getJSONArray(name);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.opt(i);
                            Map<String, Object> map = new HashMap<String, Object>();
              /*  for (String str : fields) {
                    map.put(str, object.get(str));
                }
                list.add(map);*/
                            // RecordSQLiteOpenHelper helper=new RecordSQLiteOpenHelper(this);
                            // SearchMainActivity searchMainActivity = new SearchMainActivity();
                            // SearchMainActivity.insertData("标题"+object.optString("lecture_title"));
                            map.put("content",object.optString("title"));
                            map.put("teacher","主讲人："+object.optString("author"));
                            map.put("time",object.optString("time"));
                            //map.put("id",object.optString("id"));
                            list.add(map);
                            SearchAdapter searchAdapter =new SearchAdapter(this,list);
                            listView.setAdapter(searchAdapter);
                            searchAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        Log.e("error", e.getMessage());
                    }


                    return list;
                }

                poiDetailResult.getAddress();
                  lng= String.valueOf(poiDetailResult.getLocation().longitude);
                lat= String.valueOf(poiDetailResult.getLocation().latitude);
                expresslocaton=poiDetailResult.getName();

                poiDetailResult.getLocation();
                Toast.makeText(getContext(),
                        poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), 0)
                        .show();
            }


        }
    }

    private void create() {

        ///快递的位置信息



       // final String lag ="114.364646";
      //  final String lat =  "30.53264";
//
        final String expresstime = "2016.10.28";
      //  final String expresslocaton = editText.getText().toString();
        // final String channel="123";

        SharedPreferences ps = getSharedPreferences("token&channel", 0);//login是存储文件
        final String token0 = ps.getString("token", "");

        final String channelid = ps.getString("channel_id", "");


        //final int token = token0.substring(44, 84);\n" +
        // final String token="c7e0b41ec59aea83e6095c6d560959203f31a68d";

        String httpurl = "http://115.159.74.116:8000/express/create_express/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "response -> " + response);
                        //back.setText(response);
                        // loginSuccess();
                        //sendToken(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + token0);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("lat", lat);
                map.put("lng", lng);
                map.put("express_time", expresstime);
                map.put("express_location", expresslocaton);
                map.put("channel_id", channelid);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }


}