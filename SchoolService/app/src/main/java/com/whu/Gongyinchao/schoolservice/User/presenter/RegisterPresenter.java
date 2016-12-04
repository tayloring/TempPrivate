package com.whu.Gongyinchao.schoolservice.User.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;

import java.util.Map;

/**
 * Created by 龚银超 on 2016/8/22.
 */
public class RegisterPresenter extends NetApiProvider {


    private RegisterPresenter(Context context) {
        super(context);
    }

    @Override
    public <T> void post(String url, Map<String, String> params, Class<T> clazz, UICallBack<T> UICallBack, StringCallBack strCallBack) {
        super.post(url, params, clazz, UICallBack, strCallBack);
    }




}
