package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;

/**
 * Created by panfei on 16/4/12.
 */
public class SSPickerView extends SSRelativeLayout {

    private ImageView mLocationIcon;
    private TextView mLocationInfo;
    
    public SSPickerView(Context context) {
        this(context, null);
    }

    public SSPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflate(getContext(), R.layout.location_picker, this);
        mLocationIcon = (ImageView) findViewById(R.id.location_icon);
        mLocationInfo = (TextView) findViewById(R.id.location_info);
    }

    public SSPickerView setText(String msg) {

        if (mLocationInfo == null) {
            return this;
        }

        mLocationInfo.setText(msg);
        return this;
    }

    public SSPickerView setDrawableLeft(int resId) {
        mLocationIcon.setImageResource(resId);
        return this;
    }
}
