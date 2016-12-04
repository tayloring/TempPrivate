package com.whu.Gongyinchao.schoolservice.common.app;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;
import com.whu.Gongyinchao.schoolservice.framework.location.DefaultLocationListener;
import com.whu.Gongyinchao.schoolservice.framework.location.LocationManager;
import com.whu.Gongyinchao.schoolservice.framework.location.PresenterLocationCallBack;

import java.lang.ref.WeakReference;

/**
 * Created by panfei on 16/4/14.
 */
public abstract class BasePresenter<T extends PresenterCallBack> {

    protected WeakReference<Context> mContext;
    protected T mCallBack;

    private static LocationManager sLocationManager = SchoolServiceApplication.getInstance().getLocationManager();

    public BasePresenter(Context context, T mCallBack) {
        mContext = new WeakReference<Context>(context);
        this.mCallBack = mCallBack;
    }

    public void requestLocation() {
        sLocationManager.start();
    }

    protected void doAfterLocate(final IPrensenterRequest callback) {
        ((DefaultLocationListener)sLocationManager.getLocationListener()).setCallBack(new PresenterLocationCallBack() {
            @Override
            public void onPresenterLocationCallBack() {
                callback.request();
                sLocationManager.stop();
            }
        });

        sLocationManager.start();
    }

    protected void doDirect(final IPrensenterRequest callback) {
        callback.request();
    }

    public void onDestory() {
        mContext.clear();
        mCallBack = null;
    }

    protected interface IPrensenterRequest {
        void request();
    }
}
