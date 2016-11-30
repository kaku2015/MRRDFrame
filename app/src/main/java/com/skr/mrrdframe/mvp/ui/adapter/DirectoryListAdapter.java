package com.skr.mrrdframe.mvp.ui.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skr.mrrdframe.R;
import com.skr.mrrdframe.base.BaseRecyclerViewAdapter;
import com.skr.mrrdframe.mvp.entity.DirectoryFile;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hyw
 * @since 2016/11/18
 */
public class DirectoryListAdapter extends BaseRecyclerViewAdapter<DirectoryFile> {
    private static final String LOG_TAG = "DirectoryListAdapter";

    @Inject
    Activity mContext;

    @Inject
    DirectoryListAdapter() {
        super(null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_directory_file, parent, false);
        return new DirectoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        DirectoryFile item = mData.get(position);

        DirectoryListViewHolder viewHolder = (DirectoryListViewHolder) holder;
        viewHolder.mFileNameTv.setText(item.getName());
    }

    class DirectoryListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.file_name_tv)
        TextView mFileNameTv;

        DirectoryListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
