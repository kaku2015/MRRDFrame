/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.skr.mrrdframe.repository.network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import com.skr.mrrdframe.listener.HttpDataListener;
import com.skr.mrrdframe.repository.network.subscriber.HttpSubscriber;
import com.skr.mrrdframe.repository.network.subscriber.ResultMap;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 咖枯
 * @since 2016/12/4
 */
public class HttpManager {
    @SuppressLint("StaticFieldLeak")
    private volatile static HttpManager sHttpManager;
    private Context mContext;
    private Observable mObservable;
    private HttpSubscriber mSubscriber;
    private boolean mIsShowProgressDialog = true;

    private HttpManager() {

    }

    public static HttpManager getInstance() {
        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager();
                }
            }
        }
        return sHttpManager;
    }

    public HttpManager with(Context context) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        mContext = weakReference.get();
        return sHttpManager;
    }

    public HttpManager setObservable(Observable observable) {
        this.mObservable = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ResultMap());
        return sHttpManager;
    }

    //是否显示ProgressDialog
    public HttpManager isShowProgressDialog(boolean isShowProgressDialog) {
        this.mIsShowProgressDialog = isShowProgressDialog;
        return sHttpManager;
    }

    //创建subscriber
    public void setDataListener(HttpDataListener listener) {
        mSubscriber = new HttpSubscriber(listener, mContext);
        mObservable.subscribe(mSubscriber);
    }

    //创建subscriber 自定义ProgressDialog的文字
    public void setDataListener(HttpDataListener listener, String message) {
        mObservable.subscribe(new HttpSubscriber(listener, mContext, message));
    }

    //创建subscriber 自定义ProgressDialog
    public void setDataListener(HttpDataListener listener, ProgressDialog dialog) {
        mObservable.subscribe(new HttpSubscriber(listener, mContext, dialog));
    }


    //取消请求
    public void cancelRequest() {
        mSubscriber.cancelRequest();
    }

}
