package com.skr.mrrdframe.repository.network;

import android.util.SparseArray;

import com.skr.mrrdframe.App;
import com.skr.mrrdframe.BuildConfig;
import com.skr.mrrdframe.repository.network.interceptor.LoggingInterceptor;
import com.skr.mrrdframe.repository.network.service.HttpService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hyw
 * @since 2016/12/9
 */
public class RetrofitManager2 {
    public static HttpService getService(int hostType) {
        return getInstance(hostType).mService;
    }

    private HttpService mService;

    private static volatile OkHttpClient sOkHttpClient;

    private static SparseArray<RetrofitManager2> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);
    private boolean mIsUseCache;

    /**
     * @param hostType
     */
    public static RetrofitManager2 getInstance(int hostType) {
        RetrofitManager2 retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager2(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }

    public RetrofitManager2(@HostType.Checker int hostType) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.getHost(hostType))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(HttpService.class);

    }

    private OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS);

                    //debug模式打印网络请求日志
                    if (BuildConfig.DEBUG) {
//                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(new LoggingInterceptor());
                    }

                    if (mIsUseCache) {
                        Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"),
                                1024 * 1024 * 100);
                        builder.cache(cache);
                    }

                    sOkHttpClient = builder.build();
                }
            }
        }
        return sOkHttpClient;
    }

}