package com.yw.mvp.presenter;

import android.util.Log;

import com.yw.mvp.base.BaseActivity;
import com.yw.mvp.base.BasePresenter;
import com.yw.mvp.constant.Constant;
import com.yw.mvp.iview.IAddressBookAtView;

/**
 * 作者：create by YW
 * 日期：2018.03.19 15:20
 * 描述：P - 通讯录
 */

public class AddressBookAtPresenter extends BasePresenter<IAddressBookAtView> {

    public AddressBookAtPresenter(BaseActivity context) {
        super(context);
    }

    /**
     * 监听别个生命周期状态
     */
    @Override
    public void onResume() {
        Log.e(Constant.GLOBAL_TAG, "AddressBookAtPresenter onResume()");
    }
}
