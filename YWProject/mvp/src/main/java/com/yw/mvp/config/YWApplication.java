package com.yw.mvp.config;

import android.app.Application;
import android.content.Intent;

import com.yw.mvp.db.helper.GreenDbManager;
import com.yw.mvp.service.DaemonService;

/**
 * 作者：create by YW
 * 日期：2018.02.23 16:33
 * 描述：
 */

public class YWApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GreenDbManager.instance(this);

        startService(new Intent(this, DaemonService.class));
    }
}
