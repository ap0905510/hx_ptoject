package com.yw.mvp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yw.mvp.R;

/**
 * 作者：create by YW
 * 日期：2018.03.03 14:42
 * 描述：
 */

public class CancelNoticeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentText("取消守护进程通知栏")
                    .setSmallIcon(R.mipmap.ic_launcher);
            startForeground(DaemonService.NOTICE_ID, builder.build());

            //取消自己的前台进程
            stopForeground(true);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(DaemonService.NOTICE_ID);

            //停止服务（自己停止自己）
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
