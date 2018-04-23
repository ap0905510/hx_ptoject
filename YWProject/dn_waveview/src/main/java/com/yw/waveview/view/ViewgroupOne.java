package com.yw.waveview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：create by YW
 * 日期：2018.03.14 10:15
 * 描述：
 */

public class ViewgroupOne extends ViewGroup {

    public ViewgroupOne(Context context) {
        super(context);
    }

    public ViewgroupOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount(); //获取子View的数量
        if (childCount > 0) {
            View child = getChildAt(0);//获取第一个子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            View child = getChildAt(0);
            int measuredWidth = child.getMeasuredWidth(); //raw measure width 调用measure()后执行该方法
            int measureHeight = child.getMeasuredHeight(); //getWidth() 只能layout()结束后才能拿到正确的宽高

            child.layout(100,200, measuredWidth, measureHeight);
            child.measure(measuredWidth, measureHeight + 200);

            //View v = getChildAt(1);
            //v.layout(0, 0 , v.getMeasuredWidth() + 200, v.getMeasuredHeight() + 200);
        }
    }

}
















