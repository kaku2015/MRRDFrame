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
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.skr.mrrdframe.R;
import com.skr.mrrdframe.repository.network.exception.ApiException;
import com.skr.mrrdframe.utils.ToastUtil;
import com.socks.library.KLog;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @author 咖枯
 * @since 2016/12/4
 */
public abstract class ApiSubscriber<T> extends Subscriber<T> {
    private static final String LOG_TAG = "ApiSubscriber";

    private Context mContext;

    public void setContext(Context mCtx) {
        this.mContext = mCtx;
    }

    private ProgressDialog mProgressDialog;

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.mProgressDialog = progressDialog;
    }

    private boolean mIsShowProgressDialog;

    public void setShowProgressDialog(boolean showProgressDialog) {
        mIsShowProgressDialog = showProgressDialog;
    }

    private boolean mIsDialogCancelable;

    public void setDialogCancelable(boolean dialogCancelable) {
        mIsDialogCancelable = dialogCancelable;
    }

    private String mDialogMessage;

    public void setDialogMessage(String dialogMessage) {
        mDialogMessage = dialogMessage;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsShowProgressDialog) {
            showProgressDialog();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            initProgressDialog();
        }
        mProgressDialog.show();
    }

    private void initProgressDialog() {
        if (mProgressDialog == null && mContext != null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mDialogMessage);
            mProgressDialog.setCancelable(mIsDialogCancelable);
        }
    }

    public void cancelRequest() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public void onCompleted() {
        dismissDialog();
    }

    private void dismissDialog() {
        if (mIsShowProgressDialog && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        KLog.e(LOG_TAG, e.getMessage());
        showError(e);
    }

    private void showError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (TextUtils.isEmpty(httpException.getMessage())) {
                ToastUtil.showShortToast(R.string.error_network);
            } else {
                ToastUtil.showShortToast(httpException.getMessage());
            }
        } else if (e instanceof ApiException) {//服务器返回的错误
            onResultError((ApiException) e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {//解析异常
            ToastUtil.showShortToast(R.string.error_parse);
        } else if (e instanceof UnknownHostException) {
            ToastUtil.showShortToast(R.string.error_server);
        } else if (e instanceof SocketTimeoutException) {
            ToastUtil.showShortToast(R.string.error_socket_timeout);

        } else if (e instanceof ConnectException) {
            ToastUtil.showShortToast(R.string.error_network_interrupt);
        } else {
            ToastUtil.showShortToast(e.getMessage());
        }
    }

    /**
     * 服务器返回的错误
     *
     * @param ex
     */
    protected void onResultError(ApiException ex) {
/*        switch (ex.getCode()){  //服务器返回code默认处理
            case 10021:
                ToastUtil.showShortToast(R.string.imi_login_input_mail_error);
                break;
            case 10431:
                ToastUtil.showShortToast(R.string.imi_const_tip_charge);
                break;
            default:
                String msg = ex.getMessage();
                if(TextUtils.isEmpty(msg)){
                    ToastUtil.showShortToast(R.string.imi_toast_common_net_error);
                }else {
                    ToastUtil.showShortToast(mContext, msg);
                }
        }*/

    }

}
