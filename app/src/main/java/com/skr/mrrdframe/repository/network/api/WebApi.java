//package com.skr.mrrdframe.repository.network.api;
//
//import com.skr.mrrdframe.repository.network.RtHttp;
//import com.skr.mrrdframe.repository.network.HttpApi;
//
//import java.util.HashMap;
//
//import rx.Observable;
//
///**
// * @author hyw
// * @since 2016/12/6
// */
//public class WebApi extends BaseApi {
//
//    public static final int ROLLER = 1;
//    public static final int FRUIT = 2;
//    public static final int WX = 3;
//    public static HttpApi sHttpApi;
//    public static Observable observable;
//
//    public static HttpApi getHttpApi(String baseUrl, HashMap map) {
//        sHttpApi = new RtHttp.NetworkApiBuilder()
//                .setBaseUrl(baseUrl)
//                .addDynamicParameter(map)
////                .setConvertFactory(StringConverFactory.create())
//                .build();
//        return sHttpApi;
//    }
//
//    public static HttpApi getRollerApi(HashMap map) {
//        return getHttpApi(Web.getRollerUrl(), map);
//    }
//
//    public static HttpApi getFruitApi(HashMap map) {
//        return getHttpApi(Web.getFruitUrl(), map);
//    }
//
//    public static HttpApi getWxApi(HashMap map) {
//        return getHttpApi(Web.getWXUrl(), map);
//    }
//
//    public static Observable getObserable(Observable observable) {
//        observable = new ObserableBuilder(observable)
//                .isWeb()
//                .build();
//        return observable;
//    }
//
//    public static Observable webPost(HashMap map, String action, int type) {
//        HttpApi sHttpApi = null;
//        if (type == ROLLER) {
//            sHttpApi = getRollerApi(map);
//        } else if (type == FRUIT) {
//            sHttpApi = getFruitApi(map);
//        } else if (type == WX) {
//            sHttpApi = getWxApi(map);
//        }
//        String[] str = action.split("/");
//        if (str.length == 1) {
//            observable = sHttpApi.webPost(str[0]);
//        } else if (str.length == 2) {
//            observable = sHttpApi.webPost(str[0], str[1]);
//        } else {
//            return null;
//        }
//        return getObserable(observable);
//    }
//}
