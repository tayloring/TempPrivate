package com.whu.Gongyinchao.schoolservice.loginmodule.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whu.Gongyinchao.schoolservice.MainActivity;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.activity.FirstSetActivity;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SharePreferenceUtil;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.app.SchoolServiceApplication;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.loginmodule.interfaces.LoginCallBack;
import com.whu.Gongyinchao.schoolservice.loginmodule.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainLoginActivity extends BaseActivity implements LoginCallBack {


    EditText etUsername;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;

    private RelativeLayout mErrorTips;
    private LoginPresenter mPresenter;
    private SharePreferenceUtil spUtil;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainlogin);
        etUsername=(EditText)findViewById(R.id.et_username);
        etPassword=(EditText)findViewById(R.id.et_password);
        btGo=(Button)findViewById(R.id.bt_go);
        cv=(CardView)findViewById(R.id.cv);
        fab=(FloatingActionButton)findViewById(R.id.fab);



        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login();

            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLoginActivity.this, RegisterActivity.class));
            }
        });



      //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
     //   ButterKnife.bind(this);

    }



    private  void login(){


        final String name =etUsername.getText().toString();
        final String pass = etPassword.getText().toString();
        SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
        // String token0=ps.getString("token", "");
        // final String token = token0.substring(21,61);
        final String channel= ps.getString("channel_id","");

        // final String channel="123";
        String httpurl ="http://115.159.74.116:8000/user/log_in/";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "response -> " + response);
                        //back.setText(response);
                        parseJSONObjectOrJSONArray(response);
                        SharedPreferences sp=getSharedPreferences("token&channel",0);//token是存储文件名
                        SharedPreferences.Editor se=sp.edit();
                        se.putString("name", name);
                        se.putString("pass", pass);
                      //  se.putString("token",response);
                        se.commit();

                        loginSuccess();
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
                map.put("user", name);
                map.put("password", pass);
                map.put("channel_id",channel);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }


   //解析JSON数据
    private void parseJSONObjectOrJSONArray(String jsonData) {

            String count = "";
            String mcount = "";
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                String token=jsonObject.getString("token");
                SharedPreferences sp=getSharedPreferences("token&channel",0);//token是存储文件名
                SharedPreferences.Editor se=sp.edit();
             //   se.putString("name", name);
              //  se.putString("pass", pass);
                se.putString("token",token);
                se.commit();

                 Log.d("TAG",token);

            } catch (JSONException e1) {
                e1.printStackTrace();
            }


        }



    @Override
    protected void onNewIntent(Intent intent) {
        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
            finish();
        }
    }

    @Override
    public void loginSuccess() {

        spUtil = SchoolServiceApplication.getInstance().getSpUtil();

			if ((!TextUtils.isEmpty(spUtil.getUserId()))&&(!TextUtils.isEmpty(spUtil.getNick()))) {
				Intent intent = new Intent(MainLoginActivity.this,
						MainActivity.class);
				startActivity(intent);
			} else {
				startActivity(new Intent(MainLoginActivity.this,
						FirstSetActivity.class));
			}



    }

    @Override
    public void loginFailed(String msg) {
        mErrorTips.setVisibility(View.VISIBLE);
        ((TextView)mErrorTips.getChildAt(1)).setText(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
        BaseApi.getInstance().cacelAll(getRequestTag());
    }



}
