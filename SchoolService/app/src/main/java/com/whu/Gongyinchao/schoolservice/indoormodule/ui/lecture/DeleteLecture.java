package com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture;

import android.app.Activity;
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
public class DeleteLecture extends Activity {

    private EditText mlectureid,mchannelid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.delete_lecture);

      mlectureid=(EditText)findViewById(R.id.lectureID);
        mchannelid=(EditText)findViewById(R.id.channelID);



    }




    private void delete() {


        final String id = mlectureid.getText().toString();
        final String channelid = mchannelid.getText().toString();

        String httpurl = "http://115.159.74.116:8000/lecture/delete_lecture/";
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
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("id",id);
                map.put("channel_id", channelid);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }


    private void deletesuccess(){



    }






}
