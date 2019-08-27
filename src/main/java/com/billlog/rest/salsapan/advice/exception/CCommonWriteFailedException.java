package com.billlog.rest.salsapan.advice.exception;

public class CCommonWriteFailedException extends RuntimeException{
    public CCommonWriteFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CCommonWriteFailedException(String msg) {
        super(msg);
    }

    public CCommonWriteFailedException() {
        super();
    }
}
