package com.dongnao.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class Battery {

    /**
     * 是否正在充电
     * @return
     */
    public static boolean isPlugged(Context context){
        //发送个包含充电状态的广播，并且是一个持续的广播
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null,filter);
        //获取充电状态
        int isPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean acPlugged = isPlugged == BatteryManager.BATTERY_PLUGGED_AC;
        boolean usbPlugged = isPlugged == BatteryManager.BATTERY_PLUGGED_USB;
        boolean wifiPlugged = isPlugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        return acPlugged || usbPlugged || wifiPlugged;
    }


    /**
     * 是否正在使用wifi
     * @param context
     * @return
     */
    public static boolean isWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前活动的网络信息
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (null != activeNetworkInfo && activeNetworkInfo.isConnected() &&
                activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }
}
