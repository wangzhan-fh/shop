package com.fh.shop.api.area.controller;

import com.fh.shop.api.area.biz.IAreaService;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.conmmons.ServerResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/areas")
@CrossOrigin("*")
public class AreaController {
    @Resource(name = "areaService")
    private IAreaService areaService;


    @GetMapping
    public ServerResponse findList(Integer id){
        return areaService.findList(id);
    }


}
