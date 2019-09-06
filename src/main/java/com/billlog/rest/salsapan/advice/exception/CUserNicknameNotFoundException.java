package com.billlog.rest.salsapan.advice.exception;

public class CUserNicknameNotFoundException extends RuntimeException {
    public CUserNicknameNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    public CUserNicknameNotFoundException(String msg) {
        super(msg);
    }
    public CUserNicknameNotFoundException() {
        super();
    }
}