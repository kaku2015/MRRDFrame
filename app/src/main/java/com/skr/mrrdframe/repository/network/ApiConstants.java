package com.skr.mrrdframe.repository.network;

/**
 * @author hyw
 * @since 2016/11/22
 */
public class ApiConstants {
    public static final String HOST = "192.168.0.118";
    public static final int PORT = 7878;

    public static final String BASE = "http://www.kuaidi100.com/";
    public static final String TEST = "http://www.kuaidi100.com/Test/";

    /**
     * 获取base url
     *
     * @param hostType HostType.RELEASE,
     *                 HostType.TEST
     * @return
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.RELEASE:
                host = BASE;
                break;
            case HostType.TEST:
                host = TEST;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
