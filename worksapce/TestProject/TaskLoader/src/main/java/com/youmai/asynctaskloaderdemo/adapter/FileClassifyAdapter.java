package com.youmai.asynctaskloaderdemo.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.youmai.asynctaskloaderdemo.FilePickerConst;
import com.youmai.asynctaskloaderdemo.PickerManager;
import com.youmai.asynctaskloaderdemo.R;
import com.youmai.asynctaskloaderdemo.bean.BaseFile;
import com.youmai.asynctaskloaderdemo.bean.Document;
import com.youmai.asynctaskloaderdemo.utils.AbDateUtil;
import com.youmai.asynctaskloaderdemo.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：create by YW
 * 日期：2017.08.28 15:02
 * 描述：
 */
public class FileClassifyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private PackageManager mPm;
    private LruCache<String, Drawable> mIconCache;
    private ArrayList<Document> mListApps;

    public FileClassifyAdapter(Context context, ArrayList<Document> list) {
        this.mContext = context;
        mPm = context.getPackageManager();
        mIconCache = new LruCache<>(80);
        this.mListApps = list;
    }

    public void updateListAndNotifyDataChanged(ArrayList<Document> list) {
        this.mListApps = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建Holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.hx_item_picker_file_layout, parent, false);
        return new FileManagerHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Document document =  mListApps.get(position);

        String path = document.getPath();
        int drawable = FileUtils.getTypeDrawable(document.getPath());
        ((FileManagerHolder)holder).mIvLogo.setImageResource(drawable);
        ((FileManagerHolder)holder).mTitle.setText(document.getTitle());
        ((FileManagerHolder)holder).mTvSize.setText(Formatter.formatShortFileSize(mContext, Long.parseLong(document.getSize())));
        ((FileManagerHolder)holder).mTvTime.setText(AbDateUtil.getStringByFormat(document.getModifyDate(), "yyyy-MM-dd HH:mm"));

        if (path.toLowerCase().endsWith(".mp4") || path.toLowerCase().endsWith(".rmvb")
                || path.toLowerCase().endsWith(".avi") || path.toLowerCase().endsWith(".3gp")) {

            Glide.with(mContext)
                    .load(Uri.fromFile(new File(document.getPath())))
                    .error(R.drawable.hx_icon_rd)
                    .into(((FileManagerHolder) holder).mIvLogo);

        } else if (path.toLowerCase().endsWith(".apk")) {
            Log.e("Yw", "path: " + path);
            try {
                if (path.toLowerCase().startsWith("huxin")) {
                    ((FileManagerHolder) holder).mIvLogo.setImageResource(R.drawable.img_apk);
                } else {
                    PackageManager pm = mContext.getPackageManager();
                    PackageInfo pi = pm.getPackageArchiveInfo(path, 0);
                    String appName = pi.packageName;
                    Drawable applicationIcon = pm.getApplicationIcon(appName);
                    ((FileManagerHolder) holder).mIvLogo.setImageDrawable(applicationIcon);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click position " + position, Toast.LENGTH_LONG).show();
                PickerManager.getInstance().setMaxCount(1);
                PickerManager.getInstance().add(document.getPath(), FilePickerConst.FILE_TYPE_DOCUMENT);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListApps == null ? 0 : mListApps.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class FileManagerHolder extends RecyclerView.ViewHolder {
        private final ImageView mIvLogo;
        private final TextView mTitle;
        private final TextView mTvSize;
        private final TextView mTvTime;

        public FileManagerHolder(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            mTitle = (TextView) itemView.findViewById(R.id.file_name_tv);
            mTvTime = (TextView) itemView.findViewById(R.id.file_time_tv);
            mTvSize = (TextView) itemView.findViewById(R.id.file_size_tv);
            itemView.setTag(this);
        }
    }

}
