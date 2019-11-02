package com.fh.shop.api.pay.controller;

import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.pay.biz.IPayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.ws.Service;

@RestController
@RequestMapping("/pays")
public class PayApi {

    @Resource(name="payService")
    private IPayService payService;

    @RequestMapping("/createNative")
    @Check
    public ServerResponse createNative(MemberVo memberVo){
        Long id = memberVo.getId();
        return payService.createNative(id);

    }

    @RequestMapping("/queryNative")
    @Check
    public ServerResponse queryNative(MemberVo memberVo){
        Long id = memberVo.getId();
        return payService.queryNative(id);

    }


}
