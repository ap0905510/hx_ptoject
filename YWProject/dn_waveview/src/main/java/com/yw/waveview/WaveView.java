package com.yw.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by yw on 2017/8/20.
 */

public class WaveView extends View {

    private static final String TAG = "YW";

    private Paint paint;
    private Path path;
    private int wavelength = 400;
    private int dx;
    private int dy;
    private Bitmap mBitmap;
    private int width;
    private int height;
    private int waterColor;

    private int waveHeight = 80;
    private boolean waveView_rise;
    private int duration;
    private int originY;
    private ValueAnimator animator;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        waveView_rise = a.getBoolean(R.styleable.WaveView_rise, false);
        duration = (int) a.getDimension(R.styleable.WaveView_duration, 2000);
        originY = (int) a.getDimension(R.styleable.WaveView_originY, 300);
        waveHeight = (int) a.getDimension(R.styleable.WaveView_waveHeight, 160);
        wavelength = (int) a.getDimension(R.styleable.WaveView_waveLength, 400);
        waterColor = a.getColor(R.styleable.WaveView_waterColor, 0x3f51b5);
        a.recycle();

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 1;//2
//        if (waveView_boatBitmap > 0) {
//            mBitmap = BitmapFactory.decodeResource(getResources(), waveView_boatBitmap, options);
//            mBitmap = getCircleBitmap(mBitmap); //获取圆形头像
//        } else {
//            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round, options);
//        }

        paint = new Paint();
        paint.setColor(waterColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE); //填充海水颜色

        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.e(TAG, "onMeasure NO2");

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        width = widthSize;
        height = heightSize;
        if (originY == 0) {
            originY = height;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.e(TAG, "onFinishInflate NO1");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.e(TAG, "onSizeChanged NO3");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


        Log.e(TAG, "layout No4: l: " + l + "\tt: " + t);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.e(TAG, "onLayout No5: l: " + left + "\tt: " + top);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Log.e(TAG, "onDraw NO5");

        //计算path
        setPathData();
        canvas.drawPath(path, paint);

    }

    private void setPathData() {
        path.reset();
        //构成一个封闭的波浪多边形
        int halfWaveLength = wavelength / 2; //半个波长
        path.moveTo(-wavelength + dx, originY);//起始点
        for (int i = -wavelength; i < width + wavelength; i += wavelength) {
            path.rQuadTo(halfWaveLength / 2, -waveHeight, halfWaveLength, 0);
            path.rQuadTo(halfWaveLength / 2, waveHeight, halfWaveLength, 0);
        }

        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();

    }

    public void startAnimation() {
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = (float) valueAnimator.getAnimatedValue();
                dx = (int) (wavelength * fraction); //平移量 0 ~ 1
                postInvalidate();
            }
        });
        animator.start();
    }
}
