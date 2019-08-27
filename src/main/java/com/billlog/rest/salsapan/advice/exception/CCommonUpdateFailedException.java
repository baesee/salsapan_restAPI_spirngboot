package com.billlog.rest.salsapan.advice.exception;

public class CCommonUpdateFailedException extends RuntimeException{
    public CCommonUpdateFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CCommonUpdateFailedException(String msg) {
        super(msg);
    }

    public CCommonUpdateFailedException() {
        super();
    }
}
