package com.yw.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 作者：create by YW
 * 日期：2017.07.25 16:14
 * 描述：自定义HorizontalScrollView中合理处理Touch事件的拦截和分发
 */
public class HorizontalScrollViewSubclass extends HorizontalScrollView {

    public HorizontalScrollViewSubclass(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewSubclass(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private float mLastX; //上一次的X坐标
    private float mLastY; //上一次的Y坐标
    private float mDistanceX; //滑动的X距离
    private float mDistanceY; //滑动的Y距离

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                //刚开始按下时候滑动的距离是0
                mDistanceX = 0f;
                mDistanceY = 0f;
                //获取相对自身的坐标
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //获取实时的坐标
                float currentX = ev.getX();
                float currentY = ev.getY();
                //计算两次的实际滑动距离
                mDistanceX = Math.abs(currentX - mLastX);
                mDistanceY = Math.abs(currentY - mLastY);
                //当前的坐标即是下一次的开始坐标
                mLastX = currentX;
                mLastY = currentY;
                //判断滑动的距离处理事件
                if (mDistanceX > mDistanceY) {
                    return false; //不拦截，分发让子view处理
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
