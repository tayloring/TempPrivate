package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.baidu.location.BDLocation;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.interfaces.LocationCallBack;

/**
 * Created by panfei on 16/4/16.
 */
public class SSViewPager extends ViewPager implements LocationCallBack{

    public SSViewPager(Context context) {
        super(context);
    }

    public SSViewPager(Context context, AttributeSet attrs) {
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
