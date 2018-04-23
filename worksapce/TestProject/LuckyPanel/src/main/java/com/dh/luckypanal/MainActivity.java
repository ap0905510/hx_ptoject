package com.dh.luckypanal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final double MAX = 10000.0;

    private LuckyPanView mLuckyPanal;
    private ImageView mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLuckyPanal = (LuckyPanView) findViewById(R.id.id_luckyPanal);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();
                Double d = random.nextDouble();

                if (!mLuckyPanal.isStart()) {
                    d = d * MAX;
                    Log.e("YW", "d = " + d);
                    if (d <= 2) {
                        mLuckyPanal.luckyStart(0);
                    } else if (d > 2 && d <= 10 ) {
                        mLuckyPanal.luckyStart(1);
                    } else if (d > 10 && d <= 500) {
                        mLuckyPanal.luckyStart(3);
                    } else if (d > 500 && d <= 1500) {
                        mLuckyPanal.luckyStart(4);
                    } else if (d > 1500 && d <= 6000) {
                        mLuckyPanal.luckyStart(2);
                    } else {
                        mLuckyPanal.luckyStart(5);
                    }
                    mStartBtn.setImageResource(R.mipmap.stop);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLuckyPanal.luckyEnd();
                            mStartBtn.setImageResource(R.mipmap.start);
                        }
                    }, 3000);
                } /*else {
                    if (!mLuckyPanal.isShouldEnd()) {
                        mLuckyPanal.luckyEnd();
                        mStartBtn.setImageResource(R.mipmap.start);
                    }
                }*/
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendCustom(MainActivity.this);
            }
        }, 2000);
    }

    public void sendCustom(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("hello");//"我是呼信"

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);//设置这个标志当用户单击面板就可以让通知将自动取消
        builder.setOngoing(true);

        builder.setTicker("hello world!"); //通知首次出现在通知栏，带上升动画效果的
        builder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间

        builder.build().vibrate = new long[]{0, 300, 500, 700};

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());

    }

}
