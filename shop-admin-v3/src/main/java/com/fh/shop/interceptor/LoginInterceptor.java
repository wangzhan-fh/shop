package com.fh.shop.interceptor;


import com.fh.shop.util.DistributedSession;
import com.fh.shop.util.KeyUtil;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.util.SystemConst;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor  extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // Object attribute = request.getSession().getAttribute(SystemConst.CURRENT_USER);
        String sessionId = DistributedSession.getSessionId(request, response);
        String attribute = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        if(attribute!=null){

            //赋值
            RedisUtil.exPire(KeyUtil.buildMenuListKey(sessionId),SystemConst.TIME_REDIS);
            RedisUtil.exPire(KeyUtil.buildUserKey(sessionId),SystemConst.TIME_REDIS);
            RedisUtil.exPire(KeyUtil.buildAllResourceKey(sessionId),SystemConst.TIME_REDIS);
            RedisUtil.exPire(KeyUtil.buildMenuUrlKey(sessionId),SystemConst.TIME_REDIS);
                return true;
        }

            response.sendRedirect("/");
            return false;


    }
}
