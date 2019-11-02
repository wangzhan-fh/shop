package com.fh.shop.api.job;

import com.fh.shop.api.product.biz.IProductService;
import org.apache.ibatis.annotations.Results;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductStockLess {

    @Resource(name="productService")
    private IProductService productService;


    @Scheduled(cron = "0/20 * * * * ?")
    public void findStockLess(){
        productService.findStockLess(5);
    }

}
