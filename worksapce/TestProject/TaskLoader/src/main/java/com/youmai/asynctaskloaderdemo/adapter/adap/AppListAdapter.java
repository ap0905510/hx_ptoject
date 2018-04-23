package com.youmai.asynctaskloaderdemo.adapter.adap;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youmai.asynctaskloaderdemo.R;

import java.util.List;

/**
 * 作者：create by YW
 * 日期：2017.08.28 15:02
 * 描述：
 */
public class AppListAdapter extends BaseAdapter {

    private Context mContext;
    private PackageManager mPm;
    private LruCache<String, Drawable> mIconCache;
    private List<ApplicationInfo> mListApps;

    public AppListAdapter(Context context, List<ApplicationInfo> list) {
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
    public int getCount() {
        return mListApps == null ? 0 : mListApps.size();
    }

    @Override
    public Object getItem(int position) {
        return mListApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AppHolder appHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.app_list_item_layout, null, false);
            appHolder = new AppHolder(convertView);
        } else {
            appHolder = (AppHolder) convertView.getTag();
        }

        ApplicationInfo applicationInfo = (ApplicationInfo) getItem(position);
        Drawable logo = mIconCache.get(applicationInfo.packageName);

        if (logo == null) {
            logo = mPm.getApplicationIcon(applicationInfo);
            mIconCache.put(applicationInfo.packageName, logo);
        }

        appHolder.mIcon.setImageDrawable(logo);
        appHolder.mTitle.setText(mPm.getApplicationLabel(applicationInfo));

        return convertView;
    }

    private class AppHolder {

        private final ImageView mIcon;
        private final TextView mTitle;

        public AppHolder(View itemView) {
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setTag(this);
        }
    }

}
