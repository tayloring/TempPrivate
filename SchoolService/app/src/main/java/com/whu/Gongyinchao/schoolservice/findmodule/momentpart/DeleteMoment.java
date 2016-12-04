package com.whu.Gongyinchao.schoolservice.findmodule.momentpart;

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
public class DeleteMoment extends Service {
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


        upload();


        return START_STICKY;




    }



    private void  upload(){

       final String momentid="1";//id of a moment
        SharedPreferences ps=getSharedPreferences("token&channel",0);//token是存储文件
        String token0=ps.getString("token", "");
        final String channelid= ps.getString("channel_id","");
        final String token = token0.substring(21, 61);
        String httpurl = "http://115.159.74.116:8000/moment/delete_moment/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "response -> " + response);
                        //back.setText(response);
                        // registersuccess();
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
            headers.put("Authorization","Token "+token);
            return headers;
        }

            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                  map.put("id", momentid);
                  map.put("channel_id", channelid);
                return map;
            }
        };
        requestQueue.add(stringRequest);





    }








    @Override
    public void onDestroy(){


        super.onDestroy();


    }







}
