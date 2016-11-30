package com.skr.mrrdframe.mvp.ui.view;

import com.skr.mrrdframe.base.BaseView;
import com.skr.mrrdframe.mvp.entity.DirectoryFile;

import java.util.List;

/**
 * @author hyw
 * @since 2016/11/28
 */
public interface IDirectoryFileView extends BaseView {
    void setData(List<DirectoryFile> data);
}
