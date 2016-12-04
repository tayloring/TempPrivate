package com.whu.Gongyinchao.schoolservice.framework.net;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by panfei on 16/4/5.
 */
class ResponseHandler<T>{

    private ExecutorService mResponseHander;
    private WeakReference<Handler> handler = new WeakReference<Handler>(new Handler(Looper.getMainLooper()));

    public ResponseHandler(){
        mResponseHander = Executors.newFixedThreadPool(1);
    }

    public void start(Callable<T> callable, final NetApiProvider.UICallBack<T> uiCallBack){
        final Future<T> future = mResponseHander.submit(callable);
        if (handler == null || handler.get() == null) {
            return;
        }

        handler.get().post(new Runnable() {
            @Override
            public void run() {
                try {
                    uiCallBack.onResponse(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
