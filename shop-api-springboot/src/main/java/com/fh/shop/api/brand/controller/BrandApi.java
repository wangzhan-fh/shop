package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.brand.param.BrandSearchBrand;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.conmmons.DataTableResult;
import com.fh.shop.api.conmmons.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brands")
@CrossOrigin("*")
public class BrandApi {

    @Resource(name="brandService")
    private IBrandService brandService;

    //分页条件
    @RequestMapping(value = "lists" ,method = RequestMethod.GET)
    public ServerResponse searchList(BrandSearchBrand brandSearchBrand){
        return brandService.searchList(brandSearchBrand);
    }

    @GetMapping
    public ServerResponse list(){
        return brandService.findlist();
    }

    @PostMapping
    public ServerResponse  addBrand(Brand brand){
        return  brandService.addBrand(brand);
    }

    @DeleteMapping(value="/{id}")
    public ServerResponse  deleteBrand(@PathVariable Long id){
        return  brandService.deleteBrand(id);
    }

    @DeleteMapping
    public ServerResponse  deleteBatchBrand(String ids){
        return  brandService.deleteBatchBrand(ids);
    }


    @GetMapping(value="/{id}")
    public ServerResponse findOne(@PathVariable Long id){
        return brandService.findOne(id);
    }

    @PutMapping
    public ServerResponse update(@RequestBody Brand  brand){
        return brandService.update(brand);
    }





}
