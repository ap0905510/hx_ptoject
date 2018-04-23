package com.yw.mvp.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 作者：create by YW
 * 日期：2018.02.23 14:57
 * 描述：对应着BaseFragmentActivity
 */

public class BaseFragmentPresenter<V> {
    private BaseFragmentActivity mContext;

    public BaseFragmentPresenter(BaseFragmentActivity context) {
        this.mContext = context;
    }

    private Reference<V> mViewRef;

    /**
     * 绑定Activity -- Base
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * @return true : 已绑定View
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解绑并清空
     */
    public void detachedView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 获取已绑定的Activity或者Fragment实例
     * @return
     */
    public V getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }

}
