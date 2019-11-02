package com.fh.shop.api.exception;

import com.fh.shop.api.conmmons.ServerResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerHanderException {

    @ExceptionHandler(GlobalException.class)
    public ServerResponse exception(GlobalException globalException){
        return ServerResponse.error(globalException.getResponceEnum());
    }
}
