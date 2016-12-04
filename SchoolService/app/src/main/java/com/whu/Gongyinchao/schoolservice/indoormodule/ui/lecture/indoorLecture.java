package com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.GetPublischedExpress;
import com.whu.Gongyinchao.schoolservice.searchmodule.SearchMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/8/27.
 */
public class indoorLecture extends Activity implements CloudListener {
    private static final String LTAG = GetPublischedExpress.class.getSimpleName();

    private Context mContext;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ListView lecture;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
   private EditText search;


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.indoor_lecture);
// 先隐藏键盘
       // ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        CloudManager.getInstance().init(indoorLecture.this);
        mMapView=(MapView)findViewById(R.id.lecture_mapView);
        mBaiduMap=mMapView.getMap();
        search=(EditText)findViewById(R.id.et_search_keyword);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(indoorLecture.this, SearchMainActivity.class);
                startActivity(intent);
            }
        });

        lecture = (ListView) findViewById(R.id.backmsg);


        NearbySearchInfo info = new NearbySearchInfo();
        info.ak = "eH0WkS7KG2CLpZckx4ZSRX4k4Y0i3QVp";
        info.geoTableId = 147251;
        info.radius = 60000;
        info.location = "114.30,30.60";
        info.pageSize=30;
        CloudManager.getInstance().nearbySearch(info);


        volley_get();


    }




    private void pop() {
        InfoWindow.OnInfoWindowClickListener listener = null;
          //1.LatLng 位置
        Button button = new Button(getApplicationContext()); //2.Button
        button.setBackgroundResource(R.drawable.pop);
        button.setText("click here");
        button.setTextColor(android.graphics.Color.BLACK);
        listener = new InfoWindow.OnInfoWindowClickListener() {//3.OnInfoWindowClickListener监听器
            @Override
            public void onInfoWindowClick() {
                // Intent intent = new Intent(MainActivity.this, b.class);//跳转到另一个页面 记得mainfest.xml中多加个<Activity />
                // MainActivity.this.startActivity(intent);
            }
        };
       // InfoWindow infoWindow;//新建InfoWindow
       //infoWindow = new InfoWindow(point, button, listener);
       // baiduMap.showInfoWindow(infoWindow);//显示 这里不用addOverlay了
    }

    public void volley_get(){
        String url = "http://api.map.baidu.com/geosearch/v3/nearby?ak=eH0WkS7KG2CLpZckx4ZSRX4k4Y0i3QVp&geotable_id=" +
                "147251&location=114.30,30.60&radius=60000&page_size=50";
        mContext = this;


        // 1 创建RequestQueue对象

        mRequestQueue = Volley.newRequestQueue(mContext);

        // 2 创建StringRequest对象

        mStringRequest = new StringRequest(url,

                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {

                        // textView.setText(response);



                        String name="contents";
                        String[] fields={"time","univ","speaker","url","lecture_location","lecture_title"};

                        convertJSON2List(response,name,fields);
//
                    }

                }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                System.out.println("请求错误:" + error.toString());

            }

        });

        // 3 将StringRequest添加到RequestQueue

        mRequestQueue.add(mStringRequest);



    }


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
                map.put("content",object.optString("lecture_title"));
                map.put("teacher","主讲人"+object.optString("speaker"));
                String time =object.optString("time");
             String time2=   time.substring(0,10)+time.substring(12,13)+":"+time.substring(15,16);
                map.put("time",time2);
                map.put("url",object.optString("url"));
                list.add(map);
                LectureAdapter lectureAdapter =new LectureAdapter(this,list);
              /*  SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
                        android.R.layout.simple_list_item_2, new String[] {"lecture_title","speaker","time"},
                        new int[] {android.R.id.text1, android.R.id.text2});*/
                lecture.setAdapter(lectureAdapter);



            }
        } catch (JSONException e) {
            Log.e("error", e.getMessage());
        }


        return list;
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
    public void onGetSearchResult(CloudSearchResult result, int i) {

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
    public void onGetDetailSearchResult(DetailSearchResult result, int i) {
        if (result != null) {
            if (result.poiInfo != null) {


                Toast.makeText(indoorLecture.this, result.poiInfo.title,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(indoorLecture.this,
                        "status:" + result.status, Toast.LENGTH_SHORT).show();
            }


        }

    }
    }

