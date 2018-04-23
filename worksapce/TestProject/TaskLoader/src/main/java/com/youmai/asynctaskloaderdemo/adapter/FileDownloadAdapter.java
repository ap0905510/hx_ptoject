package com.youmai.asynctaskloaderdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youmai.asynctaskloaderdemo.FilePickerConst;
import com.youmai.asynctaskloaderdemo.PickerManager;
import com.youmai.asynctaskloaderdemo.R;
import com.youmai.asynctaskloaderdemo.bean.Document;

import java.util.ArrayList;

/**
 * 作者：create by YW
 * 日期：2017.08.30 16:12
 * 描述：
 */
public class FileDownloadAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Document> mListApps;

    public FileDownloadAdapter(Context context, ArrayList<Document> list) {
        this.mContext = context;
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
        View view = inflater.inflate(R.layout.app_list_item_layout, parent, false);
        return new FileDownloadHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Document document =  mListApps.get(position);

        ((FileDownloadHolder)holder).mIcon.setImageResource(document.getFileType().drawable == 0 ? R.drawable.hx_icon_folder_txt : document.getFileType().drawable);
        ((FileDownloadHolder)holder).mTitle.setText(document.getTitle());

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

    private class FileDownloadHolder extends RecyclerView.ViewHolder {
        private final ImageView mIcon;
        private final TextView mTitle;

        public FileDownloadHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setTag(this);
        }
    }

}
