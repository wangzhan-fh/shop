package com.fh.shop.api.brand.controller;

import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.conmmons.ServerResponse;
import io.swagger.annotations.Api;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brand")
@Api(value = "品牌测试模块")
public class BrandController {

    @Resource(name="brandService")
    private IBrandService brandService;


    @RequestMapping("/list")
    @Check
    public ServerResponse list(){
        ServerResponse list = brandService.list();
        return list;
    }

    @PostMapping("/add")
    public ServerResponse add(Brand brand){
        return brandService.add(brand);
    }

}
