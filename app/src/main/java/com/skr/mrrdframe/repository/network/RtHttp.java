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

import com.skr.mrrdframe.BuildConfig;
import com.skr.mrrdframe.repository.network.interceptor.DynamicParameterInterceptor;
import com.skr.mrrdframe.repository.network.interceptor.HeaderInterceptor;
import com.skr.mrrdframe.repository.network.interceptor.ParameterInterceptor;
import com.skr.mrrdframe.repository.network.service.NetworkApi;
import com.skr.mrrdframe.repository.network.subscriber.ApiSubscriber;
import com.skr.mrrdframe.repository.network.subscriber.HttpSubscriber;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author 咖枯
 * @since 2016/12/4
 */
public class RtHttp {
    private volatile static RtHttp sRtHttp;
    private Context mContext;
    private Observable mObservable;
    private HttpSubscriber mSubscriber;
    private boolean mIsShowProgressDialog = true;

    private RtHttp() {

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
        this.mIsShowProgressDialog = isShowProgressDialog;
        return sRtHttp;
    }

    public RtHttp setObservable(Observable observable) {
        this.mObservable = observable;
        return sRtHttp;
    }

    public RtHttp subscriber(ApiSubscriber subscriber) {
        subscriber.setmCtx(mContext);  //给subscriber设置Context，用于显示网络加载动画
        subscriber.setShowWaitDialog(mIsShowProgressDialog); //控制是否显示动画
        mObservable.subscribe(subscriber); //RxJava 方法
        return sRtHttp;
    }

    //取消请求
    public void cancelRequest() {
        mSubscriber.cancelRequest();
    }

    /**
     * 使用Retrofit.Builder和OkHttpClient.Builder构建NetworkApi
     */
    public static class NetworkApiBuilder {
        private String baseUrl;  //根地址
        private boolean isAddSession; //是否添加sessionid
        private HashMap<String, String> addDynamicParameterMap; //url动态参数
        private boolean isAddParameter; //url是否添加固定参数
        private Retrofit.Builder rtBuilder;
        private OkHttpClient.Builder okBuild;
        private Converter.Factory convertFactory;

        public NetworkApiBuilder setConvertFactory(Converter.Factory convertFactory) {
            this.convertFactory = convertFactory;
            return this;
        }

        public NetworkApiBuilder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public NetworkApiBuilder addParameter() {
            isAddParameter = true;
            return this;
        }


        public NetworkApiBuilder addSession() {
            isAddSession = true;
            return this;
        }

        public NetworkApiBuilder addDynamicParameter(HashMap map) {
            addDynamicParameterMap = map;
            return this;
        }


        public NetworkApi build() {
            rtBuilder = new Retrofit.Builder();
            okBuild = new OkHttpClient().newBuilder();
            rtBuilder.baseUrl(baseUrl);

            if (isAddSession) {
                okBuild.addInterceptor(new HeaderInterceptor());
            }
            if (isAddParameter) {
                okBuild.addInterceptor(new ParameterInterceptor());
            }
            if (addDynamicParameterMap != null) {
                okBuild.addInterceptor(new DynamicParameterInterceptor(addDynamicParameterMap));
            }
            //warning:must in the last intercepter to log the network;
            if (BuildConfig.DEBUG) { //改成自己的显示log判断逻辑
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okBuild.addInterceptor(logging);
            }
            if (convertFactory != null) {
                rtBuilder.addConverterFactory(convertFactory);
            } else {
                rtBuilder.addConverterFactory(GsonConverterFactory.create());
            }
            rtBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okBuild.build());
            return rtBuilder.build().create(NetworkApi.class);
        }
    }

}
