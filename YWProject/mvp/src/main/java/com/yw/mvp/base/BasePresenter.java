package com.yw.mvp.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 作者：create by YW
 * 日期：2018.01.03 15:11
 * 描述：P
 */
public abstract class BasePresenter<V> {

    private BaseActivity mContext;

    public BasePresenter(BaseActivity context) {
        mContext = context;
    }

    protected Reference<V> mViewRef;

    /**
     * @param view 绑定View(Activity)
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 在Base的onResume调 并回调到Presenter处理
     */
    public abstract void onResume();

    /**
     * 解绑View
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * @return 获取绑定的View
     */
    public V getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }

}






