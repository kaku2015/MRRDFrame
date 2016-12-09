package com.skr.mrrdframe.repository.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author hyw
 * @since 2016/12/5
 */
public class HeaderInterceptor implements Interceptor {
    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("sessionId", "" /*CommonData.getUserInfo(context).sessionId*/); //添加sessionId
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
