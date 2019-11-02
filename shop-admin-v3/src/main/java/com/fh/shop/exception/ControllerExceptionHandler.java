package com.fh.shop.exception;

import com.fh.shop.conmmons.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse exceptionHandler(Exception ex){
        ex.printStackTrace();
        return ServerResponse.error();
    }
}
