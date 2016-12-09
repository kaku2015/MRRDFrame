//package com.skr.mrrdframe.repository.network.api;
//
//import com.skr.mrrdframe.repository.network.RtHttp;
//import com.skr.mrrdframe.repository.network.service.NetworkApi;
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
//    public static NetworkApi networkApi;
//    public static Observable observable;
//
//    public static NetworkApi getNetworkApi(String baseUrl, HashMap map) {
//        networkApi = new RtHttp.NetworkApiBuilder()
//                .setBaseUrl(baseUrl)
//                .addDynamicParameter(map)
////                .setConvertFactory(StringConverFactory.create())
//                .build();
//        return networkApi;
//    }
//
//    public static NetworkApi getRollerApi(HashMap map) {
//        return getNetworkApi(Web.getRollerUrl(), map);
//    }
//
//    public static NetworkApi getFruitApi(HashMap map) {
//        return getNetworkApi(Web.getFruitUrl(), map);
//    }
//
//    public static NetworkApi getWxApi(HashMap map) {
//        return getNetworkApi(Web.getWXUrl(), map);
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
//        NetworkApi networkApi = null;
//        if (type == ROLLER) {
//            networkApi = getRollerApi(map);
//        } else if (type == FRUIT) {
//            networkApi = getFruitApi(map);
//        } else if (type == WX) {
//            networkApi = getWxApi(map);
//        }
//        String[] str = action.split("/");
//        if (str.length == 1) {
//            observable = networkApi.webPost(str[0]);
//        } else if (str.length == 2) {
//            observable = networkApi.webPost(str[0], str[1]);
//        } else {
//            return null;
//        }
//        return getObserable(observable);
//    }
//}
