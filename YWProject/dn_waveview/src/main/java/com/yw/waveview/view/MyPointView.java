package com.yw.waveview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：create by YW
 * 日期：2018.03.09 14:40
 * 描述：
 */
public class MyPointView extends View {

    private Context mContext;

    PointRadius mPointRadius;
    Paint mCirclePaint;

    public MyPointView(Context context) {
        this(context, null);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mPointRadius = new PointRadius(100);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GREEN);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(300, 300, getPointRadius(), mCirclePaint);

        super.onDraw(canvas);
    }

    /**
     * 刷新UI
     * @param radius
     */
    public void setPointRadius(int radius) {
        mPointRadius.setRadius(radius);
        invalidate();
    }

    public int getPointRadius() {
        return mPointRadius.getRadius();
    }
}
