package com.whu.Gongyinchao.schoolservice.loginmodule.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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


public class RegisterActivity extends AppCompatActivity {


    private   FloatingActionButton fab;
    private    CardView cvAdd;
    private EditText username,password,Email;
    private Button regis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        cvAdd=(CardView)findViewById(R.id.cv_add);
        username=(EditText)findViewById(R.id.et_username);
        password=(EditText)findViewById(R.id.et_password);
        Email=(EditText)findViewById(R.id.et_email);
        regis=(Button)findViewById(R.id.bt_go);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }


    private void register(){


        final String name =username.getText().toString();
        final String pass = password.getText().toString();
        final String email=Email.getText().toString();
        String httpurl ="http://115.159.74.116:8000/user/register/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "注册成功 " + response);
                        //back.setText(response);
                        startActivity(new Intent(RegisterActivity.this,MainLoginActivity.class));

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
                map.put("user", name);
                map.put("password", pass);
                map.put("email",email);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


        }






