package com.yw.mvp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yw.letterbar.letter.LetterDemoFragment;
import com.yw.mvp.R;
import com.yw.mvp.fragment.AddressBookFragment;
import com.yw.mvp.fragment.ContactsFragment;
import com.yw.mvp.fragment.LiveRoomFragment;
import com.yw.mvp.fragment.MeFragment;

/**
 * 作者：create by YW
 * 日期：2018.03.19 11:53
 * 描述: MainActivity & MainViewPager的适配器
 */

public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_ID_MSG = 0;
    private static final int TAB_ID_DIALOGUE = 1;
    private static final int TAB_ID_LIVEROOM = 2;
    private static final int TAB_ID_MINE = 3;

    private Context mContext;
    private String[] mTitleString;

    private int[] imageResId = {
            R.drawable.y_main_tb_bg_msg,
            R.drawable.y_main_tb_bg_dialogue,
            R.drawable.y_main_tb_bg_liveroom,
            R.drawable.y_main_tb_bg_mine
    };

    public TabFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context.getApplicationContext();
        mTitleString = mContext.getResources().getStringArray(R.array.tab_main_title);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment ft = null;
        switch (position) {
            case TAB_ID_MSG:
                ft = new AddressBookFragment();
                break;
            case TAB_ID_DIALOGUE:
                ft = new LetterDemoFragment();
                break;
            case TAB_ID_LIVEROOM:
                ft = new LiveRoomFragment();
                break;
            case TAB_ID_MINE:
                ft = new MeFragment();
                break;
        }
        return ft;
    }

    @Override
    public int getCount() {
        return mTitleString.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleString[position];
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    public View getTabView(int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.y_custom_tab, null);
        TextView tv = v.findViewById(R.id.tv_tab);
        tv.setText(mTitleString[position]);
        ImageView img = v.findViewById(R.id.img_tab);
        img.setImageResource(imageResId[position]);
        return v;
    }
}
