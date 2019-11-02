package com.fh.shop.controller;

import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.util.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cache")
@RestController
public class CacherController {

    @RequestMapping("deleteCache")
    public ServerResponse deleteCache(){
        RedisUtil.del("showProductList");
        return ServerResponse.success();
    }
}
