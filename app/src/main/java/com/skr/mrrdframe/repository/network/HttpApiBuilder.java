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

import com.skr.mrrdframe.BuildConfig;
import com.skr.mrrdframe.repository.network.interceptor.DynamicParameterInterceptor;
import com.skr.mrrdframe.repository.network.interceptor.HeaderInterceptor;
import com.skr.mrrdframe.repository.network.interceptor.ParameterInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 咖枯
 * @since 2016/12/11
 */
public class HttpApiBuilder {
    private String baseUrl;  //根地址
    private boolean isAddSession; //是否添加sessionid
    private HashMap<String, String> addDynamicParameterMap; //url动态参数
    private boolean isAddParameter; //url是否添加固定参数
    private OkHttpClient.Builder mOkHttpBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private Converter.Factory convertFactory;

    public HttpApiBuilder setConvertFactory(Converter.Factory convertFactory) {
        this.convertFactory = convertFactory;
        return this;
    }

    public HttpApiBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public HttpApiBuilder addParameter() {
        isAddParameter = true;
        return this;
    }


    public HttpApiBuilder addSession() {
        isAddSession = true;
        return this;
    }

    public HttpApiBuilder addDynamicParameter(HashMap map) {
        addDynamicParameterMap = map;
        return this;
    }


    public HttpApi build() {
        mOkHttpBuilder = new OkHttpClient.Builder();

        mOkHttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        if (isAddSession) {
            mOkHttpBuilder.addInterceptor(new HeaderInterceptor());
        }
        if (isAddParameter) {
            mOkHttpBuilder.addInterceptor(new ParameterInterceptor());
        }
        if (addDynamicParameterMap != null) {
            mOkHttpBuilder.addInterceptor(new DynamicParameterInterceptor(addDynamicParameterMap));
        }
        //warning:must in the last intercepter to log the network;
        if (BuildConfig.DEBUG) { //改成自己的显示log判断逻辑
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpBuilder.addInterceptor(logging);
        }

        mRetrofitBuilder = new Retrofit.Builder();
        mRetrofitBuilder.baseUrl(baseUrl);
        if (convertFactory != null) {
            mRetrofitBuilder.addConverterFactory(convertFactory);
        } else {
            mRetrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        }
        mRetrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpBuilder.build());
        return mRetrofitBuilder.build().create(HttpApi.class);
    }
}
