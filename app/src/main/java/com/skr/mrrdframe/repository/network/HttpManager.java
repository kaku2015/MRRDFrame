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
    private volatile static HttpManager singleton;
    private WeakReference<Context> context;
    private Observable observable;
    private HttpSubscriber subscriber;
    private boolean showProgress = true;

    private HttpManager() {

    }

    public static HttpManager getInstance() {
        if (singleton == null) {
            synchronized (HttpManager.class) {
                if (singleton == null) {
                    singleton = new HttpManager();
                }
            }
        }
        return singleton;
    }

    public HttpManager with(Context context) {
        this.context = new WeakReference<>(context);
        return singleton;
    }

    public HttpManager setObservable(Observable observable) {
        this.observable = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ResultMap());
        return singleton;
    }

    //是否显示ProgressDialog
    public HttpManager showProgress(boolean showProgress) {
        this.showProgress = showProgress;
        return singleton;
    }

    //创建subscriber
    public void setDataListener(HttpDataListener listener) {
        subscriber = new HttpSubscriber(listener, context.get());
        observable.subscribe(subscriber);
    }

    //创建subscriber 自定义ProgressDialog的文字
    public void setDataListener(HttpDataListener listener, String message) {
        observable.subscribe(new HttpSubscriber(listener, context.get(), message));
    }

    //创建subscriber 自定义ProgressDialog
    public void setDataListener(HttpDataListener listener, ProgressDialog dialog) {
        observable.subscribe(new HttpSubscriber(listener, context.get(), dialog));
    }


    //取消请求
    public void cancelRequest() {
        subscriber.cancelRequest();
    }

}
