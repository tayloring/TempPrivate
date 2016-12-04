package com.whu.Gongyinchao.schoolservice.User.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by 龚银超 on 2016/9/16.
 */
public class ChangePassword extends Activity {

private EditText oldpass,newpass,channelid;
    private Button surebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        oldpass=(EditText)findViewById(R.id.oldpass);
        newpass =(EditText)findViewById(R.id.newpass);
        surebutton=(Button)findViewById(R.id.sure);

        surebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });

       surebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               change();
           }
       });


    }

    private void change(){

        final String moldpass = oldpass.getText().toString();
        final String mnewpass = newpass.getText().toString();
        SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
        String token0=ps.getString("token", "");
        final String token = token0.substring(44, 84);
        final String channel_id= ps.getString("channel_id","");

        String httpurl = "http://115.159.74.116:8000/user/change_password/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", " 修改密码成功" + response);
                       changesuccess();
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
                map.put("old_password", moldpass);
                map.put("new_password", mnewpass);
                map.put("channel_id", channel_id);
                map.put("Authorization: Token",token);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }


    private void changesuccess()
    {




    }

}
