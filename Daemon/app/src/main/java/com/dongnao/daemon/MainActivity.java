package com.dongnao.daemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dongnao.daemon.account.AccountHelper;
import com.dongnao.daemon.activity.KeepManager;
import com.dongnao.daemon.jobschuduler.MyJobService;
import com.dongnao.daemon.service.ForgroundService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //通过Activity 提权
//        KeepManager.getInstance().registerKeep(this);

        //通过Service 提权
//        startService(new Intent(this, ForgroundService.class));

        //Stick 拉活
//        startService(new Intent(this,StickService.class));

        //账户拉活
        AccountHelper.addAccount(this);
        AccountHelper.autoSync();

        Log.e("YW", "onCreate...");

        //jobscheduler拉活
//        MyJobService.StartJob(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KeepManager.getInstance().unregisterKeep(this);
    }
}
