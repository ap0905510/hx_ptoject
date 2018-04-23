package com.yw.letterbar.letter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yw.letterbar.R;
import com.yw.letterbar.letter.IndexBar.widget.LetterBar;
import com.yw.letterbar.letter.IndexBar.widget.LetterBarBuilder;
import com.yw.letterbar.letter.decoration.DividerItemDecoration;
import com.yw.letterbar.letter.decoration.TitleItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class LetterDemoActivity extends Activity {
    private static final String TAG = "YW";
    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;

    private TitleItemDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private LetterBar mLetterBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letter_activity_main);

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        //initDatas();
        initDatas(getResources().getStringArray(R.array.provinces));
        //mDatas = new ArrayList<>();//测试为空或者null的情况 已经通过 2016 09 08

        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.addItemDecoration(mDecoration = new TitleItemDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(LetterDemoActivity.this, DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mLetterBar = (LetterBar) findViewById(R.id.indexBar);//IndexBar

        LetterBarBuilder.Builder builder = new LetterBarBuilder.Builder().build()
                .setPressedShowTextView(mTvSideBarHint)
                .setNeedRealIndex(true)
                .setLayoutManager(mManager)
                .setSourceData(mDatas);
        mLetterBar.setIndexParam(builder);
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(String[] data) {
        mDatas = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(data[i]);//设置城市名称
            mDatas.add(cityBean);
        }
    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas(View view) {
        for (int i = 0; i < 999; i++) {
            mDatas.add(new CityBean("东京"));
            mDatas.add(new CityBean("泰山"));
        }
        mAdapter.notifyDataSetChanged();
        mLetterBar.setSourceData(mDatas);
    }

}
