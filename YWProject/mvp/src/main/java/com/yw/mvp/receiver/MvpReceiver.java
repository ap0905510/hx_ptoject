package com.yw.mvp.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * 作者：create by YW
 * 日期：2018.03.01 14:08
 * 描述：静态注册广播
 */
public class MvpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.e("YW", "MvpReceiver");
        }
    }
}
