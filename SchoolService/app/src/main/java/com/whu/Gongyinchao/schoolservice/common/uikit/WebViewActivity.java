package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whu.Gongyinchao.schoolservice.R;

/**
 * Created by panfei on 16/4/14.
 */
public class WebViewActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView = null;
    }
}
