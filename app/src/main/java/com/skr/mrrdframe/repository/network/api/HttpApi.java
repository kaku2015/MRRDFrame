package com.skr.mrrdframe.repository.network.api;

import com.skr.mrrdframe.repository.network.entity.Express;
import com.skr.mrrdframe.repository.network.entity.ResultInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author hyw
 * @since 2016/12/5
 */
public interface HttpApi {

    @GET("query")
    Observable<ResultInfo<List<Express>>> getExpress(
            @Query("type") String type,
            @Query("postid") String postid);
}
