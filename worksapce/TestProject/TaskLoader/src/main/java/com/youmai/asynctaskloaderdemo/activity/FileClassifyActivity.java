package com.youmai.asynctaskloaderdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.youmai.asynctaskloaderdemo.FilePickerConst;
import com.youmai.asynctaskloaderdemo.PickerManager;
import com.youmai.asynctaskloaderdemo.PickerManagerListener;
import com.youmai.asynctaskloaderdemo.R;
import com.youmai.asynctaskloaderdemo.adapter.FileClassifyAdapter;
import com.youmai.asynctaskloaderdemo.bean.Document;

import java.util.ArrayList;

/**
 * 作者：create by YW
 * 日期：2017.08.29 18:04
 * 描述：
 */
public class FileClassifyActivity extends SdkHomeActivity implements View.OnClickListener, PickerManagerListener {

    private static final String TAG = "FileClassifyActivity";

    public static final String TXT_TITLE = "txt_title";
    public static final String TXT_LIST_DATA = "txt_list_data";

    //bundle 数据
    private String txt_title;
    ArrayList<Document> mListData;

    //view
    private TextView tv_file_type;
    private RecyclerView mRecyclerView;

    //adapter
    FileClassifyAdapter mAdapter;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.hx_activity_file_manager_classify);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        txt_title = getIntent().getStringExtra(TXT_TITLE);
        mListData = getIntent().getParcelableArrayListExtra(TXT_LIST_DATA);
        Log.e(TAG, "txt_title: " + txt_title + "\tmListData: " + mListData.size());
    }

    @Override
    public void initView() {
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.header_title_line).setVisibility(View.GONE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(txt_title);

        tv_file_type = (TextView) findViewById(R.id.tv_file_type);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    public void bindData() {
        tv_file_type.setText(txt_title);
        mAdapter = new FileClassifyAdapter(mContext, mListData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void bindClick() {
        PickerManager.getInstance().setPickerManagerListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_MEDIA_DETAIL:
                if (resultCode == Activity.RESULT_OK) {
                    returnData(PickerManager.getInstance().getSelectedFiles());
                }
                break;
        }
    }

    private void returnData(ArrayList<String> paths) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS, paths);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemSelected(int currentCount) {
        // undo nothing
    }

    @Override
    public void onSingleItemSelected(ArrayList<String> paths) {
        //选择发送的文件
        Log.e(TAG, "select file ready to send: " + paths.toString());
        returnData(paths);
    }
}
