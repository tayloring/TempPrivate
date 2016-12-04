package com.whu.Gongyinchao.schoolservice.framework.net;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by panfei on 16/4/5.
 */

class OkHttpStack extends HurlStack{

    private OkHttpClient okHttpClient;

    public OkHttpStack(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return okUrlFactory.open(url);
    }
}
