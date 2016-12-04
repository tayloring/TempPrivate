package com.whu.Gongyinchao.schoolservice.User.ui;

/**
 * Created by 龚银超 on 2016/8/22.
 */
/*public class RegisterActivity extends Activity implements RegisterCallBack {


    private EditText name, pwd;
    EditText email;
    private Button zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        zhuce = (Button) findViewById(R.id.zhuce);
        email = (EditText) findViewById(R.id.tel);

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = pwd.getText().toString();
                final String finalemail = email.getText().toString();
                final String finalName = name.getText().toString();
                String httpurl = "http://115.159.74.116:8000/user/register/";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("TAG", "response -> " + response);
                                //back.setText(response);
                                registersuccess();
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
                        map.put("user", finalName);
                        map.put("password", pass);
                        map.put("email", finalemail);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);






            }
        });

    }*/


 /*   protected void onNewIntent(Intent intent) {
        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
            finish();
        }
    }

    @Override
    public void registersuccess() {

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

    }

    @Override
    public void registerfaild(String msg) {

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
       // BaseApi.getInstance().cacelAll();

    }


}*/
