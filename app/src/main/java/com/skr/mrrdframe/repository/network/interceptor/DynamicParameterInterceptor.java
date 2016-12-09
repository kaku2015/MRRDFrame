package com.skr.mrrdframe.repository.network.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author hyw
 * @since 2016/12/5
 */
public class DynamicParameterInterceptor implements Interceptor {

    private HashMap<String, String> map;

    public DynamicParameterInterceptor(HashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //get请求后面追加共同的参数
        HttpUrl.Builder bulider = request.url().newBuilder();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            bulider.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        request = request.newBuilder().url(bulider.build()).build();
        return chain.proceed(request);
    }
}
