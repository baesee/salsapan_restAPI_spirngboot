package com.billlog.rest.salsapan.advice.exception;

public class CFileDownloadException extends RuntimeException {

    public CFileDownloadException(String msg, Throwable t) {
        super(msg, t);
    }

    public CFileDownloadException(String msg) {
        super(msg);
    }

    public CFileDownloadException() {
        super();
    }
}
