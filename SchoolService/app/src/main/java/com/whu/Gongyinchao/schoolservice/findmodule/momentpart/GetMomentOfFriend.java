package com.whu.Gongyinchao.schoolservice.findmodule.momentpart;

import android.app.Activity;
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
public class GetMomentOfFriend extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.moment_of_friend);


    }


    private  void get(){
        final String begin_index="";
        final String end_index="";
        SharedPreferences ps=getSharedPreferences("token&channel",0);//token是存储文件
        String token0=ps.getString("token", "");
        final String channelid= ps.getString("channel_id","");
        final String token = token0.substring(44, 84);
        String httpurl = "http://115.159.74.116:8000/moment/moments_of_friends/";
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
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("begin_index", begin_index);
                map.put("end_index", end_index);
                map.put("channel_id",channelid);
                map.put("Authorization: Token",token);
                return map;
            }
        };
        requestQueue.add(stringRequest);






    }




}
