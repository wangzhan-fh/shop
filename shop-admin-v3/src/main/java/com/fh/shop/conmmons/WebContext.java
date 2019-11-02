package com.fh.shop.conmmons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {

    private static ThreadLocal<HttpServletRequest> threadLocal=new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> responseThreadLocal=new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request){
        threadLocal.set(request);
    }
    public static void setResponse(HttpServletResponse response){
        responseThreadLocal.set(response);
    }

    public static HttpServletRequest getRequest(){
             return threadLocal.get();
    }

    public static HttpServletResponse getResponse(){
        return responseThreadLocal.get();
    }
    public static void remove(){
      threadLocal.remove();
        responseThreadLocal.remove();
    }


}
