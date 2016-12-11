package com.skr.mrrdframe.repository.network.exception;

/**
 * @author hyw
 * @since 2016/12/5
 */
public class ApiException extends Exception {
    int mCode;

    public ApiException(int code, String message) {
        super(message);
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}