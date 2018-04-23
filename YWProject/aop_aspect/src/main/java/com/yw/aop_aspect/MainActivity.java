package com.yw.aop_aspect;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yw.aop_aspect.annotation.BehaviorTrace;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "YW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onSay(1000);

    }

    /**
     * 摇一摇
     *
     * @param view
     */
    @BehaviorTrace("摇一摇")
    public void onShark(View view) {
        Log.e(TAG, "摇一摇");
        SystemClock.sleep(1000);
    }

    /**
     * 录语音
     *
     * @param view
     */
    @BehaviorTrace("录语音")
    public void onAudio(View view) {
        Log.e(TAG, "录语音");
    }

    /**
     * 录视频
     *
     * @param view
     */
    @BehaviorTrace("录视频")
    public void onVideo(View view) {
    }

    /**
     * 说一说
     *
     * @param
     */
    @BehaviorTrace("说一说")
    public void onSay(int id) {
        Log.e(TAG, "说一说" + id);
    }
}
