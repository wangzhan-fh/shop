package com.fh.shop.api.shInfo.controller;

import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.shInfo.biz.IShInfoService;
import com.fh.shop.api.shInfo.po.Shinfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shInfo")
public class ShInfoApi {


    @Resource(name = "shinfoService")
    private IShInfoService shInfoService;

    @PostMapping
    @Check
    public ServerResponse addInfo(Shinfo shinfo){
        return shInfoService.addInfo(shinfo);
    }

    @GetMapping
    @Check
    public ServerResponse findInfo(){
        return shInfoService.findInfo();
    }

    @DeleteMapping("/{id}")
    @Check
    public ServerResponse deleteArea(@PathVariable Integer id){
        return shInfoService.deleteArea(id);
    }


    @GetMapping("/byId")
    @Check
    public ServerResponse findInfobyId(Long id){
        return shInfoService.findInfobyId(id);
    }


}
