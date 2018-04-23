package com.yw.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者：create by YW
 * 日期：2018.01.03 15:06
 * 描述：V : view
 *      P : presenter
 *      M : model
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建绑定的Presenter
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

        // 绑定子类布局
        setContentView(provideContentViewId());

        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑View与Presenter
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initListener() {

    }

    /**
     * @return xml布局Id
     */
    protected abstract int provideContentViewId();

    /**
     * @return P : Activity绑定Presenter
     */
    public abstract P createPresenter();
}
