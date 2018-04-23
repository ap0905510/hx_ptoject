package com.yw.transview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.yw.transview.R;

/**
 * 作者：create by YW
 * 日期：2017.08.17 10:07
 * 描述：
 */
public class MyFrameLayout extends FrameLayout {

    public static final int DECLARE_TRANS_Y = 0x0001;
    public static final int DECLARE_ALPHA_Y = 0x0002;

    int declare_trans_y;
    int declare_alpha_y;

    private Context mContext;

    public MyFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyFrameLayout);

        declare_trans_y = a.getInt(R.styleable.MyFrameLayout_declare_trans_y, -1);
        declare_alpha_y = a.getInt(R.styleable.MyFrameLayout_declare_alpha_y, -1);

        a.recycle();
    }

    public boolean getDeclare_trans_y() {
        return declare_trans_y == DECLARE_TRANS_Y;
    }

    public boolean getDeclare_alpha_y() {
        return declare_alpha_y == DECLARE_ALPHA_Y;
    }

    public int getDeclare_Trans_Alpha_y() {
        return declare_trans_y | declare_alpha_y;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MyFrameLayout);

        declare_trans_y = a.getInt(R.styleable.MyFrameLayout_declare_trans_y, -1);
        declare_alpha_y = a.getInt(R.styleable.MyFrameLayout_declare_alpha_y, -1);

        a.recycle();
        return super.generateLayoutParams(attrs);
    }

    public void setTranslation(View child, int declareStyle) {
        Log.e("YW", "**********");
        if (declareStyle == DECLARE_TRANS_Y) {
            child.setTranslationY(300f);
        } else if (declareStyle == DECLARE_ALPHA_Y) {
            child.setAlpha(0.5f);
        }
    }

}
