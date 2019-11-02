package com.fh.shop.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DistributedSession {

    public static String getSessionId(HttpServletRequest request, HttpServletResponse response) {
        String readCookie = CookieUtil.readCookie(SystemConst.SESSIONID, request);
        if(StringUtils.isEmpty(readCookie)){
            //如果等于null返回一个sessionId
            readCookie= UUID.randomUUID().toString();
            CookieUtil.writeCookie(SystemConst.SESSIONID,readCookie,SystemConst.DOMAIN,response);
        }
        return readCookie;
    }
}
