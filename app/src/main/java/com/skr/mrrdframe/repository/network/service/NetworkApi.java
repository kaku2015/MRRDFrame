package com.skr.mrrdframe.repository.network.service;

import com.skr.mrrdframe.repository.network.entity.ResponseInfo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author hyw
 * @since 2016/12/5
 */
public interface NetworkApi {

    @POST("open/open.do")
    Observable<Object> post(@Query("ACID") int acid, @Body RequestBody entery);

    @POST("open/open.do")
    Observable<ResponseInfo<Object>> response(@Query("ACID") int acid, @Body RequestBody entery);
}
