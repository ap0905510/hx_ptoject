package com.yw.waveview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

/**
 * 作者：create by YW
 * 日期：2018.03.13 09:48
 * 描述：
 */

public class CustomView extends View {

    private static final String TAG = "YW-CustomView";

    private Context mContext;
    int mViewWidth;
    int mViewHeight;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "mViewWidth: " + mViewWidth + "\t mViewHeight: " + mViewHeight);
    }

    //第一步：开始速度追踪
    VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        return super.onTouchEvent(event);
    }

    //第二步：获取追踪到的速度
    int getScrollXTracker() {
        //每秒滑动的像素 单位
        velocityTracker.computeCurrentVelocity(1000);
        //每秒的所滑动的像素值
        int xVelocity = (int) velocityTracker.getXVelocity();
        return xVelocity;
    }

    //第三步：解除速度追踪
    void releaseTracker() {
        if (null != velocityTracker) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
}
