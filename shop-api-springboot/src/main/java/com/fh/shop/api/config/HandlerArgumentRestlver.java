package com.fh.shop.api.config;

import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.SystemConst;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
@Component
public class HandlerArgumentRestlver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> nestedParameterType = parameter.getNestedParameterType();
        return nestedParameterType==MemberVo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBER_Info);
        return memberVo;
    }
}
