package com.whu.Gongyinchao.schoolservice.common.app;

import android.content.Context;
import android.util.AttributeSet;

import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSRelativeLayout;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.location.LocationManager;

/**
 * Created by panfei on 16/4/12.
 */
public abstract class BaseLayout extends SSRelativeLayout implements PresenterCallBack {

    protected LocationManager mLocationManager;

    public BaseLayout(Context context) {
        super(context);
    }

    public BaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mLocationManager = ((BaseActivity) getContext()).getLocationManager();
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BaseApi.getInstance().cacelAll(getRequestTag());
    }
}
