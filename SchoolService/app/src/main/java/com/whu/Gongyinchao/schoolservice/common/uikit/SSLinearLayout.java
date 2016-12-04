package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.interfaces.LocationCallBack;

/**
 * Created by panfei on 16/4/16.
 */
public class SSLinearLayout extends LinearLayout implements LocationCallBack {
    public SSLinearLayout(Context context) {
        super(context);
    }

    public SSLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void locating() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i ++) {
            View child = getChildAt(i);
            if (child instanceof LocationCallBack) {
                ((LocationCallBack) child).locating();
            }
        }
    }

    @Override
    public void locateFailed() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i ++) {
            View child = getChildAt(i);
            if (child instanceof LocationCallBack) {
                ((LocationCallBack) child).locateFailed();
            }
        }
    }

    @Override
    public void locateSuccess(BDLocation location) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i ++) {
            View child = getChildAt(i);
            if (child instanceof LocationCallBack) {
                ((LocationCallBack) child).locateSuccess(location);
            }
        }
    }

    public Object getRequestTag() {
        return ((BaseActivity) getContext()).getRequestTag();
    }
}
