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
package com.skr.mrrdframe.repository.network.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.skr.mrrdframe.listener.HttpDataListener;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * @author 咖枯
 * @since 2016/12/4
 */
public class HttpSubscriber<T> extends Subscriber<T> {

    private HttpDataListener mSubscriberOnNextListener;
    private WeakReference<Context> context;
    private ProgressDialog dialog;

    public HttpSubscriber(HttpDataListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = new WeakReference<>(context);
        initProgressDialog();
    }

    //自定义ProgressDialog提示文字
    public HttpSubscriber(HttpDataListener mSubscriberOnNextListener, Context context, String message) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = new WeakReference<>(context);
        initProgressDialog(message);
    }

    //自定义ProgressDialog
    public HttpSubscriber(HttpDataListener mSubscriberOnNextListener, Context context, ProgressDialog dialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = new WeakReference<>(context);
        this.dialog = dialog;
    }

    private void initProgressDialog() {
        Context context = this.context.get();
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("加载中……");
            dialog.setCancelable(false);
        }
    }

    private void initProgressDialog(String message) {
        Context context = this.context.get();
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
    }

    private void showProgressDialog() {
        Context context = this.context.get();
        if (dialog == null || context == null) return;
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    //取消请求
    public void cancelRequest(){
        if (!isUnsubscribed()){
            unsubscribe();
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Context context = this.context.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("http", "error----------->" + e.toString());
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

}
