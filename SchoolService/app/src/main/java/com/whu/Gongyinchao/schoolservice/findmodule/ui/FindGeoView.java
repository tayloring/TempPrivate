package com.whu.Gongyinchao.schoolservice.findmodule.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;
import com.whu.Gongyinchao.schoolservice.common.uikit.WebViewActivity;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;

/**
 * Created by panfei on 16/4/12.
 */
public class FindGeoView extends SSRelativeLayout {

    private ImageView icon;
    private TextView geo;
    private TextView[] geos = new TextView[4];

    public FindGeoView(Context context) {
        this(context, null);
    }

    public FindGeoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.find_each, this);
        icon = (ImageView) findViewById(R.id.find_icon);
        geo = (TextView) findViewById(R.id.fing_geo);
        geos[0] = (TextView) findViewById(R.id.geo1);
        geos[1] = (TextView) findViewById(R.id.geo2);
        geos[2] = (TextView) findViewById(R.id.geo3);
        geos[3] = (TextView) findViewById(R.id.geo4);
    }

    public FindGeoView setIcon(int res) {
        icon.setImageResource(res);
        return this;
    }

    public FindGeoView setMainGeo(String msg) {
        geo.setText(msg);
        return this;
    }

    public FindGeoView setGeos(String[] msg) {
        if (msg.length < 4) {
            return this;
        }

        for (int i = 0; i < geo.length(); i ++) {
            geos[i].setText(msg[i]);
        }

        return this;
    }

    public FindGeoView setUrl(final String[] urls) {

        for (int i = 0; i < urls.length; i ++) {
            final int pos = i;
            geos[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("url", urls[pos]);
                    intent.setClass(getContext(), WebViewActivity.class);
                    getContext().startActivity(intent);
                }
            });
        }

        return this;
    }

    public FindGeoView setMapRouteUrl(final String[] keys) {
        for (int i = 0; i <keys.length; i ++) {
            final int pos = i;
            geos[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LatLng ptCenter = BaseApi.getInstance().getCurrentLocLat();
                    PoiParaOption para = new PoiParaOption()
                            .key(keys[pos])
                            .center(ptCenter)
                            .radius(2000);

                    try {
                        BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, getContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                        showDialog();
                    }
                }
            });
        }

        return this;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(getContext());
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
}
