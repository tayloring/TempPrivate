package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.astuetz.PagerSlidingTabStrip;
import com.baidu.location.BDLocation;
import com.whu.Gongyinchao.schoolservice.common.interfaces.LocationCallBack;

/**
 * Created by panfei on 16/4/9.
 */
public class SSPagerSlidingTabStrip extends PagerSlidingTabStrip implements LocationCallBack{

    public SSPagerSlidingTabStrip(Context context) {
        super(context);
    }

    public SSPagerSlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SSPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setViewPager(ViewPager pager) {
        super.setViewPager(pager);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        super.setOnPageChangeListener(listener);
    }

    @Override
    public void locating() {

    }

    @Override
    public void locateFailed() {

    }

    @Override
    public void locateSuccess(BDLocation location) {

    }
}
