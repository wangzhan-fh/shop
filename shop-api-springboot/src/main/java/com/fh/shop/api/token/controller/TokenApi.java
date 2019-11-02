package com.fh.shop.api.token.controller;

import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.token.biz.ITokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tokens")
public class TokenApi {

    @Resource(name = "tokenService")
    private ITokenService tokenService;

    @GetMapping
    @Check
    public ServerResponse getToken(){
        return tokenService.getToken();
    }



}
