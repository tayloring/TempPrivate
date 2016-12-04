package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;

/**
 * Created by panfei on 16/4/14.
 */
public class IndoorBanner extends SSRelativeLayout {

    private TextView mBannerText;
    private Button mBannerBtn;

    public IndoorBanner(Context context) {
        this(context, null);
    }

    public IndoorBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.indoor_each, this);
        mBannerText = (TextView) findViewById(R.id.indoor_banner_text);
        mBannerBtn = (Button) findViewById(R.id.indoor_banner_btn);
    }

    public IndoorBanner setTextView(String msg) {
        mBannerText.setText(msg);
        return this;
    }

    public IndoorBanner setBtn(String msg) {
        mBannerBtn.setText(msg);
        return this;
    }

    public IndoorBanner addOnClickListener(View.OnClickListener listener) {
        mBannerBtn.setOnClickListener(listener);
        return this;
    }
}
