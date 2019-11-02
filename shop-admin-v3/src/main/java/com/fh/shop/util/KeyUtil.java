package com.fh.shop.util;

public class KeyUtil {

    public static String buildUserKey(String readCookie) {
        return "user:"+readCookie;
    }

    public static String buildCodeKey(String readCookie) {
        return "code:"+readCookie;
    }

    public static String buildMenuListKey(String readCookie) {
        return "menuList:"+readCookie;
    }

    public static String buildAllResourceKey(String readCookie) {
        return "allResource:"+readCookie;
    }

    public static String buildMenuUrlKey(String readCookie) {
        return "menuUrl:"+readCookie;
    }
}
