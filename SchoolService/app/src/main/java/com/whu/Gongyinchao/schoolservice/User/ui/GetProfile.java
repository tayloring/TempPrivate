package com.whu.Gongyinchao.schoolservice.User.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whu.Gongyinchao.schoolservice.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/9/16.
 */
public class GetProfile extends Activity {


    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_profile);

       mImageView=(ImageView)findViewById(R.id.image);



    }


    private void getprofile(final String target){

        SharedPreferences ps=getSharedPreferences("token&channel",0);//token是存储文件
        String token0=ps.getString("token", "");
        final String channelid= ps.getString("channel_id","");
        final String token = token0.substring(44, 84);

        String httpurl = "http://115.159.74.116:8000/user/log_out/";
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
                map.put("channel_id", channelid);
                map.put("target_user",target);
                map.put("Authorization: Token",token);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    //volley异步加载图片
    private void loadImageByVolley() {
                String imageUrl ="http://115.159.74.116:8000/user/get_image/";
                 RequestQueue requestQueue = Volley.newRequestQueue(this);
                 final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
                                 20);
                ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
                         @Override
                         public void putBitmap(String key, Bitmap value) {
                                 lruCache.put(key, value);
                             }

                                 @Override
                         public Bitmap getBitmap(String key) {
                                 return lruCache.get(key);
                            }
                     };
                ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
                 ImageLoader.ImageListener listener = ImageLoader.getImageListener(mImageView,
                                 R.drawable.ic_launcher, R.drawable.ic_launcher);
        //参数：1.要加载的图片，2.默认显示的图片，3，加载错误显示的图片
               imageLoader.get(imageUrl, listener);
             }



}
