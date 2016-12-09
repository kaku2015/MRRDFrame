package com.skr.mrrdframe.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.skr.mrrdframe.App;

/**
 * @author hyw
 * @since 2016/12/9
 */
public class NetUtil {

    /**
     * 检查当前网络是否可用
     *
     * @return 是否连接到网络
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {

                    return true;
                }
            }
        }
        return false;
    }

}
