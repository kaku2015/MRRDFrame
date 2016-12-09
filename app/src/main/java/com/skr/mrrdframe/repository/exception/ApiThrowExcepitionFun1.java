package com.skr.mrrdframe.repository.exception;


import com.skr.mrrdframe.repository.network.entity.ResponseInfo;

import rx.Observable;
import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 * @author hyw
 * @since 2016/12/5
 */
public class ApiThrowExcepitionFun1<T> implements Func1<ResponseInfo<T>, Observable<T>> {

    @Override
    public Observable<T> call(ResponseInfo<T> responseInfo) {
        if (responseInfo.getCode() != 200) {  //如果code返回的不是200,则抛出ApiException异常，否则返回data数据
            return Observable.error(new ApiException(responseInfo.getCode(), responseInfo.getMessage()));
        }
        return Observable.just(responseInfo.getData());
    }
}
