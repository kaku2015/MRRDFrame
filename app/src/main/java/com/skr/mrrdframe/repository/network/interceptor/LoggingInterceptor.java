package com.skr.mrrdframe.repository.network.interceptor;

import com.socks.library.KLog;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 打印返回的json数据拦截器
 *
 * @author hyw
 * @since 2016/12/9
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        request = requestBuilder.build();

        long t1 = System.nanoTime(); // 毫微秒
        final Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        String time = (t2 - t1) / 1e6d + " ms";

        KLog.i(getHeadInfo(request, response, time));

        final ResponseBody responseBody = response.body();
        final long contentLength = responseBody.contentLength();

        Buffer buffer = getBuffer(responseBody);
        Charset charset = getCharset(responseBody);

        if (contentLength != 0) {
            KLog.i("--------------------------------------------开始打印返回数据----------------------------------------------------");
            KLog.json(buffer.clone().readString(charset));
            KLog.i("--------------------------------------------结束打印返回数据----------------------------------------------------");
        }

        return response;
    }

    private Buffer getBuffer(ResponseBody responseBody) throws IOException {
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        return source.buffer();
    }

    private Charset getCharset(ResponseBody responseBody) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        return charset;
    }

    private String getHeadInfo(Request request, Response response, String time) {
//        String userId = request.url().queryParameter("userId"); //本项目log特定参数用户id
        return ("\nrequest type：" + request.method()) +
                "\nrequest address: " +
                request.url() +
                "\nrequest headers：" +
                request.headers() +
                "\nresponse headers：" +
                response.headers() +
                "\nreturn code: " +
                response.code() +
                "\nused time：" +
                time;
    }
}