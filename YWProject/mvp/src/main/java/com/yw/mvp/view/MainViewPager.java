package com.yw.mvp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 作者：create by YW
 * 日期：2018.03.19 11:03
 * 描述：重写Viewpager，滑动时过渡连贯
 */
public class MainViewPager extends ViewPager {

    public MainViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MainViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
