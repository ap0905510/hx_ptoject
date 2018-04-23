package com.yw.ffmpeg;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 作者：create by YW
 * 日期：2018.01.22 11:52
 * 描述：播放本地视频
 */
public class VideoView extends SurfaceView implements SurfaceHolder.Callback {

    static {
        System.loadLibrary("avcodec-56");
        System.loadLibrary("avdevice-56");
        System.loadLibrary("avfilter-5");
        System.loadLibrary("avformat-56");
        System.loadLibrary("avutil-54");
        System.loadLibrary("postproc-53");
        System.loadLibrary("swresample-1");
        System.loadLibrary("swscale-3");
        System.loadLibrary("native-lib");
    }

    SurfaceHolder mHolder;

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.RGBA_8888);

        mHolder.addCallback(this);
    }

    public void player(final String inputPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isPlayer) {
                    render(inputPath, mHolder.getSurface());
                    isPlayer = false;
                }
            }
        }).start();
    }

    public native void render(String input, Surface surface);

    volatile boolean isPlayer = true;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isPlayer = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isPlayer = false;
    }
}
