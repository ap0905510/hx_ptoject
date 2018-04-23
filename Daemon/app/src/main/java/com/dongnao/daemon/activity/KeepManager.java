package com.dongnao.daemon.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class KeepManager {
    private static final KeepManager ourInstance = new KeepManager();

    public static KeepManager getInstance() {
        return ourInstance;
    }

    private WeakReference<Activity> mKeepAct;

    private KeepManager() {
    }

    private KeepReceiver keepReceiver;

    /**
     * 注册 开屏 关屏关公
     *
     * @param context
     */
    public void registerKeep(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        keepReceiver = new KeepReceiver();
        context.registerReceiver(keepReceiver, filter);
    }

    /**
     * 反注册广播接收者
     *
     * @param context
     */
    public void unregisterKeep(Context context) {
        if (null != keepReceiver) {
            context.unregisterReceiver(keepReceiver);
        }
    }

    /**
     * 开启Activity
     * @param context
     */
    public void startKeep(Context context) {
        Intent intent = new Intent(context, KeepActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finishKeep() {
        if (null != mKeepAct) {
            Activity activity = mKeepAct.get();
            if (null != activity) {
                activity.finish();
            }
            mKeepAct = null;
        }
    }

    public void setKeep(KeepActivity keep) {
        mKeepAct = new WeakReference<Activity>(keep);
    }
}
