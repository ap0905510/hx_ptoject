package com.yw.panel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
        iv_light = (ImageView) findViewById(R.id.iv_light);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashLights();

                if (!mLuckyPanal.isStart()) {
                    mLuckyPanal.luckyStart();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            Double d = random.nextDouble();
                            d = d * MAX;
                            Log.e("YW", "d = " + d);
                            if (d <= 2) {
                                mLuckyPanal.luckyStart(0);       //单反相机 0-2
                            } else if (d > 2 && d <= 10 ) {
                                mLuckyPanal.luckyStart(1);       //IPAD 2-10
                            } else if (d > 10 && d <= 500) {
                                mLuckyPanal.luckyStart(3);       //IPHONE 10-500
                            } else if (d > 500 && d <= 1000) {
                                mLuckyPanal.luckyStart(4);       //妹子一只 500-1500
                            } else if (d > 1000 && d <= 4500) {
                                mLuckyPanal.luckyStart(2);       //恭喜发财 1500-4500
                            } else if (d > 4500 && d <= 7500) {
                                mLuckyPanal.luckyStart(5);
                            } else if (d > 7500 && d <= 9000) {
                                mLuckyPanal.luckyStart(6);       //1 7500-9000
                            } else {
                                mLuckyPanal.luckyStart(7);       //2 9000-10000
                            }
                            //mStartBtn.setImageResource(R.mipmap.stop);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mLuckyPanal.luckyEnd();
                                    mStartBtn.setImageResource(R.mipmap.start);
                                    mHandler.sendEmptyMessage(1);
                                }
                            }, 3000);
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

    }

    private ImageView iv_light;
    //设置一个时间常量，此常量有两个作用，1.圆灯视图显示与隐藏中间的切换时间；2.指针转一圈所需要的时间，现设置为500毫秒
    private static final long ONE_WHEEL_TIME = 500;
    //记录圆灯视图是否显示的布尔常量
    private boolean lightsOn = true;
    //子线程与UI线程通信的handler对象
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    if (lightsOn) {
                        // 设置lightIv不可见
                        iv_light.setVisibility(View.INVISIBLE);
                        lightsOn = false;
                    } else {
                        // 设置lightIv可见
                        iv_light.setVisibility(View.VISIBLE);
                        lightsOn = true;
                    }
                    break;
                case 1:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (timer != null) {
                                timer.cancel();
                                iv_light.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 2000);
                    break;
                default:
                    break;
            }
        };
    };

    Timer timer;
    //控制灯圈动画的方法
    private void flashLights() {
        timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                // 向UI线程发送消息
                mHandler.sendEmptyMessage(0);
            }
        };

        // 每隔ONE_WHEEL_TIME毫秒运行tt对象的run方法
        timer.schedule(tt, 0, ONE_WHEEL_TIME);
    }

}
