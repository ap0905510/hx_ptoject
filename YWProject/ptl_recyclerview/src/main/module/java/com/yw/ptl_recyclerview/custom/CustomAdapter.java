package com.yw.ptl_recyclerview.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yw.ptl_recyclerview.R;
import com.yw.ptl_recyclerview.SimpleAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：create by YW
 * 日期：2018.03.29 18:05
 * 描述：
 */

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    protected String TAG = CustomAdapter.class.getSimpleName();
    protected Context mContext;
    protected List<String> mDatas;

    public CustomAdapter(Context mContext, ArrayList data) {
        this.mContext = mContext;
        this.mDatas = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_linear_manager_item, parent, false);
        return new ImageViewHolder(mContext, view, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mDatas.get(position)).placeholder(R.mipmap.ic_launcher).into(holder.<ImageView>getView(R.id.iv_item));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class ImageViewHolder extends ViewHolder {
        ImageView iv_item;

        public ImageViewHolder(Context context, View itemView, ViewGroup parent) {
            super(context, itemView, parent);
            iv_item = getView(R.id.iv_item);
        }
    }

}
