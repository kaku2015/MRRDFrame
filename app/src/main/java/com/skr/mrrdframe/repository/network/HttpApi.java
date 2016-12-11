package com.skr.mrrdframe.repository.network;

import com.skr.mrrdframe.repository.network.entity.Express;
import com.skr.mrrdframe.repository.network.entity.ResultInfo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("open/open.do")
    Observable<Object> post(@Query("ACID") int acid, @Body RequestBody entery);

}
