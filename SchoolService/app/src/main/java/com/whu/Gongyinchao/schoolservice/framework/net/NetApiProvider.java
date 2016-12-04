package com.whu.Gongyinchao.schoolservice.framework.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by panfei on 16/4/4.
 */
public class NetApiProvider {
    private static volatile NetApiProvider instance;
    private Gson gson;
    private RequestQueue mRequsetQueue = null;

    protected NetApiProvider(Context context){
        mRequsetQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        gson = new GsonBuilder().create();
    }

    public static NetApiProvider getInstance(Context context) {
        if (instance == null){
            synchronized (NetApiProvider.class){
                if (instance == null){
                    instance = new NetApiProvider(context);
                }
            }
        }

        return instance;
    }

    public <T> void get(Object TAG, String url, Map<String, String> params, final Class<T> clazz, final UICallBack<T> uiCallBack) {
        get(TAG, url, params, clazz, uiCallBack, null);
    }

    public <T> void get(Object TAG, String url, final Map<String, String> params, final Class<T> clazz, final UICallBack<T> uiCallBack, final StringCallBack strCallBack) {
        if (params != null && params.size() > 0){
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Map.Entry<String, String> entry: params.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue()).append("&");
            }

            sb.deleteCharAt(sb.length() - 1);
            url = sb.toString();
        }

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String s) {
                        new ResponseHandler<T>().start(new Callable<T>() {
                            @Override
                            public T call() throws Exception {
                                if (strCallBack != null) {
                                    String str = strCallBack.opStr(s);
                                    return gson.fromJson(str, clazz);
                                }

                                return gson.fromJson(s, clazz);
                            }
                        }, uiCallBack);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null) {
                    uiCallBack.onErrorResponse(volleyError.networkResponse.statusCode, String.valueOf(volleyError.networkResponse.data));
                }else {
                    uiCallBack.onErrorResponse(-1, volleyError.getClass().getSimpleName());
                }
            }
        });
        request.setTag(TAG);

        mRequsetQueue.add(request);
    }

    public <T> void post(String url, Map<String, String> params, final Class<T> clazz, final UICallBack<T> UICallBack){
        post(url, params, clazz, UICallBack, null);
    }

    public <T> void post(String url, final Map<String, String> params, final Class<T> clazz, final UICallBack<T> UICallBack, final StringCallBack strCallBack){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String s) {
                        new ResponseHandler<T>().start(new Callable<T>() {
                            @Override
                            public T call() throws Exception {
                                if (strCallBack != null) {
                                    String str = strCallBack.opStr(s);
                                    return gson.fromJson(str, clazz);
                                }

                                return gson.fromJson(s, clazz);
                            }
                        }, UICallBack);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                UICallBack.onErrorResponse(volleyError.networkResponse.statusCode, String.valueOf(volleyError.networkResponse.data));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        mRequsetQueue.add(request);
    }

    public void cancelAll(RequestQueue.RequestFilter filter) {
        mRequsetQueue.cancelAll(filter);
    }

    public void cacelAll(Object o) {
        mRequsetQueue.cancelAll(o);
    }

    public interface UICallBack<T>{
        void onResponse(T t);

        void onErrorResponse(int errorCode, String message);
    }

    public interface StringCallBack{
        String opStr(String s);
    }
}
