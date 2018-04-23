package com.youmai.asynctaskloaderdemo.adapter.adap;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youmai.asynctaskloaderdemo.R;

import java.util.List;

/**
 * 作者：create by YW
 * 日期：2017.08.28 15:02
 * 描述：
 */
public class AppRecyclerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private PackageManager mPm;
    private LruCache<String, Drawable> mIconCache;
    private List<ApplicationInfo> mListApps;

    public AppRecyclerAdapter(Context context, List<ApplicationInfo> list) {
        this.mContext = context;
        mPm = context.getPackageManager();
        mIconCache = new LruCache<>(80);
        this.mListApps = list;
    }

    public void updateListAndNotifyDataChanged(List<ApplicationInfo> list) {
        this.mListApps = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建Holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.app_list_item_layout, parent, false);
        return new AppHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ApplicationInfo applicationInfo = mListApps.get(position);
        Drawable logo = mIconCache.get(applicationInfo.packageName);

        if (logo == null) {
            logo = mPm.getApplicationIcon(applicationInfo);
            mIconCache.put(applicationInfo.packageName, logo);
        }

        ((AppHolder)holder).mIcon.setImageDrawable(logo);
        ((AppHolder)holder).mTitle.setText(mPm.getApplicationLabel(applicationInfo));

    }

    @Override
    public int getItemCount() {
        return mListApps == null ? 0 : mListApps.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class AppHolder extends RecyclerView.ViewHolder {
        private final ImageView mIcon;
        private final TextView mTitle;

        public AppHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setTag(this);
        }
    }

}
