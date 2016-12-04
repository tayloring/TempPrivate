package com.whu.Gongyinchao.schoolservice.findmodule.momentpart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whu.Gongyinchao.schoolservice.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/9/15.
 */
public class CreateMoment extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_moment);
         moment();
         startService(new Intent(CreateMoment.this,MomentPublisher.class));
    }

    private void moment(){

        final String momentcontent="test";
        //final String channelid="123";
        SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
        String token0=ps.getString("token", "");
       // final String token = token0.substring(44,84);
        final String channelid= ps.getString("channel_id","");
        final String token ="c7e0b41ec59aea83e6095c6d560959203f31a68d";

        String httpurl = "http://115.159.74.116:8000/moment/create_moment/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "response -> " + response);
                        //back.setText(response);
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
              //  headers.put("WWW-Authenticate","xBasic realm=");
                headers.put("Authorization","Token "+token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("moment_content", momentcontent);
                map.put("channel_id",channelid );
                return map;
            }
        };




        requestQueue.add(stringRequest);




    }



}
