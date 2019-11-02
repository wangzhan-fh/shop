package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //设置跨域问题
        response.setHeader("Access-Control-Allow-Origin","*");
        //设置请求方式
        response.setHeader("Access-Control-Allow-Methods","DELETE,POST,PUT,GET");
        //设置请求头信息
        response.setHeader("Access-Control-Allow-Headers","x_auth,content-type,x_token");


        //处理options请求
        String method1 = request.getMethod();
        if(method1.equalsIgnoreCase("options")){
            return false;
        }

        //白菜单
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method =handlerMethod.getMethod();
        if(!method.isAnnotationPresent(Check.class)){
            return true;
        }

        //获取头信息
        String header = request.getHeader("x_auth");
        //判断是否有头信息
        if(StringUtils.isEmpty(header)){
            throw new GlobalException(ResponceEnum.HEADER_IS_NULL);
        }
        //分割头信息
        String[] split = header.split("\\.");
        if(split.length!=2){
            throw new GlobalException(ResponceEnum.HEADER_IS_MISS);
        }
        //判断内容是被篡改
        String memberJsonBase64 = split[0];
        String signBase64 = split[1];
        String newsignMember = Md5Util.signMember(memberJsonBase64, SystemConst.MEMBER_SIGN);
        String newsignMemberBase64 = Base64.getEncoder().encodeToString(newsignMember.getBytes());
        if(!signBase64.equals(newsignMemberBase64)){
            throw new GlobalException(ResponceEnum.CONTEXT_IS_CHANGE);
        }
        //判断是否超时
        String memberjson = new String(Base64.getDecoder().decode(memberJsonBase64.getBytes()));
        MemberVo memberVo = JSONObject.parseObject(memberjson, MemberVo.class);
        String memberName = memberVo.getMemberName();
        String uuid = memberVo.getUuid();
        boolean exists = RedisUtil.exists(KeyUtil.buildmemberKey(memberName, uuid));
        if(!exists){
            throw new GlobalException(ResponceEnum.LOGIN_ITIME_OUT);
        }
        //续命
        RedisUtil.exPire(KeyUtil.buildmemberKey(memberName, uuid),SystemConst.MEMBER_EXPIRE);

        //用户信息放到request作用域
        request.setAttribute(SystemConst.MEMBER_Info,memberVo);
        return true;
    }
}
