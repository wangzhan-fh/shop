package com.fh.shop.api.util;

public class KeyUtil {

    public static String buildphoneKey(String readCookie) {
        return "phone:"+readCookie;
    }

    public static String buildmemberKey(String member,String uuid) {
        return "member:"+member+"||"+uuid;
    }

    public static String buildmemberIdKey(Long memberId) {
        return "member:"+memberId;
    }

    public static String buildpayLogKey(Long memberId) {
        return "member:"+memberId;
    }

}
