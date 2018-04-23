package com.youmai.asynctaskloaderdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.youmai.asynctaskloaderdemo.PickerManager;
import com.youmai.asynctaskloaderdemo.R;
import com.youmai.asynctaskloaderdemo.bean.Document;
import com.youmai.asynctaskloaderdemo.loader.FileListLoader;

import java.util.ArrayList;
import java.util.List;

public class FileManagerActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<List<Document>> {

    private ArrayList<Document> mListLoaderData;
    private ArrayList<Document> mQQListData, mWeiXinListData;
    ProgressDialog progressDialog = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FileClassifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_jump_txt:
                intent.putExtra(FileClassifyActivity.TXT_TITLE, "文档");
                intent.putParcelableArrayListExtra(FileClassifyActivity.TXT_LIST_DATA,  PickerManager.getInstance().getTxtList());
                startActivity(intent);
                break;
            case R.id.tv_jump_video:
                intent.putExtra(FileClassifyActivity.TXT_TITLE, "视频");
                intent.putParcelableArrayListExtra(FileClassifyActivity.TXT_LIST_DATA,  PickerManager.getInstance().getVideoList());
                startActivity(intent);
                break;
            case R.id.tv_jump_music:
                intent.putExtra(FileClassifyActivity.TXT_TITLE, "音乐");
                intent.putParcelableArrayListExtra(FileClassifyActivity.TXT_LIST_DATA,  PickerManager.getInstance().getMusicList());
                startActivity(intent);
                break;
            case R.id.tv_jump_download:
                Intent it = new Intent(FileManagerActivity.this, FileDownloadActivity.class);
                it.putExtra(FileDownloadActivity.TITLE, "下载");
                it.putParcelableArrayListExtra(FileDownloadActivity.QQ_LIST_DATA,  mQQListData);
                it.putParcelableArrayListExtra(FileDownloadActivity.WEIXIN_LIST_DATA,  mWeiXinListData);
                startActivity(it);
                break;
            case R.id.tv_jump_app:
                intent.putExtra(FileClassifyActivity.TXT_TITLE, "应用");
                intent.putParcelableArrayListExtra(FileClassifyActivity.TXT_LIST_DATA,  PickerManager.getInstance().getAppList());
                startActivity(intent);
                break;
            case R.id.tv_jump_zip:
                intent.putExtra(FileClassifyActivity.TXT_TITLE, "压缩包");
                intent.putParcelableArrayListExtra(FileClassifyActivity.TXT_LIST_DATA,  PickerManager.getInstance().getZipList());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hx_activity_file_manager);

        PickerManager.getInstance().getTxtList().clear();
        PickerManager.getInstance().getVideoList().clear();
        PickerManager.getInstance().getMusicList().clear();
        PickerManager.getInstance().getAppList().clear();
        PickerManager.getInstance().getZipList().clear();

        initViews();
        bindClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoad();
        bindDataToView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("branch", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("branch", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("branch", "onDestroy");

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void startLoad() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        PickerManager.getInstance().addDocTypes();

        getSupportLoaderManager().initLoader(1, null, this);

    }

    @Override
    public Loader<List<Document>> onCreateLoader(int id, Bundle args) {

        //args 是getSupportLoaderManager().initLoader传过来的数据
        Log.e("branch", "onCreateLoader");

        return new FileListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Document>> loader, final List<Document> data) {

        Log.e("branch", "onLoadFinished-》 " + data.size());

        mListLoaderData = (ArrayList<Document>) data;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_txt_count.setText("(" + PickerManager.getInstance().getTxtList().size() + ")");
                tv_video_count.setText("(" + PickerManager.getInstance().getVideoList().size() + ")");
                tv_music_count.setText("(" + PickerManager.getInstance().getMusicList().size() + ")");
                tv_app_count.setText("(" + PickerManager.getInstance().getAppList().size() + ")");
                tv_zip_count.setText("(" + PickerManager.getInstance().getZipList().size() + ")");
            }
        }, 300);

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Document>> loader) {

        Log.e("branch", "onLoaderReset");
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    TextView tv_jump_txt, tv_txt_count;
    TextView tv_jump_video, tv_video_count;
    TextView tv_jump_music, tv_music_count;
    TextView tv_jump_download, tv_download_count;
    TextView tv_jump_app, tv_app_count;
    TextView tv_jump_zip, tv_zip_count;

    private void initViews() {

        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.header_title_line).setVisibility(View.GONE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("选择文件");

        tv_jump_txt = (TextView) findViewById(R.id.tv_jump_txt);
        tv_jump_video = (TextView) findViewById(R.id.tv_jump_video);
        tv_jump_music = (TextView) findViewById(R.id.tv_jump_music);
        tv_jump_download = (TextView) findViewById(R.id.tv_jump_download);
        tv_jump_app = (TextView) findViewById(R.id.tv_jump_app);
        tv_jump_zip = (TextView) findViewById(R.id.tv_jump_zip);

        tv_txt_count = (TextView) findViewById(R.id.tv_txt_count);
        tv_video_count = (TextView) findViewById(R.id.tv_video_count);
        tv_music_count = (TextView) findViewById(R.id.tv_music_count);
        tv_download_count = (TextView) findViewById(R.id.tv_download_count);
        tv_app_count = (TextView) findViewById(R.id.tv_app_count);
        tv_zip_count = (TextView) findViewById(R.id.tv_zip_count);

    }

    private void bindClick() {
        tv_jump_txt.setOnClickListener(this);
        tv_jump_video.setOnClickListener(this);
        tv_jump_music.setOnClickListener(this);
        tv_jump_download.setOnClickListener(this);
        tv_jump_app.setOnClickListener(this);
        tv_jump_zip.setOnClickListener(this);
    }

    private void bindDataToView() {
        mQQListData = PickerManager.getInstance().loadLocalQQFile();
        mWeiXinListData = PickerManager.getInstance().loadLocalWeiXinFile();
        tv_download_count.setText("(" + (mWeiXinListData.size() + mQQListData.size()) + ")");
    }

}
