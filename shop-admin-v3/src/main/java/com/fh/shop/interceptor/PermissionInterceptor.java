package com.fh.shop.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.fh.shop.po.resource.Resource;
import com.fh.shop.util.DistributedSession;
import com.fh.shop.util.KeyUtil;
import com.fh.shop.util.RedisUtil;
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

      
       // List<ResourceVo> allUrl = (List<ResourceVo>) request.getSession().getAttribute(SystemConst.MENU_URL);
        String sessionId = DistributedSession.getSessionId(request, response);
        String s = RedisUtil.get(KeyUtil.buildMenuUrlKey(sessionId));
        List<Resource> allUrl = JSONObject.parseArray(s, Resource.class);


       // List<Resource> resourceList = (List<Resource>) request.getSession().getAttribute(SystemConst.All_MENU_URL);
        String sessionId1 = DistributedSession.getSessionId(request, response);
        String s1 = RedisUtil.get(KeyUtil.buildAllResourceKey(sessionId1));
        List<Resource> resourceList = JSONObject.parseArray(s1, Resource.class);

        String requestURI = request.getRequestURI();
        boolean idpermission=false;
        for (Resource resourceVo : allUrl) {
            if(StringUtils.isNotEmpty(resourceVo.getMenuUrl()) && requestURI.contains(resourceVo.getMenuUrl())){
                idpermission=true;
                break;
            }
        }if(!idpermission){
            return true;
        }
        boolean  permission =false;
        for (Resource resource : resourceList) {
            if(StringUtils.isNotEmpty(resource.getMenuUrl()) && requestURI.contains(resource.getMenuUrl())) {
                permission = true;
                break;
            }
        }
        if(!permission){
            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNotEmpty(header) && header.equals("XMLHttpRequest")){
                response.setContentType("application/json;charset=utf-8");
                Map map =new HashMap();
                map.put("code",-10000);
                map.put("msg","对不起，你没有权限操作");
                String jsonString = JSONObject.toJSONString(map);
                outStr(jsonString,response);
            }

            response.sendRedirect("/error.jsp");
        }
        return permission;
    }

    private void outStr(String json ,HttpServletResponse response){
        PrintWriter writer=null;
        try {
             writer = response.getWriter();
             writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != writer){
                writer.close();
            }
        }


    }

}
