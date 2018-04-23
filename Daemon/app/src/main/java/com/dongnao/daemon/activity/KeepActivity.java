package com.dongnao.daemon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class KeepActivity extends Activity {

    private static final String TAG = "KeepActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"启动Keep");
        Window window = getWindow();
        //设置这个act 左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        //宽 高都为1
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = 1;
        attributes.height = 1;
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);

        KeepManager.getInstance().setKeep(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"关闭Keep");
    }
}
