package com.fh.shop.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.po.resource.Resource;
import com.fh.shop.util.SystemConst;
import com.fh.shop.vo.resource.ResourceVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<Resource> menuUrl = (List<Resource>) request.getSession().getAttribute(SystemConst.USER_CURRENT_MENU);
        List<ResourceVo> allmenuUrl = (List<ResourceVo>) request.getSession().getAttribute(SystemConst.ALL_MENUURL);
        String requestURI = request.getRequestURI();

        //判断是否是公共资源
        boolean ispermission = false;
        for (ResourceVo resource : allmenuUrl) {
            if(StringUtils.isNotEmpty(resource.getMenuUrl()) && requestURI.contains(resource.getMenuUrl())){
                ispermission=true;
                break;
            }
        }
        //是 就放开
        if(!ispermission){
            return  true;
        }

        //判断用户是否拥有权限
        boolean haspermission = false;
        for (Resource resource : menuUrl) {
            if(StringUtils.isNotEmpty(resource.getMenuUrl()) && requestURI.contains(resource.getMenuUrl())) {
                haspermission = true;
                break;
            }
        }
        if(!haspermission){
           String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNotEmpty(header) && header.equals("XMLHttpRequest")){
                response.setContentType("application/json;charset=utf-8");
                Map result = new HashMap();
                result.put("code",-10000);
                result.put("msg","对不起,你没权限操作");
                String json = JSONObject.toJSONString(result);
                outStr(json,response);
            }else{
                response.sendRedirect("/error.jsp");
            }

        }

        return haspermission;

    }
    private void outStr(String json,HttpServletResponse response){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(null != writer){
                writer.close();
            }
        }


    }


}
