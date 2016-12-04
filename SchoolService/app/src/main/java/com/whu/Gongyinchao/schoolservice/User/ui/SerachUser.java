package com.whu.Gongyinchao.schoolservice.User.ui;

import android.app.Activity;
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
 * Created by 龚银超 on 2016/9/16.
 */
public class SerachUser extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_user);

    }


    private void search(){

        final String channelid="";

        final String keyword="";
        final String gender="";
        String httpurl = "http://115.159.74.116:8000/user/register/";
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
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("channel_id", channelid);
                map.put("keyword", keyword);
                map.put("gender", gender);
                return map;
            }
        };
        requestQueue.add(stringRequest);









    }








}
