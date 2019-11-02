package com.fh.shop.aspect;

import com.fh.shop.biz.log.IlogService;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.WebContext;
import com.fh.shop.po.log.LogInfo;
import com.fh.shop.po.user.User;
import com.fh.shop.util.SystemConst;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LogAspect {

    @Resource(name="logService")
    private IlogService logService;

    //日志记录
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    public Object  doLog(ProceedingJoinPoint  pjp){
        //获取方法名
        String name = pjp.getSignature().getName();
        //获取类名
        String canonicalName = pjp.getTarget().getClass().getCanonicalName();
        //获取用户信息
        HttpServletRequest requeest = WebContext.getRequeest();
        User user = (User) requeest.getSession().getAttribute(SystemConst.CURRENT_USER);
        String userName = user.getUserName();
        String realName = user.getRealName();
        //获取参数信息
        StringBuilder str = getStringBuilder(requeest);
        //获取自定义注解
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();

        Method method = methodSignature.getMethod();
        String value=null;
        if(method.isAnnotationPresent(Log.class)){
            Log annotation = method.getAnnotation(Log.class);
             value = annotation.value();
        }


        //实际要执行的方法
        Object proceed =null;
        try {
             proceed = pjp.proceed();
            LOGGER.info(userName+"执行了"+canonicalName+"中的"+name+"方法成功！！！");
            LogInfo logInfo = new LogInfo();
            logInfo.setStatus(SystemConst.LOG_SUCCESS);
            logInfo.setUserName(userName);
            logInfo.setRealName(realName);
            logInfo.setInfo("执行了"+canonicalName+"中的"+name+"方法成功！！！");
            logInfo.setErrorMsg("");
            logInfo.setCurrDate(new Date());
            logInfo.setDetail(str .toString());
            logInfo.setContent(value);
            logService.addLog(logInfo);
        } catch (Throwable throwable) {
            //throwable.printStackTrace();
            LogInfo logInfo = new LogInfo();
            logInfo.setStatus(SystemConst.LOG_ERROR);
            logInfo.setUserName(userName);
            logInfo.setRealName(realName);
            logInfo.setInfo("执行了"+canonicalName+"中的"+name+"方法失败！！！");
            logInfo.setErrorMsg(throwable.getMessage());
            logInfo.setCurrDate(new Date());
            logInfo.setDetail(str.toString());
            logInfo.setContent(value);
            logService.addLog(logInfo);
            LOGGER.error(userName+"执行了"+canonicalName+"中的"+name+"方法失败！！原因是"+throwable.getMessage());

            throw  new RuntimeException(throwable);
        }
        return proceed;
    }

    private StringBuilder getStringBuilder(HttpServletRequest requeest) {
        StringBuilder str = new StringBuilder();
        Map<String, String[]> parameterMap = requeest.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String[] value = entry.getValue();
            str.append("/").append(key).append("=").append(StringUtils.join(value,","));
        }
        return str;
    }


}
