package com.fh.shop.conmmons;

import javax.servlet.http.HttpServletRequest;

public class WebContext {

    private static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request){
        threadLocal.set(request);
    }
    public static HttpServletRequest getRequeest(){
        return threadLocal.get();
    }
    public static  void  remove(){
        threadLocal.remove();
    }

}
