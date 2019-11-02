package com.fh.shop.api.product.controller;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.product.biz.IProductService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("/product")
@Component
public class productController {

    @Resource(name="productService")
    private IProductService productService;


    @RequestMapping("productList")
    public Object productList(String callback){
        ServerResponse serverResponse = productService.productList();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(serverResponse);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }



}
