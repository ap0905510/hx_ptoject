package com.yw.transview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yw.transview.R;

/**
 * 作者：create by YW
 * 日期：2017.08.17 10:08
 * 描述：
 */

public class MyLinearLayout extends LinearLayout {

    private Context mContext;
    private AttributeSet mSet;

    int declare_trans_y;
    int declare_alpha_y;

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSet = attrs;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        Log.e("YW", "addView() ------------------- ");

        MyFrameLayout layout = new MyFrameLayout(mContext);
        layout.addView(child);
        if (declare_trans_y == layout.DECLARE_TRANS_Y) {
            layout.setTranslation(child, declare_trans_y);
        } else if (layout.getDeclare_alpha_y()) {
            layout.setTranslation(child, declare_alpha_y);
        }
        child = layout;

        super.addView(child, index, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }
}
