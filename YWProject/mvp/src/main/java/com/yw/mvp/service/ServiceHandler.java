package com.yw.mvp.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * 作者：create by YW
 * 日期：2018.03.01 11:14
 * 描述：全局的Handler
 */
public class ServiceHandler extends Handler {

    private static final String TAG = "YW-ServiceHandler";

    public ServiceHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        Log.e(TAG, "当前线程: " + Thread.currentThread());
        Log.e(TAG, "handleMessage: " + msg.what);
    }

}
