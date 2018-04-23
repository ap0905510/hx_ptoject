package com.yw.panel2;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：create by YW
 * 日期：2017.08.01 13:55
 * 描述：
 */
public class PanelView extends RelativeLayout {

    private static final double MAX = 10000.0;

    LuckyPanView luckyPanView;
    ImageView ivStart;
    ImageView ivLight;

    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        LayoutParams rlParams = new LayoutParams(-1, -1);
        rlParams.setMargins(5, 5, 5, 5);
        this.setLayoutParams(rlParams);

//        luckyPanView = new LuckyPanView(context);
//        luckyPanView.setId(luckyPanView.hashCode());
//        luckyPanView.setPadding(30, 30, 30, 30);
//        LayoutParams params = new LayoutParams(-1, -1);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        this.addView(luckyPanView, -1, -1);
//
//        iv_start = new ImageView(context);
//        iv_start.setId(iv_start.hashCode());
//        iv_start.setImageResource(R.mipmap.start);
//        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        this.addView(iv_start, params);
//
//        iv_light = new ImageView(context);
//        iv_light.setId(iv_light.hashCode());
//        params = new LayoutParams(540, 540);
//        iv_light.setImageResource(R.mipmap.light);
//        this.addView(iv_light, params);

        View view = LayoutInflater.from(context).inflate(R.layout.activity_panel, null);
        this.addView(view);
        luckyPanView = (LuckyPanView) view.findViewById(R.id.lpv_luckyPanel);
        ivStart = (ImageView) view.findViewById(R.id.iv_start);
        ivLight = (ImageView) view.findViewById(R.id.iv_light);

        init();
    }

    public LuckyPanView getLuckyPanView() {
        return luckyPanView;
    }

    public ImageView getIvStart() {
        return ivStart;
    }

    public ImageView getIvLight() {
        return ivLight;
    }

    private void init() {
        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashLights();

                if (!luckyPanView.isStart()) {
                    luckyPanView.luckyStart();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            Double d = random.nextDouble();
                            d = d * MAX;
                            Log.e("YW", "d = " + d);
                            if (d <= 2) {
                                luckyPanView.luckyStart(0);       //单反相机 0-2
                            } else if (d > 2 && d <= 10 ) {
                                luckyPanView.luckyStart(1);       //IPAD 2-10
                            } else if (d > 10 && d <= 500) {
                                luckyPanView.luckyStart(3);       //IPHONE 10-500
                            } else if (d > 500 && d <= 1000) {
                                luckyPanView.luckyStart(4);       //妹子一只 500-1500
                            } else if (d > 1000 && d <= 4500) {
                                luckyPanView.luckyStart(2);       //恭喜发财 1500-4500
                            } else if (d > 4500 && d <= 7500) {
                                luckyPanView.luckyStart(5);
                            } else if (d > 7500 && d <= 9000) {
                                luckyPanView.luckyStart(6);       //1 7500-9000
                            } else {
                                luckyPanView.luckyStart(7);       //2 9000-10000
                            }
                            //mStartBtn.setImageResource(R.mipmap.stop);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    luckyPanView.luckyEnd();
                                    ivStart.setImageResource(R.mipmap.start);
                                    mHandler.sendEmptyMessage(1);
                                }
                            }, 3000);
                        }
                    }, 3000);
                }
            }
        });
    }

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
                        ivLight.setVisibility(View.INVISIBLE);
                        lightsOn = false;
                    } else {
                        // 设置lightIv可见
                        ivLight.setVisibility(View.VISIBLE);
                        lightsOn = true;
                    }
                    break;
                case 1:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (timer != null) {
                                timer.cancel();
                                ivLight.setVisibility(View.VISIBLE);
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
                //mHandler.sendEmptyMessage(0);
                mHandler.sendEmptyMessage(0);
            }
        };

        // 每隔ONE_WHEEL_TIME毫秒运行tt对象的run方法
        timer.schedule(tt, 0, ONE_WHEEL_TIME);
    }
}
