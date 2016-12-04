package com.whu.Gongyinchao.schoolservice.User.ui;

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
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SharePreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/9/16.
 */
public class UploadProfile extends Service {

    private SharePreferenceUtil mSpUtil;

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


    new Thread(new Runnable() {
        @Override
        public void run() {
            upload();
        }
    });


    return START_STICKY;


}


    private void  upload(){

        SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
        final String token0=ps.getString("token", "");
       // final String token = token0.substring(21, 61);

        final String userid=ps.getString("userid","");
        final String gender=ps.getString("gender","");
        final String nickname=ps.getString("nick","");
        final String headicon="/storage/emulated/0/temp_head_image.jpg";
        final String channelid= ps.getString("channel_id","");
       // final String token = token0.substring(44, 84);
        Log.d("TAG", token0);
        String httpurl = "http://115.159.74.116:8000/user/upload_profile/";
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
                Log.e("TAG", error.getMessage().toString(), error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization","Token "+token0);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("channel_id", channelid);
                map.put("user_identity", userid);
                map.put("gender", gender);
                map.put("nick_name",nickname);
                map.put("head_icon",headicon);
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
