package com.skr.mrrdframe.repository.network;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author hyw
 * @version 1.0 2016/11/15
 */
public class HostType {

    public static final int TYPE_COUNT = 2;

    public static final int RELEASE = 1;

    public static final int TEST = 2;

    @IntDef({RELEASE, TEST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Checker {

    }
}
