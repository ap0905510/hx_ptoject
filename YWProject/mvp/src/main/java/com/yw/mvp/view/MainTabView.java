package com.yw.mvp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.mvp.R;

/**
 * 作者：create by YW
 * 日期：2018.03.19 14:29
 * 描述：TabLayout的icon视图
 */

public class MainTabView extends LinearLayout {

    private Context mContext;

    public MainTabView(Context context) {
        this(context, null);
    }

    public MainTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        TextView tv = new TextView(mContext);
        tv.setTextSize(12f);
        //tv.setTextColor(mContext.getColorStateList(R.color.y_tb_text_color));
    }
}
