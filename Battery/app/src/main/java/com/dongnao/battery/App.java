package com.dongnao.battery;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.dongnao.battery.location.LocationService;

import java.util.List;

/**
 * @author Lance
 * @date 2018/1/14
 */

public class App extends Application {

    public static final String TAG = "YW";

    private Intent location;
    private static App applicationt;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.equals(BuildConfig.APPLICATION_ID + ":location", getProcessName(Process
                .myPid()))) {
            applicationt = this;
            location = new Intent(this, LocationService.class);
            //startService(location);
            this.bindService(location, new ServiceConn(), Context.BIND_AUTO_CREATE);
            Log.e(TAG, "********************");
        }
        Log.e(TAG, "..................");
    }

    class ServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.e(TAG, "onBindingDied");
        }
    }

    public static App getApplicationt() {
        return applicationt;
    }

    public Intent getLocation() {
        return location;
    }

    String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
