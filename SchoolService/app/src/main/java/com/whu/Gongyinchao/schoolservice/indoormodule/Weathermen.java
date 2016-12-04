package com.whu.Gongyinchao.schoolservice.indoormodule;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 龚银超 on 2016/10/6.
 */

public class Weathermen extends Activity {
private TextView textView;
    private  TextView textView1;
    private ImageView mImageView;
    private String murl;
    private String cloth;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String re = (String) msg.obj;
                    textView.setText(re);
              //  case 1:
                  //  String re2=(String) msg.obj;
                  //  textView1.setText(re2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.weather);
        textView=(TextView) findViewById(R.id.info1);
        textView1=(TextView)findViewById(R.id.cloth);
        mImageView=(ImageView)findViewById(R.id.info2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    load();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }





    private void load(){

        HttpURLConnection connection;
        try {


            URL url=new URL( "http://api.map.baidu.com/telematics/v3/weather?location="
                    + URLEncoder.encode("武汉","UTF-8")
                    + "&output=json"
                    +"&ak=jDCa2FmmWgQ47UVwtGiUO9Pz5E3j14fK&"
                    +"&mcode=69:E4:E4:69:4F:2D:F3:8D:87:2A:86:3B:BB:04:3E:AA:83:24:15:B1;"
                    +"com.whu.GongYinchao.schoolservice");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            Log.i("TAG", response.toString());
            // textView.setText(response.toString());
            parseJSONObjectOrJSONArray(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }



    //解析JSON数据
    private void parseJSONObjectOrJSONArray(String jsonData) {
        try {
            String count = "";
            String mcount="";
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if (jsonArray.length() > 0) {
                JSONObject object = jsonArray.getJSONObject(0);
                String city = object.optString("currentCity");
                JSONArray array = object.getJSONArray("weather_data");
                JSONArray array1 =object.getJSONArray("index");
                JSONObject jsonObject12=array1.getJSONObject(0);
                JSONObject jsonObject13=array1.getJSONObject(1);
              //String  unkon=jsonObject13.optString("des");
                cloth=jsonObject12.optString("des");
              //  textView1.setText(cloth);



                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject1 = array.getJSONObject(i);
                    String dateDay = jsonObject1.optString("date");
                    String weather = jsonObject1.optString("weather");
                    String wind = jsonObject1.optString("wind");
                    String temperature = jsonObject1.optString("temperature");
                     murl=jsonObject1.optString("dayPictureUrl");



                    count = count + "\n" + dateDay + " " + weather + " " + wind + " " + temperature;
                    Log.i("AAA", count);
                }

                Message message = new Message();
                message.what = 0;
                message.obj = count;
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //volley异步加载图片
  /*  private void loadImageByVolley() {

          String imageUrl = "http://api.map.baidu.com/images/weather/day/duoyun.png";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
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
                R.drawable.ic_launcher1, R.drawable.ic_launcher1);
        //参数：1.要加载的图片，2.默认显示的图片，3，加载错误显示的图片
        imageLoader.get(imageUrl, listener);
    }*/


}