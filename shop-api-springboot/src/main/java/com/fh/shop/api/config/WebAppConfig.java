package com.fh.shop.api.config;

import com.fh.shop.api.interceptor.ApiIdempotentInterceptor;
import com.fh.shop.api.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(apiIdempotentInterceptor()).addPathPatterns("/**");
    }

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(handlerArgumentRestlver());
    }


    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor(){
        return new ApiIdempotentInterceptor();
    }


    @Bean
    public HandlerArgumentRestlver handlerArgumentRestlver(){
        return new HandlerArgumentRestlver();
    }
}
