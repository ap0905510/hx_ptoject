package com.yw.touch;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：create by YW
 * 日期：2017.07.25 15:47
 * 描述：
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mList = new ArrayList<>();

    public ViewPagerAdapter(Context context) {
        for (int i = 0; i < 3; i++) {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.img);
            View view  = new View(context);
            view.setBackgroundDrawable(drawable);
            mList.add(view);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position), 0);
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(mList.get(position));
    }

}
