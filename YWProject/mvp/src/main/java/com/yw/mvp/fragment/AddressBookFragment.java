package com.yw.mvp.fragment;

import android.view.View;
import android.widget.TextView;

import com.yw.mvp.MainActivity;
import com.yw.mvp.R;
import com.yw.mvp.base.BaseFragment;
import com.yw.mvp.iview.IAddressBookAtView;
import com.yw.mvp.presenter.AddressBookAtPresenter;
import com.yw.ui.letter.LetterIndexView;

/**
 * 作者：create by YW
 * 日期：2018.03.19 15:09
 * 描述：Main - 通讯录
 */

public class AddressBookFragment extends BaseFragment<IAddressBookAtView, AddressBookAtPresenter> implements IAddressBookAtView {

    LetterIndexView mLetterIndexView;
    TextView mTitle;

    @Override
    protected AddressBookAtPresenter createPresenter() {
        return new AddressBookAtPresenter((MainActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.y_fragment_main_addrbook;
    }

    @Override
    protected void initView(View rootView) {
        mLetterIndexView = rootView.findViewById(R.id.liv_index_view);
        mTitle = rootView.findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
        mTitle.setText("设置后的通讯录");
    }
}
