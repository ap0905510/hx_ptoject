package com.yw.waveview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：create by YW
 * 日期：2018.03.16 11:40
 * 描述：
 */

public class CanvasDraw extends View {

    Paint mPaint;

    public CanvasDraw(Context context) {
        this(context, null);
    }

    public CanvasDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        //canvas(canvas);
        pathEffect(canvas);
        canvas.restore();
    }

    void pathEffect(Canvas canvas) {
        canvas.translate(0,300);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(15, 60);
        for (int i = 0; i <= 35; i++) {
            path.lineTo(i * 30, (float) (Math.random() * 150));
        }
        canvas.drawPath(path, paint);
        canvas.translate(0, 400);
        paint.setPathEffect(new CornerPathEffect(60));
        canvas.drawPath(path, paint);
        canvas.translate(0, 400);
        paint.setPathEffect(new DashPathEffect(new float[] {15, 8}, 1));
        canvas.drawPath(path, paint);
    }

    /**
     *
     canvas.translate
     canvas.rotate
     canvas.clipRect
     canvas.save和canvas.restore
     PorterDuffXfermode
     Bitmap和Matrix
     Shader
     PathEffect
     */
    void canvas(Canvas canvas) {
        // 1.canvas.translate
        canvas.drawColor(Color.GREEN);
        canvas.drawLine(100, 100, 300, 100, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawText("蓝色字体前translate", 150, 150, mPaint);
        mPaint.reset();

        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        canvas.translate(0, 100); //坐标系平移
        canvas.drawText("蓝色字体后translate", 150, 150, mPaint);
        mPaint.reset();

        // 2.canvas.rotate
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        canvas.rotate(45); //坐标系旋转
        canvas.drawText("黄色字体后rotate", 250, 150, mPaint);
        mPaint.reset();
        canvas.rotate(-45);

        // 3.canvas.clipRect 裁剪
        mPaint.setTextSize(40);
        canvas.drawText("裁剪前的文本clipRect", 20, 0, mPaint);

        RectF r3 = new RectF(50, 50, 400, 400);
        canvas.clipRect(r3);
        canvas.drawColor(Color.WHITE);
        canvas.drawText("裁剪后的文本clipRect", 60, 80, mPaint);

    }

    void paint(Canvas canvas) {
        Paint mPaint = new Paint(); //初始化画笔
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 600, 600, mPaint);
        mPaint.reset(); //重置画笔

        // 划线
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(220, 220, 400, 400, mPaint);
        mPaint.reset();

        // 画矩形
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(128);
        mPaint.setStyle(Paint.Style.FILL); // 填充满
        mPaint.setStrokeWidth(10);
        RectF rectF = new RectF(30, 60, 350, 350);
        canvas.drawRect(rectF, mPaint);
        mPaint.reset();

        // 画圆
        mPaint.setColor(Color.YELLOW);
        //mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(150);
        canvas.drawCircle(300, 300, 200, mPaint);
        mPaint.reset();

        // 绘制文本
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);
        mPaint.setUnderlineText(true);
        canvas.drawText("Hello world", 100, 300, mPaint);
        mPaint.reset();

        // 画椭圆
        mPaint.setColor(Color.CYAN);
        mPaint.setAlpha(180);
        RectF r2 = new RectF(100, 300, 400, 600);
        canvas.clipRect(r2);
        canvas.drawOval(r2, mPaint);
    }
}
