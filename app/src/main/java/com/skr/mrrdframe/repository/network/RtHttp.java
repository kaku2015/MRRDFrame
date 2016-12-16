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

import android.content.Context;

import com.skr.mrrdframe.App;
import com.skr.mrrdframe.R;
import com.skr.mrrdframe.repository.network.subscriber.ApiSubscriber;
import com.skr.mrrdframe.repository.network.subscriber.ResultMap;
import com.skr.mrrdframe.utils.TransformUtils;

import java.lang.ref.WeakReference;

import rx.Observable;

/**
 * @author 咖枯
 * @since 2016/12/4
 */
public class RtHttp {
    private volatile static RtHttp sRtHttp;
    private Context mContext;
    private Observable mObservable;
    private ApiSubscriber mSubscriber;
    private boolean mIsShowProgressDialog = true;

    private boolean mIsDialogCancelable = false;

    private String mDialogMessage;

    public void setTransformer(Observable.Transformer transformer) {
        mTransformer = transformer;
    }

    private Observable.Transformer mTransformer;

    private RtHttp() {
        init();
    }

    private void init() {
        mDialogMessage = App.getAppContext().getString(R.string.loading);
        mTransformer = TransformUtils.defaultSchedulers();
    }

    public static RtHttp getInstance() {
        if (sRtHttp == null) {
            synchronized (RtHttp.class) {
                if (sRtHttp == null) {
                    sRtHttp = new RtHttp();
                }
            }
        }
        return sRtHttp;
    }

    public RtHttp with(Context context) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        mContext = weakReference.get();
        return sRtHttp;
    }

    public RtHttp isShowProgressDialog(boolean isShowProgressDialog) {
        mIsShowProgressDialog = isShowProgressDialog;
        return sRtHttp;
    }

    public RtHttp setObservable(Observable observable) {
//        mObservable = observable;
        mObservable = observable
                .compose(mTransformer)
                .map(new ResultMap());
        return sRtHttp;
    }

    public RtHttp setDialogMessage(String dialogMessage) {
        mDialogMessage = dialogMessage;
        return sRtHttp;
    }

    public RtHttp setDialogCancelable(boolean dialogCancelable) {
        mIsDialogCancelable = dialogCancelable;
        return sRtHttp;
    }

    public RtHttp subscriber(ApiSubscriber subscriber) {
        mSubscriber = subscriber;
        initSubscriber(subscriber);
        mObservable.subscribe(subscriber);
        return sRtHttp;
    }

    private void initSubscriber(ApiSubscriber subscriber) {
        //给subscriber设置Context，用于显示网络加载动画
        subscriber.setContext(mContext);
        subscriber.setShowProgressDialog(mIsShowProgressDialog);
        subscriber.setDialogCancelable(mIsDialogCancelable);
        subscriber.setDialogMessage(mDialogMessage);
    }

    public void cancelRequest() {
        mSubscriber.cancelRequest();
    }

}
