package com.skr.mrrdframe.repository.network.subscriber;


import com.skr.mrrdframe.repository.network.entity.ResultInfo;
import com.skr.mrrdframe.repository.network.exception.ApiException;

import rx.Observable;
import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 * @author hyw
 * @since 2016/12/5
 */
public class ResultFlatMap<T> implements Func1<ResultInfo<T>, Observable<T>> {

    @Override
    public Observable<T> call(ResultInfo<T> resultInfo) {
        if (resultInfo.getCode() != 200) {  //如果code返回的不是200,则抛出ApiException异常，否则返回data数据
            return Observable.error(new ApiException(resultInfo.getCode(), resultInfo.getMessage()));
        }
        return Observable.just(resultInfo.getData());
    }
}
