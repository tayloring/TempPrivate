package com.whu.Gongyinchao.schoolservice.chatmodule.friendsrequestpart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by 龚银超 on 2016/9/15.
 */
public class RequestFriends extends Activity{





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request();
    }



    private void request(){
        SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
        String token0=ps.getString("token", "");
        final String token = "Token "+token0.substring(21, 61);
        Log.d("TAG",token);
        final String channelid=ps.getString("channel_id","");
        final  String name="user1";
        String httpurl = "http://115.159.74.116:8000/friend/request_friend/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
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
                //headers.put("WWW-Authenticate","Basic realm=\"fake\"");
                headers.put("Authorization",token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("channel_id", channelid);
               map.put("request_name",name);
                return map;
            }
        };


        requestQueue.add(stringRequest);*/
        Map<String, String> map = new HashMap<String, String>();
        map.put("channel_id", channelid);
        map.put("request_name",name);
        JSONObject jsonObject = new JSONObject(map);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,httpurl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        })
        {
            //注意此处override的getParams()方法,在此处设置post需要提交的参数根本不起作用
            //必须象上面那样,构成JSONObject当做实参传入JsonObjectRequest对象里
            //所以这个方法在此处是不需要的
//    @Override
//    protected Map<String, String> getParams() {
//          Map<String, String> map = new HashMap<String, String>();
//            map.put("name1", "value1");
//            map.put("name2", "value2");

//        return params;
//    }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("WWW-Authenticate","Basic realm=\"fake\"");
                headers.put("Authorization",token);

                return headers;
            }
        };
        requestQueue.add(jsonRequest);

    }
















}
