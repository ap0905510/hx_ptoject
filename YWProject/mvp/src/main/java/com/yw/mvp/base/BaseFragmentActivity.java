package com.yw.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * 作者：create by YW
 * 日期：2018.02.23 14:55
 * 描述：
 */

public abstract class BaseFragmentActivity<V, P extends BaseFragmentPresenter<V>> extends FragmentActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //P
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView((V) this);
        }

        //V
        setContentView(contentViewId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachedView();
        }
    }

    /**
     * 由子类创建并绑定对应的Presenter
     * @return
     */
    protected abstract P createPresenter();

    protected abstract int contentViewId();

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initListener() {

    }

}
