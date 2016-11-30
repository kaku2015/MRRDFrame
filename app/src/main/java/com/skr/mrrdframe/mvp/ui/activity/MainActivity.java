package com.skr.mrrdframe.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skr.mrrdframe.R;
import com.skr.mrrdframe.base.BaseActivity;
import com.skr.mrrdframe.mvp.entity.DirectoryFile;
import com.skr.mrrdframe.mvp.presenter.impl.DirectoryFilePresenterImpl;
import com.skr.mrrdframe.mvp.ui.adapter.DirectoryListAdapter;
import com.skr.mrrdframe.mvp.ui.view.IDirectoryFileView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements IDirectoryFileView {
    public static final String LOG_TAG = "MainActivity";

    @BindView(R.id.directory_list_rv)
    RecyclerView mDirectoryListRv;
    @BindView(R.id.fab)
    FloatingActionButton mFabBtn;

    @Inject
    DirectoryFilePresenterImpl mIDirectoryFilePresenter;
    @Inject
    DirectoryListAdapter mDirectoryListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initPresenter() {
        mPresenter = mIDirectoryFilePresenter;
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        mFabBtn.setOnClickListener(view -> Snackbar.make(view, "hello MRRDFrame!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        initRecycleView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecycleView() {
        mDirectoryListRv.setHasFixedSize(true);
        mDirectoryListRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mDirectoryListRv.setAdapter(mDirectoryListAdapter);
        mDirectoryListRv.setNestedScrollingEnabled(false);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void setData(List<DirectoryFile> data) {
        mDirectoryListAdapter.setData(data);
    }
}
