package com.fh.shop.api.type.controller;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.type.biz.ITypeService;
import com.fh.shop.api.type.biz.ITypeServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/types")
@CrossOrigin("*")
public class TypeController {

    @Resource(name="typeService")
    private ITypeService typeService;

    @RequestMapping("/list")
    public ServerResponse  findList(){
        return  typeService.findList();
    }

}
