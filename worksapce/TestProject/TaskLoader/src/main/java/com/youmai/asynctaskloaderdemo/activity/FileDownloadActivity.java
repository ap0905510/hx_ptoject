package com.youmai.asynctaskloaderdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youmai.asynctaskloaderdemo.R;
import com.youmai.asynctaskloaderdemo.bean.Document;
import com.youmai.asynctaskloaderdemo.view.FileDownloadItemView;

import java.util.ArrayList;

/**
 * 作者：create by YW
 * 日期：2017.08.30 13:57
 * 描述：
 */
public class FileDownloadActivity extends SdkHomeActivity implements View.OnClickListener {

    public static final String TITLE = "title";
    public static final String QQ_LIST_DATA = "qq_list_data";
    public static final String WEIXIN_LIST_DATA = "weixin_list_data";

    private ArrayList<Document> mQQListData, mWeiXinListData;
    private String mTitle;

    private FileDownloadItemView ll_item_qq, ll_item_weixin;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FileDLClassifyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_item_qq:
                intent.putExtra(FileDLClassifyActivity.TITLE, "下载内容");
                intent.putParcelableArrayListExtra(FileDLClassifyActivity.DOWNLOAD_LIST_DATA, mQQListData);
                startActivity(intent);
                break;
            case R.id.ll_item_weixin:
                intent.putExtra(FileDLClassifyActivity.TITLE, "微信");
                intent.putParcelableArrayListExtra(FileDLClassifyActivity.DOWNLOAD_LIST_DATA, mWeiXinListData);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.hx_activity_file_manager_download);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mTitle = getIntent().getStringExtra(TITLE);
        mQQListData = getIntent().getParcelableArrayListExtra(QQ_LIST_DATA);
        mWeiXinListData = getIntent().getParcelableArrayListExtra(WEIXIN_LIST_DATA);
    }

    @Override
    public void initView() {
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.header_title_line).setVisibility(View.GONE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(mTitle);

        ll_item_weixin = (FileDownloadItemView) findViewById(R.id.ll_item_weixin);
        ll_item_qq = (FileDownloadItemView) findViewById(R.id.ll_item_qq);
    }

    @Override
    public void bindClick() {
        ll_item_qq.setOnClickListener(this);
        ll_item_weixin.setOnClickListener(this);
    }

    @Override
    public void bindData() {
        if (mQQListData != null && mQQListData.size() > 0) {
            ll_item_qq.setCountText(mQQListData.size());
        }
        if (mWeiXinListData != null && mWeiXinListData.size() > 0) {
            ll_item_weixin.setCountText(mWeiXinListData.size());
        }
    }
}
