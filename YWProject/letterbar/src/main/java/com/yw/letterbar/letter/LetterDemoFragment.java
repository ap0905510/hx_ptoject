package com.yw.letterbar.letter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yw.letterbar.R;
import com.yw.letterbar.letter.IndexBar.widget.LetterBar;
import com.yw.letterbar.letter.IndexBar.widget.LetterBarBuilder;
import com.yw.letterbar.letter.decoration.DividerItemDecoration;
import com.yw.letterbar.letter.decoration.TitleItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：create by YW
 * 日期：2018.03.20 17:16
 * 描述：
 */

public class LetterDemoFragment extends Fragment {

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

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.letter_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRv = (RecyclerView) view.findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(mContext));

        initDatas(getResources().getStringArray(R.array.provinces));
        //mDatas = new ArrayList<>();//测试为空或者null的情况 已经通过 2016 09 08

        mRv.setAdapter(mAdapter = new CityAdapter(mContext, mDatas));
        mRv.addItemDecoration(mDecoration = new TitleItemDecoration(mContext, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) view.findViewById(R.id.tvSideBarHint);//HintTextView
        mLetterBar = (LetterBar) view.findViewById(R.id.indexBar);//IndexBar

        LetterBarBuilder.Builder builder = new LetterBarBuilder.Builder().build()
                .setPressedShowTextView(mTvSideBarHint)
                .setNeedRealIndex(true)
                .setLayoutManager(mManager)
                .setSourceData(mDatas);
        mLetterBar.setIndexParam(builder);

        view.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatas(null);
            }
        });
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

        for (int i = 0; i < 4; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity("↑");
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
