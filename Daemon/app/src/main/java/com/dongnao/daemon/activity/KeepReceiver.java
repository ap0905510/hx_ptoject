package com.dongnao.daemon.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class KeepReceiver extends BroadcastReceiver {
    private static final String TAG = "KeepReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "receive:" + action);
        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
            //关屏 开启1px activity
            KeepManager.getInstance().startKeep(context);
        } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {
            KeepManager.getInstance().finishKeep();
        }
    }
}
