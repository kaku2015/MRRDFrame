package com.skr.mrrdframe.repository.network.api;

import com.skr.mrrdframe.repository.network.HttpApi;
import com.skr.mrrdframe.repository.network.HttpApiBuilder;

import rx.Observable;

/**
 * @author hyw
 * @since 2016/12/6
 */
public class MobileApi extends BaseApi{

    public static HttpApi sHttpApi;
    public static Observable obserable;

    public static HttpApi getHttpApi() { //使用NetworkApiBuilder创建networkApi
        if(sHttpApi ==null ){
            sHttpApi = new HttpApiBuilder()
                    .addSession()               //添加sessionId
                    .addParameter()             //添加固定参数
                    .build();
        }
        return sHttpApi;
    }

    public static Observable getObserable(Observable observable) {
        obserable = new ObserableBuilder(observable)
                .addApiException()   //添加apiExcetion过滤
                .build();
        return obserable;
    }

/*    public static   Observable response(HashMap map, int protocolId) {
        RequestBody body = toBody(map);
        return getObserable(getHttpApi().response(protocolId, body));
    }*/
}
