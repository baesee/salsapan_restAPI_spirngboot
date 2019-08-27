package com.billlog.rest.salsapan.controller;

import java.util.List;
import java.util.Map;

/**
 * 오브젝트 널값 체크
 */
public class CustomUtils {
    public static boolean isEmpty(Object obj) { //Null 이면 True 를 리턴한다.
        if (obj == null) { return true; }
        if ((obj instanceof String) && (((String)obj).trim().length() == 0)) { return true; }
        if (obj instanceof Map) { return ((Map<?, ?>)obj).isEmpty(); }
        if (obj instanceof List) { return ((List<?>)obj).isEmpty(); }
        if (obj instanceof Object[]) { return (((Object[])obj).length == 0); }

        return false;
    }
}
