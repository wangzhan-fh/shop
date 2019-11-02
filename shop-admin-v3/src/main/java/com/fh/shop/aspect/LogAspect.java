package com.fh.shop.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.biz.log.IlogService;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.conmmons.WebContext;
import com.fh.shop.po.log.LogInfo;
import com.fh.shop.po.user.User;
import com.fh.shop.util.DistributedSession;
import com.fh.shop.util.KeyUtil;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.util.SystemConst;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LogAspect {

    private static  final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);


    @Resource(name="logService")
    private IlogService logService;

    public Object doLog(ProceedingJoinPoint pjp){

        //获取类名
        String canonicalName = pjp.getTarget().getClass().getCanonicalName();
        //获取方法名
        String methodname = pjp.getSignature().getName();
        //获取session中用户信息
        HttpServletRequest request = WebContext.getRequest();
        HttpServletResponse response = WebContext.getResponse();
        String sessionId = DistributedSession.getSessionId(request, response);
        String attribute = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        User userInfo = JSONObject.parseObject(attribute, User.class);
        String userName = userInfo.getUserName();
        String realName = userInfo.getRealName();
        //获取参数详情
        StringBuilder str = getStringBuilder(request);

        //获取自定义注解
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        String value =null;
        if(method.isAnnotationPresent(Log.class)){
            Log annotation = method.getAnnotation(Log.class);
            value = annotation.value();
        }
        //实际执行的方法
        Object proceed =null;
        try {
             proceed = pjp.proceed();
             LOGGER.info(userName+"执行了"+canonicalName+"中的"+methodname+"方法成功！！！");
            LogInfo logInfo = new LogInfo();
            logInfo.setCurrDate(new Date());
            logInfo.setErrorMsg("");
            logInfo.setInfo("执行了"+canonicalName+"中的"+methodname+"方法成功！！！");
            logInfo.setRealName(realName);
            logInfo.setUserName(userName);
            logInfo.setStatus(SystemConst.LOG_INFO_SUCCESS);
            logInfo.setDetail(str.toString());
            logInfo.setContent(value);
            logService.addLog(logInfo);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOGGER.error(userName+"执行了"+canonicalName+"中的"+methodname+"方法失败！！ 失败原因是："+throwable.getMessage());
            LogInfo logInfo = new LogInfo();
            logInfo.setCurrDate(new Date());
            logInfo.setErrorMsg(throwable.getMessage());
            logInfo.setInfo("执行了"+canonicalName+"中的"+methodname+"方法失败！！");
            logInfo.setRealName(realName);
            logInfo.setUserName(userName);
            logInfo.setStatus(SystemConst.LOG_INFO_ERROR);
            logInfo.setDetail(str.toString());
            logInfo.setContent(value);
            logService.addLog(logInfo);
            throw new RuntimeException(throwable);
        }
        return proceed;
    }

    private StringBuilder getStringBuilder(HttpServletRequest request) {
        StringBuilder str =new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String[] value = next.getValue();
            str.append("/").append(key).append("==").append(StringUtils.join(value, ","));
        }
        return str;
    }


}
