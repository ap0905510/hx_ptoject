package com.yw.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 作者：create by YW
 * 日期：2018.03.03 11:27
 * 描述：
 */

public class PView extends ViewGroup {

    public PView(Context context) {
        super(context);
    }

    public PView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
