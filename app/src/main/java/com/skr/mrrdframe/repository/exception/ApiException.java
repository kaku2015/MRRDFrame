package com.skr.mrrdframe.repository.exception;

/**
 * @author hyw
 * @since 2016/12/5
 */
public class ApiException extends Exception {
    int code;
    public ApiException(int code,String s) {
        super(s);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}