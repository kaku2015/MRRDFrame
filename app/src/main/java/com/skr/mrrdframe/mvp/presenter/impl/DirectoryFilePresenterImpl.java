package com.skr.mrrdframe.mvp.presenter.impl;

import android.content.Context;
import android.os.Environment;

import com.skr.mrrdframe.base.BasePresenterImpl;
import com.skr.mrrdframe.di.scope.ContextLife;
import com.skr.mrrdframe.mvp.entity.DirectoryFile;
import com.skr.mrrdframe.mvp.presenter.IDirectoryFilePresenter;
import com.skr.mrrdframe.mvp.ui.view.IDirectoryFileView;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author hyw
 * @since 2016/11/30
 */
public class DirectoryFilePresenterImpl extends BasePresenterImpl<IDirectoryFileView, List<DirectoryFile>>
        implements IDirectoryFilePresenter {
    private static final String LOG_TAG = "DirectoryFilePresenterImpl";

    @Inject
    @ContextLife()
    Context mAppContext;

    @Inject
    DirectoryFilePresenterImpl() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView.setData(getStorageList());
    }

    private List<DirectoryFile> getStorageList() {
        List<DirectoryFile> list = new ArrayList<>();
        String sDStateString = Environment.getExternalStorageState();
        if (sDStateString.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File SDFile = Environment.getExternalStorageDirectory();
                File sdPath = new File(SDFile.getAbsolutePath());
                File[] files = sdPath.listFiles();
                if (sdPath.listFiles().length > 0) {
                    for (File file : files) {
                        DirectoryFile directoryFile = new DirectoryFile();
                        directoryFile.setName(file.getName());
                        directoryFile.setPath(file.getAbsolutePath());
                        list.add(directoryFile);
                    }
                }
            } catch (Exception e) {
                KLog.e(LOG_TAG, "getStorageList() error : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public void load() {
        // do something
    }
}
