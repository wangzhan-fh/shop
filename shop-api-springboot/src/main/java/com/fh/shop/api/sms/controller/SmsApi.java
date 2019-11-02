package com.fh.shop.api.sms.controller;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.sms.biz.ISmsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sms")
@CrossOrigin("*")
public class SmsApi {

    @Resource
    private ISmsService smsService;

    @GetMapping
    public ServerResponse sendMsg(String phone){
        return smsService.sendMsg(phone);
    }


}
