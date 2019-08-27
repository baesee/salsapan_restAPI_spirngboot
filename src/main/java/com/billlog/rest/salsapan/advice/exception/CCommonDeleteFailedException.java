package com.billlog.rest.salsapan.advice.exception;

public class CCommonDeleteFailedException extends RuntimeException {
    public CCommonDeleteFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CCommonDeleteFailedException(String msg) {
        super(msg);
    }

    public CCommonDeleteFailedException() {
        super();
    }
}
