package com.whu.Gongyinchao.schoolservice.loginmodule.presenter;

import android.content.Context;

import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.data.LoginResult;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.loginmodule.interfaces.LoginCallBack;

/**
 * Created by panfei on 16/4/10.
 */
public class LoginPresenter extends BasePresenter<LoginCallBack>{

    public LoginPresenter(Context context, LoginCallBack login) {
        super(context, login);
    }

    public void login(String account, String password){

        BaseApi.getInstance().login(mCallBack.getRequestTag(), account, password, new NetApiProvider.UICallBack<LoginResult>() {
            @Override
            public void onResponse(LoginResult loginResult) {
                if (mCallBack == null) {
                    throw new RuntimeException("presenter is destoryed");
                }

                mCallBack.loginSuccess();
            }

            @Override
            public void onErrorResponse(int errorCode, String message) {
                if (mCallBack == null) {
                    throw new RuntimeException("presenter is destoryed");
                }

                mCallBack.loginFailed(message);
            }
        });
    }
}
