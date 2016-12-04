package com.whu.Gongyinchao.schoolservice.loginmodule.interfaces;

import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/10.
 */
public interface LoginCallBack extends PresenterCallBack{

    void loginSuccess();

    void loginFailed(String msg);

}
