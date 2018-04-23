package com.yw.waveview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import com.yw.waveview.view.MyPointView;

public class MainActivity extends AppCompatActivity {

    MyPointView mPointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WaveView waveView = (WaveView) findViewById(R.id.waveView);
        final WaveView wv2 = findViewById(R.id.waveView2);
        final WaveView wv3 = findViewById(R.id.waveView3);
        waveView.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wv2.startAnimation();
                wv3.startAnimation();
            }
        }, 300);

        //mPointView = findViewById(R.id.mpv);

        configuration();

        findViewById();
    }

    ImageView iv_01;

    void findViewById() {
        iv_01 = findViewById(R.id.iv_01);
        //drawBitmap();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yw);

        xfermode(bitmap);

        //matrix(bitmap);
    }

    public void changeCircle(View view) {
        mPointView.setPointRadius(50);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100, 300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mPointView, "pointRadius", 0, 100, 300);
        objectAnimator.setRepeatCount(10);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    public void configuration() {
        Configuration configuration = getResources().getConfiguration();
        int mcc = configuration.mcc;
        int mnc = configuration.mnc;
        Log.e("YW", "mcc: " + mcc + "\tmnc: " + mnc);
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("YW", "横屏");
        }

        //ViewConfiguration
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
        //两次点击的距离
        int doubleDistance = viewConfiguration.getScaledDoubleTapSlop();
        //Fling的惯性 最小的速度
        int flingMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        //甩(Fling)的惯性 最大的速度
        int flingMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        //识别滑动溢出的最小距离
        int touchSlopDistance = viewConfiguration.getScaledTouchSlop();
        //是否有虚拟按键
        boolean hasVirtualKey = viewConfiguration.hasPermanentMenuKey();
        //获取View滑动过程中滚动条的消隐时间
        int scrollBarFadeTime = ViewConfiguration.getScrollBarFadeDuration();
        //按住状态转变为长按状态需要的时间
        int longPressTime = ViewConfiguration.getLongPressTimeout();


        Log.e("YW", "是否有虚拟按键" + hasVirtualKey);

    }

    /**
     * 1.常规系统传递Canvas,来自ViewRootImpl的Surface,由系统创建
     */
    void drawBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(800, 400, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap); //传参保存bitmap的像素
        canvas.drawColor(Color.MAGENTA);
        canvas.save();
        Paint paint = new Paint(); //画笔
        paint.setAntiAlias(true); //抗锯齿 平滑过渡
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(60);
        canvas.drawText("Hello world! I'm coming.", 100, 200, paint);
        iv_01.setImageBitmap(bitmap);
        canvas.restore();
    }


    float cornerPixel = 10f;

    /**
     * xfermode 图片融合
     *
     * Shader的主要子类如下：
         BitmapShader———图像渲染
         LinearGradient——–线性渲染
         RadialGradient——–环形渲染
         SweepGradient——–扫描渲染
         ComposeShader——组合渲染

     * @param bitmap
     */
    void xfermode(Bitmap bitmap) {
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        // 创建临时画板存储像素
        Bitmap roundCornerBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        // 初始化画板
        Canvas canvas = new Canvas(roundCornerBitmap);
        canvas.save();

        // 初始化画笔
        Paint paint = new Paint(); //画笔
        paint.setAntiAlias(true); //抗锯齿 平滑过渡
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);

        //渲染
        //LinearGradient l = new LinearGradient(0, 0, 100, 100, Color.BLUE, Color.GRAY, Shader.TileMode.REPEAT);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint.setShader(shader);

        // 初始化一个矩形
        Rect rect = new Rect(0, 0, mWidth, mHeight);
        RectF rectF = new RectF(rect);
        // 画圆角的图
        //canvas.drawRoundRect(rectF, cornerPixel, cornerPixel, paint);
        canvas.drawOval(rectF, paint);
        // 设置xformode
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        // 绘制
        canvas.drawBitmap(bitmap, rect, rect, paint);
        canvas.restore();
        iv_01.setImageBitmap(roundCornerBitmap); // 返回的是融合后的Bitmap
    }

    /**
     * 矩阵 位移
     */
    void matrix(Bitmap bitmap) {
        Matrix matrix = new Matrix();

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);

        Bitmap rawBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(rawBitmap);

        matrix.setRotate(15);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, matrix, paint);
        canvas.drawLine(50, 50 , 300, 300, paint);

        matrix.setTranslate(30, 20); //平移
        matrix.postScale(0.5f, 0.6f); //缩放
        matrix.postRotate(-30); //旋转
        canvas.drawBitmap(bitmap, matrix, paint);

        iv_01.setImageBitmap(rawBitmap);
    }


}
