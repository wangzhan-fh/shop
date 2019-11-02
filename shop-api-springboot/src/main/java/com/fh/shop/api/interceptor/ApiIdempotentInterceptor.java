package com.fh.shop.api.interceptor;

import com.fh.shop.api.annontion.ApiIdempotent;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
public class ApiIdempotentInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取自定义注解
       HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(!method.isAnnotationPresent(ApiIdempotent.class)){
            return true;
        }
        //获取头信息
        String header = request.getHeader("x_token");
        if(StringUtils.isEmpty(header)){
            throw  new GlobalException(ResponceEnum.HEADER_IS_NULL);
        }
        //验证
        boolean exists = RedisUtil.exists(header);
        if(!exists){
            throw  new GlobalException(ResponceEnum.HEADER_IS_NULL);
        }

        //判断
        Long del = RedisUtil.del(header);
        if(del==0){
            throw  new GlobalException(ResponceEnum.TOKEN_IS_CF);
        }

        return true;
    }


}
