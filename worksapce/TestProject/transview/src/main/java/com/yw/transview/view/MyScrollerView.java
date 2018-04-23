package com.yw.transview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 作者：create by YW
 * 日期：2017.08.17 10:08
 * 描述：
 */

public class MyScrollerView extends ScrollView {

    private Context mContext;

    public MyScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        Log.e("YW", "scroller addView() ------------------- ");
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
