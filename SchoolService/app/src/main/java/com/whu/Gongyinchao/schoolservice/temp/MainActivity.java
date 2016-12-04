package com.whu.Gongyinchao.schoolservice.temp;

import android.app.LocalActivityManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSPagerSlidingTabStrip;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSViewPager;
import com.whu.Gongyinchao.schoolservice.findmodule.FindView;
import com.whu.Gongyinchao.schoolservice.minemodule.MineView;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private SSViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private SSPagerSlidingTabStrip mIndicator;
    private DestoryListener outdoorView;
    private DestoryListener indoorView;
    private DestoryListener findView;
    private DestoryListener mineView;
     private LocalActivityManager manager;

     private MainViewPagerAdapter mainViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (SSViewPager) findViewById(R.id.main_vierpager);
        mIndicator = (SSPagerSlidingTabStrip) findViewById(R.id.main_indicator);

        List<View> pagers = new ArrayList<>(3);
      //  outdoorView = new OutdoorView(this);
      //  pagers.add((OutdoorView)outdoorView);
       // indoorView = new IndoorView(this);
       // pagers.add((IndoorView)indoorView);
        findView = new FindView(this);
        pagers.add((FindView)findView);
        mineView = new MineView(this);
        pagers.add((MineView)mineView);

        mPagerAdapter = new MainViewPagerAdapter(pagers);
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onDestroy() {
        outdoorView.onDestory();
        indoorView.onDestory();
        findView.onDestory();
        mineView.onDestory();
        super.onDestroy();
    }
}
