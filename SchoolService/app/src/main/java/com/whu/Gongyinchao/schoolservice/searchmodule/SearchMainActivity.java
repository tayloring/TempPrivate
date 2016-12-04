package com.whu.Gongyinchao.schoolservice.searchmodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whu.Gongyinchao.schoolservice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/10/22.
 */

public class SearchMainActivity extends Activity {
    private EditText et_search;
    private TextView tv_tip;
    private MyListView listView;
    private TextView tv_clear;
 //   private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
  //  public SQLiteDatabase db;
  //  private BaseAdapter adapter;

    private Context mContext;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_main);
        // 初始化控件
        initView();

        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  deleteData();
               // queryData("");
            }
        });


        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                 /*   boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                       // volley_get(et_search.getText().toString());
                        queryData("");

                    }*/

                   /// volley_get(et_search.getText().toString());
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Toast.makeText(SearchMainActivity.this, "clicked!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }
                String tempName = et_search.getText().toString();
                // 根据tem

                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);


                Toast.makeText(SearchMainActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
            }
        });

        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
        Date date = new Date();
        long time = date.getTime();
        //insertData("gyc" + time);

     //   insertData("标题",+);

        // 第一次进入查询所有的历史记录
       // queryData("");
    }


    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search_keyword);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (MyListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);


        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.drawable.magnifying_glass);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        et_search.setCompoundDrawables(drawable, null, null, null);// 只放左边
    }



    /**
     * 插入数据
     */
  /*  public void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }*/

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        //Cursor cursor = helper.getReadableDatabase().rawQuery(
            //    "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象

       /* adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        volley_get(tempName);

    }


    /**
     * 网上关键字匹配搜索
     */

    public void volley_get(String keyword) {
        String url = null;
        try {
            url = "http://182.254.147.91:8080/search/?query="+ URLEncoder.encode(keyword,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        mContext = this;


        // 1 创建RequestQueue对象

        mRequestQueue = Volley.newRequestQueue(mContext);

        // 2 创建StringRequest对象

        mStringRequest = new StringRequest(url,

                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {

                        // textView.setText(response);



                        String name="lectures";
                        String[] fields={"time","title","author","id"};

                        convertJSON2List(response,name,fields);
//
                    }

                }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                System.out.println("请求错误:" + error.toString());

            }

        });

        // 3 将StringRequest添加到RequestQueue

        mRequestQueue.add(mStringRequest);



    }

    public List<Map<String, Object>> convertJSON2List(String result,
                                                      String name, String[] fields) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            JSONArray array = new JSONObject(result).getJSONArray(name);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.opt(i);
                Map<String, Object> map = new HashMap<String, Object>();
              /*  for (String str : fields) {
                    map.put(str, object.get(str));
                }
                list.add(map);*/
                // RecordSQLiteOpenHelper helper=new RecordSQLiteOpenHelper(this);
                // SearchMainActivity searchMainActivity = new SearchMainActivity();
                // SearchMainActivity.insertData("标题"+object.optString("lecture_title"));
                map.put("content",object.optString("title"));
                map.put("teacher","主讲人："+object.optString("author"));
                map.put("time",object.optString("time"));
                //map.put("id",object.optString("id"));
                list.add(map);
                SearchAdapter searchAdapter =new SearchAdapter(this,list);
                listView.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            Log.e("error", e.getMessage());
        }


        return list;
    }








    /**
     * 检查数据库中是否已经有该条记录
     */
  /*  private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }*/

    /**
     * 清空数据
     */
    /*private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }*/



}
