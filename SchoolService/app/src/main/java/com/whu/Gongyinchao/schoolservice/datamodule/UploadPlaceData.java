package com.whu.Gongyinchao.schoolservice.datamodule;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/9/15.
 */
public class UploadPlaceData extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){


        super.onCreate();



    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        SharedPreferences ps=getSharedPreferences("place",0);//存储文件
        final String lat=ps.getString("lat", "");
        final String lag=ps.getString("lag", "");
        SharedPreferences pe=getSharedPreferences("token&channel",0);//token是存储文件
        String token0=pe.getString("token", "");
        final String channelid= ps.getString("channel_id","");
        final String token = token0.substring(44, 84);
        final String channel="123";
        String httpurl ="http://115.159.74.116:8000/data/upload_place/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "上传坐标成功 " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("lat", lat);
                map.put("lag", lag);
                map.put("channel_id",channelid);
                //map.put("Authorization: Token",token);
                return map;
            }
        };
        requestQueue.add(stringRequest);

        return START_STICKY;




    }

    @Override
    public void onDestroy(){


        super.onDestroy();


    }








}
