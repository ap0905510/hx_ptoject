package com.yw.mvp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 作者：create by YW
 * 日期：2018.03.01 10:52
 * 描述：可用于耗时操作：onHandleIntent
 */
public class MyIntentService extends IntentService {

    public static final String TAG = "YW-MyIntentService";

    public MyIntentService() {
        this("");
    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent");
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.e(TAG, "HandleIntent onCreate");
//    }
}