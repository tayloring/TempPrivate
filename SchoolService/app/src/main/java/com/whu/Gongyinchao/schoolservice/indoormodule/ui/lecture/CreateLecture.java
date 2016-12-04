package com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

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
 * Created by 龚银超 on 2016/9/13.
 */
public class CreateLecture extends Activity {

    private EditText tx1,tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx9,tx10;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lecture);


       /* tx1=(EditText)findViewById(R.id.tx1);

        tx2=(EditText)findViewById(R.id.tx2);

        tx3=(EditText)findViewById(R.id.tx3);

        tx4=(EditText)findViewById(R.id.tx4);

        tx5=(EditText)findViewById(R.id.tx5);

        tx6=(EditText)findViewById(R.id.tx6);
        tx7=(EditText)findViewById(R.id.tx7);

        tx8=(EditText)findViewById(R.id.tx8);
        tx9=(EditText)findViewById(R.id.tx9);
        tx10=(EditText)findViewById(R.id.tx10);*/


             Info();




    }




    private  void Info(){

        final String lat ="1";//tx1.getText().toString();
        final String lng = "0";//tx2.getText().toString();
        final String lec_title="test";//tx3.getText().toString();



        final String lec_abstract="test_abstract";//tx4.getText().toString();
        final String lec_location="computerschool"; //tx5.getText().toString();
        final String lec_author="teacher";//tx6.getText().toString();


        final String constitution ="computer";//tx7.getText().toString();
        final String university ="whu"; //tx8.getText().toString();
        final String time="2016.10.14";//tx9.getText().toString();

       // final String channel_id =//tx10.getText().toString();

        SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
        final String token0=ps.getString("token", "");
        final String channelid= ps.getString("channel_id","");
      //  final String token = token0.substring(44, 84);

        String httpurl ="http://115.159.74.116:8000/lecture/create_lecture/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("TAG", "response -> " + response);
                        //back.setText(response);
                       // loginSuccess();
                        //sendToken(response);
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
                headers.put("Authorization","Token "+token0);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("lat", lat);
                map.put("lng", lng);
                map.put("lec_title",lec_title);
                map.put("lec_abstract",lec_abstract);
                map.put("lec_location",lec_location);
                map.put("lec_author",lec_author);
                map.put("constitution",constitution);
                map.put("university",university);
                map.put("lec_time",time);
                map.put("channel_id",channelid);

                return map;
            }
        };
        requestQueue.add(stringRequest);




    }










}
